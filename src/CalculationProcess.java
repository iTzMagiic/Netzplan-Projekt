import java.util.List;

public class CalculationProcess {

    public void calculateFAZ(List<Process> listOFProcesses) {
        int maxFEZ = 0;

        for (Process process : listOFProcesses) {
            if (process.getFaz() > maxFEZ) {
                maxFEZ = process.getFez();
            }
        }
    }

    public void calculateFEZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setFez(process.getFaz() + process.getDuration());
        }
    }

    public void calculateSAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setSaz(process.getSez() - process.getDuration());
        }
    }

    public void calculateSEZ(List<Process> listOFProcesses) {
        if (listOFProcesses.isEmpty()) {
            return process.getFez();
        }

        int minSAZ = Integer.MAX_VALUE;

        for (Process process : listOFProcesses) {
            if (process.getSaz() < minSAZ) {
                minSAZ = process.getSaz();
            }
        }
    }

    public void calculateFP(List<Process> listOFProcesses) {
        Process successor = null;
        int index = 0;

        for (Process process : listOFProcesses) {
            index++;

            // Das erste Arbeitspaket wird anders behandelt
            if (index == 1) {
                process.setGp(0);
                process.setFp(0);
                continue;
            }
            //TODO:: Was ist wenn wir zwei Nachfolger haben
            process.setFp(listOFProcesses.get(index).getFaz() - process.getFez());
        }
    }



    public void calculateGP(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setGp();
        }
    }

    public void getSuccessor(List<Process> listofProcesses) {
        for (int i = 0; i < listofProcesses.size(); i++) {

        }
    }
}
