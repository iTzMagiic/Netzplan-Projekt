import java.util.*;

public class UserInterface {

    private Scanner scanner;
    private Logic logic;


    public UserInterface(Logic logic) {
        this.logic = logic;
        this.scanner = new Scanner(System.in);
    }



    public void menu() {
        boolean cancel = false;

        while (!cancel) {
            System.out.println("███    ██ ███████ ████████ ███████ ██████  ██       █████  ███    ██     ███████ ██████  ███████ ████████ ███████ ██      ██      ███████ ███    ██ \n" + "████   ██ ██         ██       ███  ██   ██ ██      ██   ██ ████   ██     ██      ██   ██ ██         ██    ██      ██      ██      ██      ████   ██ \n" + "██ ██  ██ █████      ██      ███   ██████  ██      ███████ ██ ██  ██     █████   ██████  ███████    ██    █████   ██      ██      █████   ██ ██  ██ \n" + "██  ██ ██ ██         ██     ███    ██      ██      ██   ██ ██  ██ ██     ██      ██   ██      ██    ██    ██      ██      ██      ██      ██  ██ ██ \n" + "██   ████ ███████    ██    ███████ ██      ███████ ██   ██ ██   ████     ███████ ██   ██ ███████    ██    ███████ ███████ ███████ ███████ ██   ████ \n" + "                                                                                                                                                    \n");
            if (NetworkplanList.isListEmpty()) {
                cancel = createNetworkplan();
                logic.consoleClear();
            } else {
                showMainMenu();

                int choice = logic.readInt("Bitte wählen Sie: ");

                switch (choice) {
                    case 1:
                        createNetworkplan();
                        break;
                    case 2:
                        showNetworkplanList();
                        break;
                    case 0:
                        cancel = true;
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


    // Gibt ein Boolean zurück, ob ein Netzplan erstellt worden ist oder ob beendet werden soll.
    public boolean createNetworkplan() {
        System.out.println("Willkommen");
        String name;
        do {    // Die Schleife wiederholt sich so lange bis was Eingegeben wurde.
            System.out.println("Falls Sie das Programm beenden möchten geben Sie '0' ein.");
            System.out.println("Bitte geben Sie den Namen des Netzplans ein: ");
            name = scanner.nextLine();
            logic.consoleClear();
        } while (name.isEmpty());

        if (name.length() == 1 && name.charAt(0) == '0') {
            return true;
        }

        // Erstellung des Netzplans
        Networkplan networkplan = logic.addNetworkplan(name);

        logic.consoleClear();
        System.out.println("Netzwerkplan wurde erstellt.");

        if (logic.askYesOrNo("Möchten Sie Knotenpunkte hinzufügen?")) {
            createProcess(networkplan);
        }
        return false;
    }

    // Erstellt Processe für einen Netzplan
    public void createProcess(Networkplan networkplan) {
        String name;
        int duration;

        do {
            logic.consoleClear();
            do {            // Namen des Knotenpunkts
                name = logic.readString("Bitte geben Sie den Namen des Knotenpunkts an ('0' zum Abbrechen): ");
            } while (name.isEmpty());

            if (name.length() == 1 && name.charAt(0) == '0') { // Abbruch
                break;
            }


            do {            // Dauer des Knotenpunkts
                logic.consoleClear();
                System.out.printf("Knotenpunkt : %S\n\n", name);
                duration = logic.readInt("Bitte geben Sie die Dauer an ('0' zum Abbrechen): ");
            } while (duration < 0);
            logic.consoleClear();

            if (duration == 0) {
                break;
            }


            int choice;


            Process process = new Process(name, networkplan.incrementAndGetProcessCounter(), duration);
            logic.addProcessToNetworkplan(networkplan, process);


            if (logic.isAllowedToCreateDependencies(networkplan)) {
                do {            // Einen Vorgänger angeben
                    System.out.printf("Nr : %d\t Knotenpunkt : %s \tDauer : %d%n\n", process.getNr(), name, duration);
                    System.out.println("Möchten Sie ein Vorgänger hinzufügen?");
                    choice = logic.readInt("'1' Ja? '2' Nein? : ");
                    logic.consoleClear();
                } while (choice < 1 || choice > 2);

                if (choice == 1) {
                    logic.addDependencies(networkplan, process);
                }
            }

        } while (logic.askYesOrNo("Möchten Sie noch ein Knotenpunkt hinzufügen?"));
        logic.consoleClear();
    }


    // Zeigt alle Vorhandenen Netzpläne an
    public void showNetworkplanList() {
        boolean cancel = false;
        logic.consoleClear();

        while (!cancel) {
            Map<Integer, String> mapNetworkplan = new HashMap<>();
            System.out.println("Vorhandene Netzpläne:");

            int index = 1;
            for (Networkplan networkplan : NetworkplanList.getNetworkplan()) {
                mapNetworkplan.put(index, networkplan.getName());
                System.out.println("'" + index + "'" + " : " + mapNetworkplan.get(index));
                index++;
            }
            System.out.println("'0' : Zurück");

            int choice = logic.readInt("Bitte wählen Sie ein Netzplan aus falls Sie möchten: ");

            if (choice == 0) {
                cancel = true;
            } else if (choice <= NetworkplanList.getNetworkplan().size() && choice >= 0) {
                logic.consoleClear();
                choice--;
                selectedNetworkplan(choice);
            }
            logic.consoleClear();
        }
    }


    public void selectedNetworkplan(int choice) {
        int option;
        Networkplan networkplan = NetworkplanList.getNetworkplan().get(choice);
        do {
            System.out.println("Ausgewählter Netzplan : " + networkplan.toString() + "\n\n");
            System.out.println("'1' Netzplan ausgeben");
            System.out.println("'2' Tabelle ausgeben");
            System.out.println("'3' Neuen Knoten hinzufügen");
            System.out.println("'4' Knoten Anzeigen/-Bearbeiten");
            System.out.println("'5' Anderen Netzplan wählen");
            System.out.println("'0' Abbrechen");
            option = logic.readInt("Eingabe : ");

            switch (option) {
                case 1:
                    showNetworkplan(networkplan);
                    continue;
                case 2:
                    showNetworkplanTable(networkplan);
                    continue;
                case 3:
                    createProcess(networkplan);
                    continue;
                case 4:
                    showProcessesFromNetworkplan(networkplan);
                    continue;
                case 5:
                    logic.consoleClear();
                    return;
            }
            logic.consoleClear();
        } while (option != 0);
        logic.consoleClear();
    }


    public void showProcessesFromNetworkplan(Networkplan networkplan) {
        int choice;
        do {
            logic.consoleClear();
            // Falls keine Knoten erstellt wurden, abfrage, ob man welche erstellen möchte
            if (networkplan.getListOfProcesses().isEmpty()) {
                System.out.println("Keine Knoten. Liste leer...");
                choice = logic.readInt("\nKnoten erstellen? ('1' Ja? '2' Nein?) : ");

                if (choice == 1) {
                    createProcess(networkplan);
                } else if (choice == 2) {
                    break;
                }

            } else {
                System.out.println("Ausgewählter Netzplan : " + networkplan);
                System.out.println("AP-Nr\tAP-Beschreibung\t\tDauer\tVorgänger");

                for (Process process : networkplan.getListOfProcesses()) {
                    System.out.printf("%d\t\t%S\t\t%d\t\t[%S]\n", process.getNr(), process.getName(), process.getDuration(), process.getDependenciesAsString());
                }

                choice = logic.readInt("\nWählen Sie ein Knoten um ihn zu bearbeiten. ('0' Zurück) : ");

                if (choice == 0) {
                    break;
                }


                if (choice > 0 && choice <= networkplan.getListOfProcesses().size()) {
                    choice--; // da Listen bei 0,1,2,3,4 anfangen
                    editProcess(networkplan, networkplan.getListOfProcesses().get(choice));
                }
            }
        } while (true);
        logic.consoleClear();
    }


    // Ist zur Änderung eines Knotens da
    public void editProcess(Networkplan networkplan, Process process) {
        int choice;
        do {
            while (true) {
                logic.consoleClear();
                System.out.printf("Bearbeitung des Knotens : %S\tDauer : %d\tVorgänger : [%S]\n", process.getName(), process.getDuration(), process.getDependenciesAsString());

                System.out.println("'1' Namen ändern");
                System.out.println("'2' Dauer ändern");
                if (process.getNr() != 1) {
                    // Knoten 1 kann keine Vorgänger haben
                    System.out.println("'3' Vorgänger ändern");
                }
                choice = logic.readInt("Eingabe ('0' Zurück) : ");

                if (choice == 0) {
                    logic.consoleClear();
                    break;
                } else if (choice >= 1 && choice <= 3) {
                    logic.consoleClear();
                    break;
                }
            }

            switch (choice) {
                case 1:
                    System.out.printf("Bearbeiten des Namen von : %S\n", process.getName());
                    String name = logic.readString("Neuer Name ('0' Zurück) : ");
                    if (name.length() == 1 && name.charAt(0) == '0') {
                        continue;
                    }
                    process.setName(name);
                    continue;
                case 2:
                    while (true) {
                        logic.consoleClear();
                        System.out.printf("Bearbeiten der Dauer : %d von dem Knoten : %S\n", process.getDuration(), process.getName());
                        int duration = logic.readInt("Neue Dauer ('0' Zurück) : ");
                        if (duration == 0) {
                            break;
                        } else if (duration < 0) {
                            System.out.println("Bitte nur echte Angaben!");
                            continue;
                        }
                        process.setDuration(duration);
                        break;
                    }
                    continue;
                case 3:
                    if (process.getNr() == 1) {
                        continue;
                    }
                    if (process.getListOfDependencies() != null) {
                        if (!logic.deleteAllDependenciesAndSuccessor(networkplan, process)) {
                            continue;
                        }
                        logic.consoleClear();
                    }
                    logic.addDependencies(networkplan, process);
                    continue;
            }
            // Wenn 0 Eingegeben wirt springt er hier hin und beendet die Methode
            break;
        } while (true);
    }


    public void showNetworkplanTable(Networkplan networkplan) {
        do {
            logic.consoleClear();
            System.out.println("Ausgewählter Netzplan : " + networkplan.toString());
            System.out.println("AP-Nr\tAP-Beschreibung\t\tDauer\tVorgänger");
            for (Process process : networkplan.getListOfProcesses()) {
                System.out.printf("%d\t\t%S\t\t%d\t\t[%S]\n", process.getNr(), process.getName(), process.getDuration(), process.getDependenciesAsString());

            }
        } while (logic.readInt("'0' Zurück : ") != 0);
        logic.consoleClear();
    }


    public void showNetworkplan(Networkplan networkplan) {
        logic.startCalculate(networkplan);
        do {
            logic.consoleClear();
            System.out.println("Ausgewählter Netzplan : " + networkplan + "\n");
            if (!networkplan.getListOfProcesses().isEmpty()) {
                for (Process process : networkplan.getListOfProcesses()) {
                    System.out.println(process.toString());
                }
                System.out.print("Der Kritische Pfad: ");
                for (int criticalPathNumber : CalculationProcess.getCalcCriticalPath(networkplan.getListOfProcesses())) {
                    System.out.print(criticalPathNumber + " ");
                }
                System.out.println();
                System.out.println();
            } else {
                System.out.println("Netzplan ist Leer.");
            }
        } while (logic.readInt("'0' Zurück : ") != 0);
        logic.consoleClear();
    }



}