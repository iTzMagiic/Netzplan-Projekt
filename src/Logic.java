import java.util.ArrayList;
import java.util.Scanner;

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

    // Hier wird ein Array aus ArrayList erstellt und zurückgegeben
    public int[] getDependenciesArray(ArrayList<Integer> listOfDependencies) {
        int[] dependenciesArray = new int[listOfDependencies.size()];
        for (int i = 0; i < listOfDependencies.size(); i++) {
            dependenciesArray[i] = listOfDependencies.get(i);
        }
        return dependenciesArray;
    }

    public boolean deleteDependencies(Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(null);
            return true;
        }
        return false;
    }



    // Gibt ein Dynamisches Array wieder mit Dependencies
    public void addDependencies(Networkplan networkplan, Process process) {
        ArrayList<Integer> listOfDependencies = new ArrayList<>(); // Liste für die Angegebenen Vorgänger
        int dependencie;
        int ownNr = process.getNr();

        do {
            do {            // Ein Vorgänger für den Process entgegennehmen
                if (!listOfDependencies.isEmpty()) { // Gibt die Aktuellen Vorgänger wieder als übersicht
                    System.out.print("Aktuelle Vorgänger: ");
                    for (int i = 0; i < listOfDependencies.size(); i++) {
                        System.out.printf("%d, ", listOfDependencies.get(i));
                    }
                    System.out.println("\n");
                }
                //TODO:: Eine Liste aller möglichen Vorgänger hier angeben im Sysout
                dependencie = readInt("Bitte geben Sie ein Vorgänger an ('0' zum Abbrechen): ");


                if (dependencie == ownNr) {
                    consoleClear();
                    System.out.println("Bitte nicht den eigenen Knoten angeben!");
                    dependencie = -1;
                    continue;
                }

                if (dependencie > ownNr) {
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
                if (listOfDependencies.contains(dependencie)) {
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
            listOfDependencies.add(dependencie);

            consoleClear();

        } while (askYesOrNo("Möchten Sie ein weiteren Vorgänger hinzufügen?"));
        consoleClear();

        if (listOfDependencies.isEmpty()) {
            return;
        }
        process.setDependencies(getDependenciesArray(listOfDependencies));
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


}
