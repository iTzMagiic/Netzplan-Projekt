import java.util.*;

public class UserInterface2 {

    private Scanner scanner;
    private Logic logic;


    public UserInterface2(Logic logic) {
        this.logic = logic;
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        boolean cancle = false;

        while (!cancle) {
            System.out.println("███    ██ ███████ ████████ ███████ ██████  ██       █████  ███    ██     ███████ ██████  ███████ ████████ ███████ ██      ██      ███████ ███    ██ \n" +        "████   ██ ██         ██       ███  ██   ██ ██      ██   ██ ████   ██     ██      ██   ██ ██         ██    ██      ██      ██      ██      ████   ██ \n" +        "██ ██  ██ █████      ██      ███   ██████  ██      ███████ ██ ██  ██     █████   ██████  ███████    ██    █████   ██      ██      █████   ██ ██  ██ \n" +        "██  ██ ██ ██         ██     ███    ██      ██      ██   ██ ██  ██ ██     ██      ██   ██      ██    ██    ██      ██      ██      ██      ██  ██ ██ \n" +        "██   ████ ███████    ██    ███████ ██      ███████ ██   ██ ██   ████     ███████ ██   ██ ███████    ██    ███████ ███████ ███████ ███████ ██   ████ \n" +        "                                                                                                                                                    \n");
            if (NetworkplanList.isListEmpty()) {
                cancle = createNetworkplan();
                consoleClear();
            } else {
                showMainMenu();

                int choice = readInt("Bitte wählen Sie: ");

                switch (choice) {
                    case 1:
                        createNetworkplan();
                        break;
                    case 2:
                        showNetworkplanList();
                        break;
                    case 0:
                        cancle = true;
                        break;
                }
            }
        }
        System.out.println("\nDas Programm wird Beendet....");
    }

    public void showMainMenu() {
        System.out.println("Willkommen");
        System.out.println("Was Möchten Sie machen?\n");
        System.out.println("'1' Neues Netzplan erstellen.");
        System.out.println("'2' Netzplan Anzeigen");
        System.out.println("'0' Beenden");
    }


    // TODO:: Die Methode createNetworkplan evtl in die Logic Klasse packen
    // Gibt ein Boolean zurück, ob ein Netzplan erstellt worden ist oder ob beendet werden soll.
    public boolean createNetworkplan() {
        System.out.println("Willkommen");
        String name;
        do {    // Die Schleife wiederholt sich so lange bis was Eingegeben wurde.
            System.out.println("Falls Sie das Programm beenden möchten geben Sie '0' ein.");
            System.out.println("Bitte geben Sie den Namen des Netzplans ein: ");
            name = scanner.nextLine();
            consoleClear();
        } while (name.isEmpty());

        if (name.length() == 1 && name.charAt(0) == '0') {
            return true;
        }

        // Erstellung des Netzplans
        Networkplan networkplan = logic.addNetworkplan(name);

        consoleClear();
        System.out.println("Netzwerkplan wurde erstellt.");

        if (askYesOrNo("Möchten Sie Knotenpunkte hinzufügen?")) {
            createProcess(networkplan);
        }
        return false;
    }

