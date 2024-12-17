import java.util.*;

public class UserInterface {

    private final Scanner scanner;
    private final Logic logic;


    public UserInterface(Logic logic) {
        this.logic = logic;
        this.scanner = new Scanner(System.in);
    }


    public void menu() {
        boolean cancel = false;

        loginMenu();
        while (!cancel) {
            System.out.println("███    ██ ███████ ████████ ███████ ██████  ██       █████  ███    ██     ███████ ██████  ███████ ████████ ███████ ██      ██      ███████ ███    ██ \n" + "████   ██ ██         ██       ███  ██   ██ ██      ██   ██ ████   ██     ██      ██   ██ ██         ██    ██      ██      ██      ██      ████   ██ \n" + "██ ██  ██ █████      ██      ███   ██████  ██      ███████ ██ ██  ██     █████   ██████  ███████    ██    █████   ██      ██      █████   ██ ██  ██ \n" + "██  ██ ██ ██         ██     ███    ██      ██      ██   ██ ██  ██ ██     ██      ██   ██      ██    ██    ██      ██      ██      ██      ██  ██ ██ \n" + "██   ████ ███████    ██    ███████ ██      ███████ ██   ██ ██   ████     ███████ ██   ██ ███████    ██    ███████ ███████ ███████ ███████ ██   ████ \n" + "                                                                                                                                                    \n");
            if (NetworkplanList.isListEmpty()) {
                cancel = createNetworkplanMenu();
                logic.consoleClear();
            } else {
                showMainMenu();

                int choice = logic.readInt("Bitte wählen Sie: ");

                switch (choice) {
                    case 1:
                        createNetworkplanMenu();
                        break;
                    case 2:
                        showAllNetworkplansMenu();
                        break;
                    case 0:
                        cancel = true;
                        UserSession.getUserSession().clearSession();
                        break;
                }
            }
        }
        System.out.println("\nDas Programm wird Beendet....");
    }

    public void loginMenu() {
        boolean isExistUser;
        boolean isCreatedUser;
        if (logic.askYesOrNo("Haben Sie bereits ein Konto?")){
            do {
                logic.consoleClear();
                String username = logic.readString("Benutzername: ");
                String password = logic.readString("Passwort: ");
                isExistUser = logic.loginToDatabase(username, password);
            } while (!isExistUser);
        } else {
            logic.consoleClear();
            do {
                System.out.println("Bitte geben Sie mind. 5 Zeichen an um sich zu Regestrieren");
                String username = logic.readString("Benutzername: ");
                String password = logic.readString("Passwort: ");
                logic.consoleClear();

                isCreatedUser = logic.createAccount(username, password);
            } while (!isCreatedUser);
        }
    }


    public void showMainMenu() {
        System.out.println("Willkommen " + UserSession.getUserSession().getUsername());
        System.out.println("Was Möchten Sie machen?\n");
        System.out.println("'1' Neues Netzplan erstellen.");
        System.out.println("'2' Netzplan Anzeigen");
        System.out.println("'0' Beenden");
    }


    // Gibt ein Boolean zurück, ob ein Netzplan erstellt worden ist oder ob beendet werden soll.
    public boolean createNetworkplanMenu() {
        System.out.println("Willkommen");
        String name;
        do {
            System.out.println("Falls Sie das Programm beenden möchten geben Sie '0' ein.");
            System.out.println("Bitte geben Sie den Namen des Netzplans ein: ");
            name = scanner.nextLine();
            logic.consoleClear();
        } while (name.isEmpty());

        if (name.length() == 1 && name.charAt(0) == '0') {
            return true;
        }

        Networkplan networkplan = logic.addNetworkplan(name);

        logic.consoleClear();
        System.out.println("Netzwerkplan wurde erstellt.");

        if (logic.askYesOrNo("Möchten Sie Knotenpunkte hinzufügen?")) {
            createProcessMenu(networkplan);
        }
        return false;
    }


