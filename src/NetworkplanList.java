import java.util.List;

public class NetworkplanList {

    private static List<Networkplan> listOfPlan;


    private NetworkplanList() {}


    public static void addNetworkplan(Networkplan p) {
        listOfPlan.add(p);
    }

    public static boolean isListEmpty() {
        return listOfPlan == null || listOfPlan.isEmpty();
    }
}
