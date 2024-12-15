import java.util.ArrayList;
import java.util.List;

public class Networkplan {

    private final String name;
    private int processCounter;
    private final List<Process> listOfProcesses = new ArrayList<>();


    public Networkplan(String name) {
        this.name = name;
    }


    public int incrementAndGetProcessCounter() {
        processCounter++;
        return processCounter;
    }

    public void addProcess(Process process) {
        listOfProcesses.add(process);
    }

    public List<Process> getListOfProcesses() {
        return listOfProcesses;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