    // Erstellt Processe für einen Netzplan
    public void createProcess(Networkplan networkplan) {
        int[] arrayWithDependencies;
        String name;
        int duration;

        do {
            consoleClear();
            do {            // Namen des Knotenpunkts
                name = readString("Bitte geben Sie den Namen des Knotenpunkts an ('0' zum Abbrechen): ");
            } while (name.isEmpty());

            if (name.length() == 1 && name.charAt(0) == '0') { // Abbruch
                break;
            }


            do {            // Dauer des Knotenpunkts
                consoleClear();
                System.out.printf("Knotenpunkt : %S\n\n", name);
                duration = readInt("Bitte geben Sie die Dauer an ('0' zum Abbrechen): ");
            } while (duration < 0);
            consoleClear();

            if (duration == 0) {
                break;
            }


            int choice;




            Process process = new Process(name, networkplan.getProcessCounter(), duration);
            logic.addProcessToNetworkplan(networkplan, process);


            if (logic.isAllowedToCreateDependencies(networkplan)) {
                do {            // Einen Vorgänger angeben
                    System.out.printf("Nr : %d\t Knotenpunkt : %s \tDauer : %d%n\n", process.getNr(), name, duration);
                    System.out.println("Möchten Sie ein Vorgänger hinzufügen?");
                    choice = readInt("'1' Ja? '2' Nein? : ");
                    consoleClear();
                } while (choice < 1 || choice > 2);

                if (choice == 1) {
                    addDependencies(networkplan, process);
                }
            }

        } while (askYesOrNo("Möchten Sie noch ein Knotenpunkt hinzufügen?"));
        consoleClear();
    }


//    //  TODO:: Die Methode addDependencies in die Logic Klasse verlagern!!
//    // Gibt ein Dynamisches Array wieder mit Dependencies
//    public int[] addDependencies(Networkplan networkplan, int ownNr) {
//        // Liste für die Vorgänger eines Process
//        List<Integer> listOfDependencies = new ArrayList<>();
//        int[] dependenciesArray;
//        int dependencie;
//        int choice;
//
//        while (true) {
//            do {            // Ein Vorgänger für den Process entgegennehmen
//                if (!listOfDependencies.isEmpty()) { // Gibt die Aktuellen Vorgänger wieder als übersicht
//                    System.out.print("Aktuelle Vorgänger: ");
//                    for (int i = 0; i < listOfDependencies.size(); i++) {
//                        System.out.printf("%d, ", listOfDependencies.get(i));
//                    }
//                    System.out.println("\n");
//                }
//                dependencie = readInt("Bitte geben Sie ein Vorgänger an ('0' zum Abbrechen): ");
//
//
//                // Prüft ob ein existierender Vorgänger angegeben wurde und nicht er selbst
//                if (dependencie != 0 && !logic.isCorrectDependencies(networkplan, dependencie) && ownNr != dependencie) {
//                    consoleClear();
//                    System.out.println("Bitte nur ein Existierenden Vorgänge angeben!");
//                    dependencie = -1;
//                    continue;
//                }
//
//
//                // Prüft, ob er die gleichen Vorgänger angibt
//                if (listOfDependencies.contains(dependencie)) {
//                    consoleClear();
//                    System.out.println("Bitte nicht den gleichen Vorgänger angeben!");
//                    dependencie = -1;   // damit die Schleife wiederholt wird
//                    continue;
//                }
//                consoleClear();
//            } while (dependencie < 0);
//
//            if (dependencie == 0) {break;} // 0 == Abbrechen
//
//            // Vorgänger in ein ArrayList packen
//            listOfDependencies.add(dependencie);
//
//
//            do {            // Abfrage um noch ein Vorgänger hinzuzufügen
//                System.out.println("Möchten Sie ein weiteren Vorgänger hinzufügen?");
//                choice = readInt("'1' Ja? '2' Nein? : ");
//                consoleClear();
//            } while (choice < 1 || choice > 2);
//
//
//            if (choice == 2) { // Abbruch der While-Schleife
//                break;
//            }
//        }
//
//        // Hier wird die das Array mit der ArrayListe definiert & gefüllt
//        dependenciesArray = new int[listOfDependencies.size()];
//        for (int i = 0; i < listOfDependencies.size(); i++) {
//            dependenciesArray[i] = listOfDependencies.get(i);
//        }
//
//        return dependenciesArray;
//    }

