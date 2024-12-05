import java.util.ArrayList;
import java.util.List;

public class Networkplan {

    private String name;
    private List<Process> listOfProcesses;



    public Networkplan(String name) {
        this.name = name;
        listOfProcesses = new ArrayList<>();
    }


    public void addProcess(Process process) {
        listOfProcesses.add(process);
    }

    public void addNewListOfProcesses(List<Process> newListOfProcesses) {
        this.listOfProcesses = newListOfProcesses;
    }

}
