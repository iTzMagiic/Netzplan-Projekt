import java.util.ArrayList;
import java.util.List;

public class NetworkplanList {

    private static List<Networkplan> listOfPlan = new ArrayList<>();


    private NetworkplanList() {
    }


    public static List<Networkplan> getAllNetworkplans() {
        return listOfPlan;
    }

    public static void addNetworkplan(Networkplan p) {
        listOfPlan.add(p);
    }

    public static boolean isListEmpty() {
        return listOfPlan.isEmpty();
    }

    public static void setNetworkplanList(List<Networkplan> newListOfNetworkplans) {
        listOfPlan = newListOfNetworkplans;
    }

}
