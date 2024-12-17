public class Rules {
    public static boolean isUsernameValid(String username) {
//        return username != null && username.matches("(?=(.*[a-zA-ZäöüÄÖÜß]){2,})[a-zA-ZäöüÄÖÜß0-9]{4,}");
        return username != null && username.length() > 4;
    }

    public static boolean isPasswordValid(String password) {
//        return password != null && password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$");
        return password != null && password.length() > 4;
    }
}
