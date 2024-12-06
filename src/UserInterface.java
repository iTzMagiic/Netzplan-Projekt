import java.util.Scanner;

public class UserInterface {


    private Scanner scanner;
    private Logic logic;
    Networkplan networkplan = null;



    public UserInterface(Logic logic) {
        scanner = new Scanner(System.in);
        this.logic = logic;
    }


    public void menu() {
        int choice;
        System.out.println("Hallo");

        // Prüfen Ob in der Liste leer
        if (NetworkplanList.isListEmpty()) {

            System.out.print("'1' Erstellen\t\t");
            System.out.println("'0' Beenden");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    showOptionsCreate();
                    break;
                case 0:
                    System.out.println("Programm wird beendet.");
                    break;
            }

        } else {
            System.out.println("'0' Beenden");
            System.out.println("'1' Paket erstellen");
            System.out.println("'2' Pakete verbinden");
            System.out.println("'3' Netzplan anzeigen");
            System.out.println("'4' Tabelle anzeigen");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    break;
                case 2:
                    scanner.nextLine();
                    break;
                case 3:
                    scanner.nextLine();
                    break;
                case 4:
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("Programm wird beendet.");
                    break;
            }



        }
    }


    public void showOptionsCreate() {
        clearConsole();
        String netzplanname;
        System.out.println("Bitte geben Sie den Netzplannamen an:");
        System.out.println("'0' Abbrechen");

        netzplanname = scanner.nextLine();


        switch (netzplanname) {
            case "0" :
                System.out.println("Das Erstellen wurde abgebrochen.");
                break;
            default:
                networkplan = new Networkplan(netzplanname);
        }


    }

    public void createProcess(){
        clearConsole();

    }


    public void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }


}