    //  TODO:: Die Methode addDependencies in die Logic Klasse verlagern!!
    // Gibt ein Dynamisches Array wieder mit Dependencies
    public void addDependencies(Networkplan networkplan, Process process) {
        // Liste für die Vorgänger eines Process
        List<Integer> listOfDependencies = new ArrayList<>();
        int[] dependenciesArray;
        int dependencie;
        int choice;
        int ownNr = process.getNr();
        System.out.println(ownNr);

        while (true) {
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

                // Prüft ob ein existierender Vorgänger angegeben wurde und nicht er selbst
                if (dependencie != 0 && !logic.isCorrectDependencies(networkplan, dependencie)) {
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

            if (dependencie == 0) {break;} // 0 == Abbrechen

            // Vorgänger in ein ArrayList packen
            listOfDependencies.add(dependencie);


            do {            // Abfrage um noch ein Vorgänger hinzuzufügen
                System.out.println("Möchten Sie ein weiteren Vorgänger hinzufügen?");
                choice = readInt("'1' Ja? '2' Nein? : ");
                consoleClear();
            } while (choice < 1 || choice > 2);


            if (choice == 2) { // Abbruch der While-Schleife
                break;
            }
        }

        // Hier wird die das Array mit der ArrayListe definiert & gefüllt
        dependenciesArray = new int[listOfDependencies.size()];
        for (int i = 0; i < listOfDependencies.size(); i++) {
            dependenciesArray[i] = listOfDependencies.get(i);
        }
        process.setDependencies(dependenciesArray);
    }


    // Zeigt alle Vorhandenen Netzpläne an
    public void showNetworkplanList(){
        boolean cancle = false;
        consoleClear();

        while (!cancle) {
            Map<Integer, String> mapNetworkplan = new HashMap<>();
            System.out.println("Vorhandene Netzpläne:");

            int index = 1;
            for (Networkplan networkplan : NetworkplanList.getNetworkplan()){

                mapNetworkplan.put(index, networkplan.getName());
                System.out.println("'" + index + "'" + " : " + mapNetworkplan.get(index));
                index++;
            }
            System.out.println("'0' : Zurück");

            int choice = readInt("Bitte wählen Sie ein Netzplan aus falls Sie möchten: ");

            if (choice == 0) {
                cancle = true;
            } else if (choice <= NetworkplanList.getNetworkplan().size() && choice >= 0) {
                consoleClear();
                choice--;
                if(!isSelectingNetworkplan(choice)) {
                    cancle = true;
                }
            }
            consoleClear();
        }
    }

    public boolean isSelectingNetworkplan(int choice) {
        int option;
        do {
            System.out.println("Ausgewählter Netzplan : " + NetworkplanList.getNetworkplan().get(choice).toString() + "\n\n");
            System.out.println("'1' Netzplan ausgeben");
            System.out.println("'2' Tabelle ausgeben");
            System.out.println("'3' Neuen Knoten hinzufügen");
            System.out.println("'4' Knoten löschen");
            System.out.println("'5' Anderen Netzplan wählen");
            System.out.println("'0' Abbrechen");
            option = readInt("Eingabe : ");

            switch (option) {
                case 1:
                    showNetworkplan(NetworkplanList.getNetworkplan().get(choice));
                    continue;
                case 2:
                    showNetworkplanTable(NetworkplanList.getNetworkplan().get(choice));
                    continue;
                case 3:
                    createProcess(NetworkplanList.getNetworkplan().get(choice));
                    continue;
                case 4:
                    deleteProcess(NetworkplanList.getNetworkplan().get(choice));
                    continue;
                case 5:
                    consoleClear();
                    return true;
            }
            consoleClear();
        } while (option != 0);
        consoleClear();
        return false;
    }

    public void deleteProcess(Networkplan networkplan) {
        String toDeleteProcess;
        boolean isDeleted;

        do {
            consoleClear();
            System.out.println("Ausgewählter Netzplan: " + networkplan.getName());
            System.out.println("Verfügbare Knotenpunkte:");
            for (Process process : networkplan.getListOfProcesses()) {
                System.out.println("- " + process.getName());
            }

            toDeleteProcess = readString("\nGeben Sie den Namen des zu löschenden Knotenpunkts ein ('0' zum Abbrechen): ");


            // Versuchen, den Prozess zu löschen
            String finalToDeleteProcess = toDeleteProcess;
            isDeleted = networkplan.getListOfProcesses().removeIf(process -> process.getName().equals(finalToDeleteProcess));

            if (isDeleted) {
                System.out.println("Der Knotenpunkt '" + toDeleteProcess + "' wurde erfolgreich gelöscht.\n");
            } else if (toDeleteProcess.length() == 1 && toDeleteProcess.charAt(0) == '0') {
                break;
            }else {
                System.out.println("Der eingegebene Knotenpunkt '" + toDeleteProcess + "' wurde nicht gefunden.\n");
            }

        } while (askYesOrNo("Möchten Sie einen weiteren Knoten löschen?"));
        consoleClear();
    }



    public void showNetworkplanTable(Networkplan networkplan) {
        do {
            consoleClear();
            System.out.println("Ausgewählter Netzplan : " + networkplan.toString());
            System.out.println("AP-Nr\tAP-Beschreibung\t\tVorgänger\tDauer");
            for (Process process : networkplan.getListOfProcesses()) {
                System.out.printf("%d\t\t%S\t\t%S\t%d\n", 1, process.getName(), Arrays.toString(process.getDependencies()), process.getDuration());

            }
        } while (readInt("'0' Zurück : ") != 0);
        consoleClear();
    }


    public void showNetworkplan(Networkplan networkplan) {
        do {
            consoleClear();
            System.out.println("Ausgewählter Netzplan : " + networkplan.toString() + "\n");
            if (!networkplan.getListOfProcesses().isEmpty()) {
                for (Process process : networkplan.getListOfProcesses()) {
                    System.out.println(process.toString());
                }
            } else {
                System.out.println("Netzplan ist Leer.");
            }
        } while (readInt("'0' Zurück : ") != 0);
        consoleClear();
    }



    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Bitte eine gültige Zahl eingeben: ");
            scanner.next();
        }
        return scanner.nextInt();
    }


    private String readString(String prompt) {
        System.out.print(prompt);
        scanner.nextLine(); // Leere Zeile lesen
        return scanner.nextLine();
    }


    public void consoleClear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    private boolean askYesOrNo(String message) {
        int choice;
        do {
            choice = readInt(message + " ('1' Ja, '2' Nein): ");
        } while (choice < 1 || choice > 2);
        return choice == 1;
    }




}