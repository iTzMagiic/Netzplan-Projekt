import java.util.List;

public class CalculationProcess {

    public void calculateFAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            int maxFEZ = 0;

            for (int dependencyIndex : process.getDependencies()) {
                Process predecessor = listOFProcesses.get(dependencyIndex);
                if (predecessor.getFez() > maxFEZ) {
                    maxFEZ = predecessor.getFez();
                }
            }
            process.setFaz(maxFEZ);
        }

    }

    public void calculateFEZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setFez(process.getFaz() + process.getDuration());
        }
    }

    public void calculateSEZ(List<Process> listOFProcesses) {
        for (int i = listOFProcesses.size() - 1; i >= 0; i--) {
            Process process = listOFProcesses.get(i);

            if (i == listOFProcesses.size() - 1) {
                process.setSez(process.getFez());
            } else {
                int minSAZ = Integer.MAX_VALUE;
                for (Process sucessor : listOFProcesses) {
                    for (int dependencyIndex : sucessor.getDependencies()) {
                        if (dependencyIndex == i) {
                            if (sucessor.getSaz() < minSAZ) {
                                minSAZ = sucessor.getSaz();
                            }
                        }
                    }
                }
                process.setSez(minSAZ);
            }
        }
    }


    public void calculateSAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setSaz(process.getSez() - process.getDuration());
        }
    }

    //alte Fp Berechnung
    /*
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

     */

    public void calculateGP(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setGp(process.getSaz() - process.getFaz());
        }
    }

    public void calculateFP(List<Process> listOFProcesses) {
        for (int i = 0; i < listOFProcesses.size(); i++) {
            Process process = listOFProcesses.get(i);

            int minFAZSuccessor = Integer.MAX_VALUE;
            for (Process sucessor : listOFProcesses) {
                for (int dependencyIndex : sucessor.getDependencies()) {
                    if (dependencyIndex == i) {
                        if (sucessor.getFaz() < minFAZSuccessor) {
                            minFAZSuccessor = sucessor.getFaz();
                        }
                    }
                }
            }
            if (minFAZSuccessor == Integer.MAX_VALUE) {
                process.setFp(0);
            } else {
                process.setFp(minFAZSuccessor - process.getFez());
            }
        }
    }

}
