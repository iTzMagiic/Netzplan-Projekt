import java.util.Scanner;

public class UserInterface {


    private Scanner scanner;
    private Logic logic;



    public UserInterface(Logic logic) {
        scanner = new Scanner(System.in);
        this.logic = logic;
    }


    public void menu() {
        int choice;
        System.out.println("Hallo");

        // Prüfen Ob in der Liste Lerr
        if (NetworkplanList.isListEmpty()) {

            System.out.println("'1' Erstellen");
            System.out.println("'0' Beenden");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    showOptionsCreate();
                    break;
                case 0:
                    System.out.println("Programm wird beendet...");
                    break;
            }

        } else {

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
        }

    }


    public void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }


}
