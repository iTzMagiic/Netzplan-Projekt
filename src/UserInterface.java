import java.util.Scanner;

public class UserInterface {


    private Scanner scanner;
    private Logic logic;


    public UserInterface(Logic logic) {
        scanner = new Scanner(System.in);
        this.logic = logic;
    }

    public void start() {
        System.out.println("Netzwerkplan-Manager");
        int option = -1;

        // Prüfen Obj in der Liste leer
        while (option != 0) {
            if (NetworkplanList.isListEmpty()) {
                System.out.println("\n--- Hauptmenü ---");
                System.out.println("--- Status: Keine Netzpläne vorhanden ---\n\nOptionen:\n");
                System.out.print("'1' Erstellen\t\t");
                System.out.println("'0' Beenden");

                option = readInt("Wähle eine Option: \n");
                if (option == 1) {
                    Networkplan networkplan = createNetworkplan();
                    //handleSubMenu(networkplan);
                } else if (option == 0) {
                    System.out.println("\nProgramm beendet.");
                } else {
                    System.out.println("Ungültige Eingabe.");
                }
            } else {
                System.out.println("\n--- Hauptmenü ---");
                System.out.println("--- Status: Netzpläne vorhanden ---");
                showNetworkplanList();
                System.out.println("Wähle einen Netzplan(Nummer) oder 0 zum beenden:");

                option = readInt("Deine Wahl: ");
                if (option == 0) {
                    System.out.println("\nProgramm beendet.");
                } else if (option > 0 && option <= NetworkplanList.getNetworkplan().size()) {
                    handleSubMenu(NetworkplanList.getNetworkplanChoice(option-1));
                } else {
                    System.out.println("Ungültige Eingabe.");
                }
            }
        }
    }

    public void showMainMenu() {
        System.out.println("\n--- Hauptmenü ---");
        System.out.println("1: Netzplan erstellen");
        System.out.println("2: Netzplan auswählen");
        System.out.println("0: Beenden");}

    public Networkplan createNetworkplan() {
        String name = readString("Gib Namen für neuen Netzplan ein: \n");
        System.out.println("Netzplan '" + name + "' wurde erstellt.");
        return logic.addNetworkplan(name);
    }

    private void handleSubMenu(Networkplan currentNetworkplan) {
        int subOption = -1;

        while (subOption != 0) {
            System.out.println("\n\n--- Netzplan-Menü: " + currentNetworkplan.getName() + " ---");
            System.out.print("1: Paket erstellen\t\t");
            System.out.print("2: Netzplan ausgeben\t\t");
            System.out.print("3: Tabelle ausgeben\t\t");
            System.out.print("4: Netzplan wählen\t\t");
            System.out.print("5: Netzplan erstellen\t\t");
            System.out.print("9: Zurück\t\t");
            System.out.println("0: Beenden\n");

            subOption = readInt("Wähle eine Option: \n");
            switch (subOption) {
                case 1:
                    System.out.println("Paket erstellen - Noch nicht implementiert.");
                    //logic.addProcessToNetworkplan();
                    break;
                case 2:
                    System.out.println("Netzplan ausgeben - Noch nicht implementiert.");
                    break;
                case 3:
                    System.out.println("Tabelle ausgeben - Noch nicht implementiert.");
                    break;
                case 4:
                    showNetworkplanList();
                    break; // Zurück zur Netzplan-Auswahl
                case 5:
                    Networkplan networkplan = createNetworkplan();
                    handleSubMenu(networkplan);
                    break;
                case 9:
                    return; //Ebene zurück
                case 0:
                    System.out.println("Zurück zum Hauptmenü.");
                    break;
                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }
    }

    public void createProcess(){
        clearConsole();

    }
    public void showNetworkplanList(){
        System.out.println("Vorhandene Netzpläne:");

        int index = 1;
        for (Networkplan networkplan : NetworkplanList.getNetworkplan()){
            System.out.println(index + " : " + networkplan.toString());
            index++;
        }
    }

    public void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
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

}
