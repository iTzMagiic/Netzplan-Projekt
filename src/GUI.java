import java.util.*;

public class GUI {

    private Scanner scanner;
    private Logic logic;


    public GUI(Logic logic) {
        this.logic = logic;
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        boolean cancle = false;

        while (!cancle) {
            System.out.println("███    ██ ███████ ████████ ███████ ██████  ██       █████  ███    ██     ███████ ██████  ███████ ████████ ███████ ██      ██      ███████ ███    ██ \n" +        "████   ██ ██         ██       ███  ██   ██ ██      ██   ██ ████   ██     ██      ██   ██ ██         ██    ██      ██      ██      ██      ████   ██ \n" +        "██ ██  ██ █████      ██      ███   ██████  ██      ███████ ██ ██  ██     █████   ██████  ███████    ██    █████   ██      ██      █████   ██ ██  ██ \n" +        "██  ██ ██ ██         ██     ███    ██      ██      ██   ██ ██  ██ ██     ██      ██   ██      ██    ██    ██      ██      ██      ██      ██  ██ ██ \n" +        "██   ████ ███████    ██    ███████ ██      ███████ ██   ██ ██   ████     ███████ ██   ██ ███████    ██    ███████ ███████ ███████ ███████ ██   ████ \n" +        "                                                                                                                                                    \n");
            if (NetworkplanList.isListEmpty()) {
                cancle = createNetworkplanMenu();
                consoleClear();
            } else {
                System.out.println("Willkommen");
                System.out.println("Was Möchten Sie machen?\n");
                System.out.println("'1' Neues Netzplan erstellen.");
                System.out.println("'2' Netzplan Anzeigen");
                System.out.println("'0' Beenden");

                int choice = readInt("Bitte wählen Sie: ");

                switch (choice) {
                    case 1:
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


    // Gibt ein Boolean zurück, ob ein Netzplan erstellt worden ist oder ob beendet werden soll.
    public boolean createNetworkplanMenu() {
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

        Networkplan networkplan = logic.addNetworkplan(name);

        consoleClear();
        System.out.println("Netzwerkplan wurde erstellt.");
        System.out.println("Möchten Sie Knotenpunkte hinzufügen?");
        System.out.println("'1' Ja");
        System.out.println("'2' Nein");

        int choice = scanner.nextInt();
        scanner.nextLine();
        consoleClear();

        if (choice == 1) {
            createProcessMenu(networkplan);
        }
        return true;
    }

    public void createProcessMenu(Networkplan networkplan) {
        int[] arrayWithDependencies;

        System.out.println("Bitte geben Sie den Namen des Knotenpunkts an:");
        String name = scanner.nextLine();

        System.out.println("Bitte geben Sie die Dauer an:");
        int duration = scanner.nextInt();
        scanner.nextLine();


        System.out.println("Möchten Sie ein Vorgänger hinzufügen?");
        System.out.println("'1' Ja");
        System.out.println("'2' Nein");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            arrayWithDependencies = addDependencies();
        } else {
            arrayWithDependencies = null;
        }
        Process process = new Process(name, duration, arrayWithDependencies);
        logic.addProcessToNetworkplan(networkplan, process);

    }

    // Gibt ein Dynamisches Array wieder mit Dependencies
    public int[] addDependencies() {
        // Liste für die Vorgänger eines Process
        List<Integer> dependencies = new ArrayList<>();
        int[] dependenciesArray = null;

        while (true) {
            System.out.println("Bitte geben Sie den Vorgänger an:");
            dependencies.add(scanner.nextInt());
            scanner.nextLine();

            System.out.println("Möchten Sie ein weiteres hinzufügen?");
            System.out.println("'1' Ja");
            System.out.println("'2' Nein");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 2) {
                break;
            }

            dependenciesArray = new int[dependencies.size()];
            for (int i = 0; i < dependencies.size(); i++) {
                dependenciesArray[i] = dependencies.get(i);
            }
        }

        return dependenciesArray;
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

            if (choice != 0) {
                consoleClear();
                choice--;
                System.out.println(NetworkplanList.getNetworkplan().get(choice).toString());
                System.out.println("Möchten Sie noch ein anderes Netzplan Anschauen?");
                System.out.println("'1' Ja");
                System.out.println("'2' Nein");

                choice = readInt("Bitte Auswahl angeben: ");

                if (choice == 1) {
                    consoleClear();
                    continue;
                }
            }
            consoleClear();
            cancle = true;
        }
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



}
