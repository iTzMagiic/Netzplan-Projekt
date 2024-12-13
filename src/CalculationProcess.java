import java.util.ArrayList;
import java.util.List;

public class CalculationProcess {

    public static void calculateStart(List<Process> listOfProcesses) {
        // Prüfen, ob jemand kein Vorgänger hat. Wenn ja, FAZ und FEZ setzen
        for (Process process : listOfProcesses) {
            if (process.getListOfDependencies() == null) {
                process.setFez(process.getDuration());
                process.setFaz(0);
                process.setSaz(0);
                process.setSez(process.getDuration());
            }
        }
    }


    public static void calculateFAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            if (process.getListOfDependencies() == null || process.getListOfDependencies().isEmpty()) {
                continue;
            }

            int maxFEZ = 0;

            for (Process predecessor : process.getListOfDependencies()) {
                calculateFEZ(listOFProcesses);
                if (predecessor.getFez() > maxFEZ) {
                    maxFEZ = predecessor.getFez();
                }
            }
            process.setFaz(maxFEZ);
        }
    }


    public static void calculateFEZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            if (process.getListOfDependencies() == null) {continue;}
            process.setFez(process.getFaz() + process.getDuration());
        }
    }


    public static void calcSEZ(List<Process> listOFProcesses) {
    for (int lastProcess = listOFProcesses.size() - 1; lastProcess >= 0; lastProcess--) {
        System.out.println(lastProcess + " last -> index " + listOFProcesses.size());
        if (listOFProcesses.get(lastProcess).getListOfDependencies() != null &&
                (listOFProcesses.get(lastProcess).getListOfSuccessors() == null || listOFProcesses.get(lastProcess).getListOfSuccessors().isEmpty())) {
            listOFProcesses.get(lastProcess).setSez(listOFProcesses.get(lastProcess).getFez());
            calcSAZ(listOFProcesses.get(lastProcess));
            continue;
        }

        int minSAZ = Integer.MAX_VALUE;

        for (Process successor : listOFProcesses.get(lastProcess).getListOfSuccessors()) {

            if (successor.getSaz() < minSAZ) {
                minSAZ = successor.getSaz();
            }

        }
        listOFProcesses.get(lastProcess).setSez(minSAZ);
        calcSAZ(listOFProcesses.get(lastProcess));
    }

}


    public static void calcSAZ(Process process) {
        process.setSaz(process.getSez() - process.getDuration());
    }


    public static void calcGp(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setGp((process.getSez() - process.getFez()));
        }
    }


    public static void calcFp(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {

            if (process.getListOfSuccessors() == null || process.getListOfSuccessors().isEmpty()) {process.setFp(process.getGp());continue;}
            int minFP = Integer.MAX_VALUE;

            for (Process successor : process.getListOfSuccessors()) {
                if (successor.getFaz() < minFP) {
                    minFP = successor.getFaz();
                }
            }
            process.setFp(minFP - process.getFez());
        }
    }


    public static List<Integer> getCalcCriticalPath(List<Process> listOFProcesses) {
        List<Integer> listOfCriticalPath = new ArrayList<>();

        for (Process process : listOFProcesses) {
            if (process.getGp() == 0) {
                listOfCriticalPath.add(process.getNr());
            }
        }
        return listOfCriticalPath;
    }










    // Methode, die alle Berechnungen in der richtigen Reihenfolge ausführt
    public static void calculateAll(List<Process> processes) {
        calculateStart(processes);
        calculateFAZ(processes);
        calculateFEZ(processes);
        calcSEZ(processes);
        calcGp(processes);
        calcFp(processes);
    }
}

