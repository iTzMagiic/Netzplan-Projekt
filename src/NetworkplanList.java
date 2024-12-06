import java.util.ArrayList;
import java.util.List;

public class NetworkplanList {

    private static List<Networkplan> listOfPlan = new ArrayList<>();


    private NetworkplanList() {}

    public static List<Networkplan> getNetworkplan(){
        return listOfPlan;
    }

    public static Networkplan getNetworkplanChoice(int index){
        return listOfPlan.get(index);
    }

    public static void addNetworkplan(Networkplan p) {
        listOfPlan.add(p);
    }

    public static boolean isListEmpty() {
        return listOfPlan == null || listOfPlan.isEmpty();
    }

}