    public void createProcessMenu(Networkplan networkplan) {
        String name;
        int duration;

        do {
            logic.consoleClear();
            do {
                name = logic.readString("Bitte geben Sie den Namen des Knotenpunkts an ('0' zum Abbrechen): ");
            } while (name.isEmpty());

            if (name.length() == 1 && name.charAt(0) == '0') {
                break;
            }


            do {
                logic.consoleClear();
                System.out.printf("Knotenpunkt : %S\n\n", name);
                duration = logic.readInt("Bitte geben Sie die Dauer an ('0' zum Abbrechen): ");
            } while (duration < 0);
            logic.consoleClear();

            if (duration == 0) {
                break;
            }

            int choice;


            Process process = logic.createProcess(networkplan, name, networkplan.incrementAndGetProcessCounter(), duration);


            if (logic.isAllowedToCreateDependencies(networkplan)) {
                do {
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


    public void showAllNetworkplansMenu() {
        boolean cancel = false;
        logic.consoleClear();

        while (!cancel) {
            Map<Integer, String> mapNetworkplan = new HashMap<>();
            System.out.println("Vorhandene Netzpläne:");

            int index = 1;
            for (Networkplan networkplan : NetworkplanList.getAllNetworkplans()) {
                mapNetworkplan.put(index, networkplan.getName());
                System.out.println("'" + index + "'" + " : " + mapNetworkplan.get(index));
                index++;
            }
            System.out.println("'0' : Zurück");

            int choice = logic.readInt("Bitte wählen Sie ein Netzplan aus falls Sie möchten: ");

            if (choice == 0) {
                cancel = true;
            } else if (choice <= NetworkplanList.getAllNetworkplans().size() && choice >= 0) {
                logic.consoleClear();
                choice--;
                showSelectedNetworkplanMenu(choice);
            }
            logic.consoleClear();
        }
    }


    public void showSelectedNetworkplanMenu(int choice) {
        int option;
        Networkplan networkplan = NetworkplanList.getAllNetworkplans().get(choice);
        do {
            System.out.println("Ausgewählter Netzplan : " + networkplan.toString() + "\n\n");
            System.out.println("'1' Netzplan ausgeben");
            System.out.println("'2' Tabelle ausgeben");
            System.out.println("'3' Neuen Knoten hinzufügen");
            System.out.println("'4' Knoten Anzeigen/-Bearbeiten");
            System.out.println("'5' Netzplan löschen");
            System.out.println("'6' Anderen Netzplan wählen");
            System.out.println("'0' Abbrechen");
            option = logic.readInt("Eingabe : ");

            switch (option) {
                case 1:
                    showSelectedNetworkplanMenu(networkplan);
                    continue;
                case 2:
                    showSelectedNetworkplanTableMenu(networkplan);
                    continue;
                case 3:
                    createProcessMenu(networkplan);
                    continue;
                case 4:
                    showProcessesFromSelectedNetworkplanMenu(networkplan);
                    continue;
                case 5:
                    if (!logic.askYesOrNo("Möchten Sie den Netzplan wirklich löschen?")) {
                        continue;
                    }
                    logic.deleteNetworkplan(networkplan);
                    return;
                case 6:
                    logic.consoleClear();
                    return;
            }
            logic.consoleClear();
        } while (option != 0);
        logic.consoleClear();
    }


    public void showProcessesFromSelectedNetworkplanMenu(Networkplan networkplan) {
        int choice;
        do {
            logic.consoleClear();
            if (networkplan.getListOfProcesses().isEmpty()) {
                System.out.println("Keine Knoten. Liste leer...");
                if (logic.askYesOrNo("\nKnoten erstellen?")) {
                    createProcessMenu(networkplan);
                }
                break;
            }

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
                editProcessMenu(networkplan, networkplan.getListOfProcesses().get(choice));
            }
        } while (true);
        logic.consoleClear();
    }

    public void editProcessMenu(Networkplan networkplan, Process process) {
        int choice;
        do {
            while (true) {
                logic.consoleClear();
                System.out.printf("Bearbeitung des Knotens : %S\tDauer : %d\tVorgänger : [%S]\n", process.getName(), process.getDuration(), process.getDependenciesAsString());

                System.out.println("'1' Namen ändern");
                System.out.println("'2' Dauer ändern");

                // Knoten 1 kann keine Vorgänger haben
                if (process.getNr() != 1) {
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
                    logic.editProcessName(process);
                    continue;
                case 2:
                    logic.editProcessDuration(process);
                    continue;
                case 3:
                    logic.editProcessDependencies(networkplan, process);
                    continue;
            }
            break;
        } while (true);
    }


    public void showSelectedNetworkplanTableMenu(Networkplan networkplan) {
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


    public void showSelectedNetworkplanMenu(Networkplan networkplan) {
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