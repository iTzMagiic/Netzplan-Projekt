import java.util.*;

public class Logic {

    Scanner scanner;


    public Logic() {
        scanner = new Scanner(System.in);
    }


    public Networkplan addNetworkplan(String name) {
        Networkplan networkplan = new Networkplan(name);
        NetworkplanList.addNetworkplan(networkplan);
        return networkplan;
    }


    public void addProcessToNetworkplan(Networkplan networkplan, Process process) {
        networkplan.addProcess(process);
    }


    public boolean isAllowedToCreateDependencies(Networkplan networkplan) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() > 1) {
                return true;
            }
        }
        return false;
    }


    public boolean isCorrectDependencies(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return true;
            }
        }
        return false;
    }


    public boolean isDeletingAllDependenciesAndSuccessorFromProcess(Networkplan networkplan, Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(new ArrayList<>());
            deleteSelectedProcessFromAllDependenciesList(networkplan, process);
            return true;
        }
        return false;
    }


    public void deleteSelectedProcessFromAllDependenciesList(Networkplan networkplan, Process deleteProcess) {
        for (Process process : networkplan.getListOfProcesses()) {
            for (Process successor : process.getListOfSuccessors()) {
                if (successor.getNr() == deleteProcess.getNr()) {
                    process.deleteSuccessor(successor);
                    break;
                }
            }
        }
    }


    public void addDependencies(Networkplan networkplan, Process process) {
        List<Process> listOfDependencies = new ArrayList<>(); // Liste für die Angegebenen Vorgänger
        int dependencie;

        do {
            do {
                if (!listOfDependencies.isEmpty()) { // Gibt die Aktuellen Vorgänger wieder als übersicht
                    System.out.print("Aktuelle Vorgänger: ");
                    for (int i = 0; i < listOfDependencies.size(); i++) {
                        System.out.printf("%d, ", listOfDependencies.get(i).getNr());
                    }
                    System.out.println("\n");
                }
                //TODO:: Eine Liste aller möglichen Vorgänger hier angeben im Sysout
                dependencie = readInt("Bitte geben Sie ein Vorgänger an ('0' zum Abbrechen): ");


                if (dependencie == process.getNr()) {
                    consoleClear();
                    System.out.println("Bitte nicht den eigenen Knoten angeben!");
                    dependencie = -1;
                    continue;
                }

                if (dependencie > process.getNr()) {
                    consoleClear();
                    System.out.println("Nur Vorgänger keine Nachfolger!");
                    dependencie = -1;
                    continue;
                }

                if (dependencie != 0 && !isCorrectDependencies(networkplan, dependencie)) {
                    consoleClear();
                    System.out.println("Bitte nur ein Existierenden Vorgänge angeben!");
                    dependencie = -1;
                    continue;
                }

                if (listOfDependencies.contains(getSelectedProcessForDependenciesList(networkplan, dependencie))) {
                    consoleClear();
                    System.out.println("Bitte nicht den gleichen Vorgänger angeben!");
                    dependencie = -1;   // damit die Schleife wiederholt wird
                    continue;
                }
                consoleClear();
            } while (dependencie < 0);

            if (dependencie == 0) {
                break;
            } // 0 == Abbrechen

            listOfDependencies.add(getSelectedProcessForDependenciesList(networkplan, dependencie));

            consoleClear();

        } while (isMoreDependenciesAllowed(networkplan, process, listOfDependencies) && askYesOrNo("Möchten Sie ein weiteren Vorgänger hinzufügen?"));
        consoleClear();

        if (listOfDependencies.isEmpty()) {
            return;
        }
        listOfDependencies.sort(Comparator.comparingInt(Process::getNr));

        setSuccessor(listOfDependencies, process);

        process.setDependencies(listOfDependencies);
    }


    public void editProcessDependencies(Networkplan networkplan, Process process) {
        if (process.getNr() == 1) {
            return;
        }
        if (process.getListOfDependencies() != null) {
            if (!isDeletingAllDependenciesAndSuccessorFromProcess(networkplan, process)) {
                return;
            }
            consoleClear();
        }
        addDependencies(networkplan, process);
    }

    public void editProcessDuration(Process process) {
        while (true) {
            consoleClear();
            System.out.printf("Bearbeiten der Dauer : %d von dem Knoten : %S\n", process.getDuration(), process.getName());
            int duration = readInt("Neue Dauer ('0' Zurück) : ");
            if (duration == 0) {
                break;
            } else if (duration < 0) {
                System.out.println("Bitte nur echte Angaben!");
                continue;
            }
            process.setDuration(duration);
            break;
        }
    }

    public void editProcessName(Process process) {
        System.out.printf("Bearbeiten des Namen von : %S\n", process.getName());
        String name = readString("Neuer Name ('0' Zurück) : ");
        if (name.length() == 1 && name.charAt(0) == '0') {
            return;
        }
        process.setName(name);
    }


    public void setSuccessor(List<Process> listOfDependencies, Process successor) {
        for (Process process : listOfDependencies) {
            process.addSuccessor(successor);
        }
    }


    public boolean isMoreDependenciesAllowed(Networkplan networkplan, Process process, List<Process> listOfDependencies) {
        for (Process checkProcess : networkplan.getListOfProcesses()) {
            if (checkProcess.getNr() != process.getNr() && !listOfDependencies.contains(checkProcess) && checkProcess.getNr() < process.getNr()) {
                return true;
            }
        }
        return false;
    }


    public Process getSelectedProcessForDependenciesList(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return process;
            }
        }
        return null;
    }


    public String readString(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
        return scanner.nextLine();
    }


    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Bitte eine gültige Zahl eingeben: ");
            scanner.next();
        }
        return scanner.nextInt();
    }


    public boolean askYesOrNo(String message) {
        int choice;
        do {
            choice = readInt(message + " ('1' Ja, '2' Nein): ");
        } while (choice < 1 || choice > 2);
        return choice == 1;
    }


    public void consoleClear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }


    public void startCalculate(Networkplan networkplan) {
        List<Process> listOfProcesses = networkplan.getListOfProcesses();

        CalculationProcess.calculateAll(listOfProcesses);
    }


}
