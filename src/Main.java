public class Main {
    public static void main(String[] args) {


        Logic logic = new Logic();
        UserInterface userInterface = new UserInterface(logic);

        userInterface.menu();
    }
}