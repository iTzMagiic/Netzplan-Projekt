import java.util.ArrayList;
import java.util.List;

public class Networkplan {

    private final String name;
    private int processCounter;
    private final int networkplanID;
    private List<Process> listOfProcesses = new ArrayList<>();


    public Networkplan(String name, int networkplanID) {
        this.name = name;
        this.networkplanID = networkplanID;
    }


    public int getNetworkplanID() {
        return networkplanID;
    }


    public int incrementAndGetProcessCounter() {
        processCounter = 0;
        for (Process process : listOfProcesses) {
            if (process.getNr() > processCounter) {
                processCounter = process.getNr();
            }
        }
        processCounter++;
        return processCounter;
    }

    public void addProcess(Process process) {
        listOfProcesses.add(process);
    }

    public List<Process> getListOfProcesses() {
        return listOfProcesses;
    }

    public void setListOfProcesses(List<Process> listOfProcesses) {
        this.listOfProcesses = listOfProcesses;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
