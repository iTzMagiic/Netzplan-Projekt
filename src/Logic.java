import java.util.*;

public class Logic {

    Scanner scanner;



    public Logic() {
        scanner = new Scanner(System.in);
    }



    // Erstellt ein Netzplan, fügt es in die Liste der Static Klasse NetworkplanList
    // und gibt das Objekt zurück.
    public Networkplan addNetworkplan(String name) {
        Networkplan networkplan = new Networkplan(name);
        NetworkplanList.addNetworkplan(networkplan);
        return networkplan;
    }


    // Fügt ein Vorgang zu einem bestimmten Netzplan hinzu
    public void addProcessToNetworkplan(Networkplan networkplan, Process process) {
        networkplan.addProcess(process);
    }


    // Die Methode prüft, ob man überhaupt vorgänger haben kann, wenn nein dann kann man auch keine erstellen.
    public boolean isAllowedToCreateDependencies(Networkplan networkplan) {
        for (Process process : networkplan.getListOfProcesses()) {
            // Wenn mehr als 1 Knoten existiert dann True
            if (process.getNr() > 1) {
                return true;
            }
        }
        return false;
    }


    // Prüft, ob der Vorgänger auch wirklich existiert
    public boolean isCorrectDependencies(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return true;
            }
        }
        return false;
    }


    public boolean deleteAllDependenciesAndSuccessorFromProcess(Networkplan networkplan, Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(null);
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


    // Erstellt die Dependencies für die Processe
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

                // Prüft, ob ein existierender Vorgänger angegeben wurde und nicht er selbst
                if (dependencie != 0 && !isCorrectDependencies(networkplan, dependencie)) {
                    consoleClear();
                    System.out.println("Bitte nur ein Existierenden Vorgänge angeben!");
                    dependencie = -1;
                    continue;
                }


                // Prüft, ob er die gleichen Vorgänger angibt
                if (listOfDependencies.contains(getSelectedProcessForDependenciesList(networkplan , dependencie))) {
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

            // Vorgänger in ein ArrayList packen
            listOfDependencies.add(getSelectedProcessForDependenciesList(networkplan, dependencie));

            consoleClear();

        } while (isMoreDependenciesAllowed(networkplan, process, listOfDependencies) && askYesOrNo("Möchten Sie ein weiteren Vorgänger hinzufügen?"));
        consoleClear();

        if (listOfDependencies.isEmpty()) {
            return;
        }
        listOfDependencies.sort(Comparator.comparingInt(Process::getNr)); // Sortiert die Dependencies Liste nach der Process NR

        setSuccessor(listOfDependencies, process);

        process.setDependencies(listOfDependencies);
    }


    public void setSuccessor(List<Process> listOfDependencies, Process successor) {
        for (Process process : listOfDependencies) {
            process.addSuccessor(successor);
        }
    }


    public boolean isMoreDependenciesAllowed(Networkplan networkplan, Process process, List<Process> listOfDependencies) {
        for (Process checkProcess : networkplan.getListOfProcesses()) {
            if (checkProcess.getNr() != process.getNr() && !listOfDependencies.contains(checkProcess) && checkProcess.getNr() < process.getNr()) {
                return true; // Es gibt mindestens einen Prozess, der nicht in den Abhängigkeiten enthalten ist
            }
        }
        return false; // Keine weiteren Abhängigkeiten möglich
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
        scanner.nextLine(); // Leere Zeile lesen
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
