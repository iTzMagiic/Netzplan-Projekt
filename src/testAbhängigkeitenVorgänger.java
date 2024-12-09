
import java.util.*;

public class testAbhängigkeitenVorgänger {
    public static void main(String[] args){

        List<Integer> listePakete1 = new ArrayList<>();
        List<Integer> listePakete2 = new ArrayList<>();


        Process paket1 = new Process("Arbeit", 1, 5);
        Process paket2 = new Process("Schlafen", 2, 10);
        Process paket3 = new Process("Essen", 3, 2);

        listePakete1.add(paket1.getNr());
        listePakete2.add(paket2.getNr());
        listePakete2.add(paket3.getNr());

        Map<Integer, List<Integer>> listDependencies = new HashMap<>();

        listDependencies.put(1, listePakete1);
        listDependencies.put(2, listePakete2);

        System.out.println(listDependencies);
    }
}
