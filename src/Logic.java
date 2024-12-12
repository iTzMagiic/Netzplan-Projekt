
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


    public boolean deleteDependencies(Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(null);
            return true;
        }
        return false;
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
        process.setDependencies(listOfDependencies);
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


//    public void deleteProcess(Networkplan networkplan) {
//        String toDeleteProcess;
//        boolean isDeleted;
//
//        do {
//            consoleClear();
//            System.out.println("Ausgewählter Netzplan: " + networkplan.getName());
//            System.out.println("Verfügbare Knotenpunkte:");
//            for (Process process : networkplan.getListOfProcesses()) {
//                System.out.println("- " + process.getName());
//            }
//
//            toDeleteProcess = readString("\nGeben Sie den Namen des zu löschenden Knotenpunkts ein ('0' zum Abbrechen): ");
//
//
//            // Versuchen, den Prozess zu löschen
//            String finalToDeleteProcess = toDeleteProcess;
//            isDeleted = networkplan.getListOfProcesses().removeIf(process -> process.getName().equals(finalToDeleteProcess));
//
//            if (isDeleted) {
//                System.out.println("Der Knotenpunkt '" + toDeleteProcess + "' wurde erfolgreich gelöscht.\n");
//            } else if (toDeleteProcess.length() == 1 && toDeleteProcess.charAt(0) == '0') {
//                break;
//            }else {
//                System.out.println("Der eingegebene Knotenpunkt '" + toDeleteProcess + "' wurde nicht gefunden.\n");
//            }
//
//        } while (askYesOrNo("Möchten Sie einen weiteren Knoten löschen?"));
//        consoleClear();
//    }



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


//    public List<List<Process>> sortProcesses(Networkplan networkplan) {
//        List<List<Process>> sortedProcessesList = new ArrayList<>();
//        List<Process> addedProcess = new ArrayList<>();
//        int counter = 0;
//
//        for (Process process : networkplan.getListOfProcesses()) {
//            if (process.getNr() == 1) {
//                sortedProcessesList.add(List.of(process));
//                continue;
//            }
//
//            if (process.getDependencies() == null) {
//                addedProcess.add(process);
//            }
//
//            if (process.getDependencies() != null) {
//
//            }
//        }
//
//
//        for (int i = 0; i < networkplan.getListOfProcesses().size(); i++) {
//            Process process = networkplan.getListOfProcesses().get(i);
//
//            // Für die alle ohne Vorgänger in die erste Liste
//            if (process.getNr() == 1) {
//                for (Process processWithoutDependencies : networkplan.getListOfProcesses()) {
//                    if (processWithoutDependencies.getDependencies() == null) {
//                        addedProcess.add(processWithoutDependencies);
//                    }
//                }
//                sortedProcessesList.add(addedProcess);
//                addedProcess.clear();
//                continue;
//            }
//
//            // Überspringt alle ohne Vorgänger da sie in der ersten Liste sind
//            if (process.getDependencies() == null) {
//                continue;
//            }
//
//
//
////            //TODO:: hier prüfen ob dependencies in der addedProcess ist
////            if (process.getDependencies() == addedProcess.forEach(stream -> stream)) {
////                sortedProcessesList.add(addedProcess);
////                addedProcess.clear();
////                counter++;
////            }
//
//
//            // Prüft die nächsten Listen
//            if (process.getDependencies() != null) {
//
//                outer:
//                for (Process processesFromOldList : sortedProcessesList.get(counter)) {
//                    for (int indexOfDependencies : processesFromOldList.getDependencies()) {
//                        if (processesFromOldList.getNr() == indexOfDependencies) {
//                            addedProcess.add(process);
//                            break outer;
//                        }
//                    }
//                }
//            }
//
//        }
//
//
//        return sortedProcessesList;
//    }




}
