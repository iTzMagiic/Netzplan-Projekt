public class UserSession {

    /*
     * Diese Klasse implementiert das "Singleton-Pattern".
     * Ein Objekt der Klasse kann nur über die Methode getInstance() erstellt werden.
     * Dadurch wird sichergestellt, dass es in der gesamten Anwendung
     * nur eine einzige Instanz der Klasse gibt.
     */


    private static UserSession userSession;
    private int userID;



    private UserSession() {}

    public static UserSession getUserSession() {
        if(userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public void clearSession() {
        userSession = null;
        this.userID = 0;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }


    public int getUserID() {
        return userID;
    }



}
