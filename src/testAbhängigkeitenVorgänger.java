
import java.util.*;

public class testAbhängigkeitenVorgänger {
    public static void main(String[] args){


        Process paket1 = new Process("Arbeit", 1, 5);
        Process paket2 = new Process("Schlafen", 2, 10);
        Process paket3 = new Process("Essen", 3, 2);
        Process paket4 = new Process("Nichts", 4, 20);

        paket2.setDependencies(new int[1]);
        int[] array = {1,2};
        paket3.setDependencies(array);

        System.out.println(Arrays.toString(paket3.getDependencies()));

        Map<Process, List<Process>> listDependencies = new HashMap<>();

        listDependencies.put(paket1, new ArrayList<>());
        listDependencies.put(paket2, Arrays.asList(paket1));
        listDependencies.put(paket3, Arrays.asList(paket1));
        listDependencies.put(paket4, Arrays.asList(paket2, paket3));

        System.out.println(listDependencies);
    }
}
