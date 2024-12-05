import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Logic logic;



    public UserInterface(Logic logic) {
        scanner = new Scanner(System.in);
        this.logic = logic;
    }



}
