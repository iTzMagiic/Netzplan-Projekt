import java.util.ArrayList;
import java.util.List;

public class Networkplan2 {

    private String name;
    private int processCounter;
    private List<Process> listOfProcesses;



    // Konstruktor
    public Networkplan2(String name) {
        this.name = name;

        // Jeder neuer Netzplan erstellt seine eigene Liste
        listOfProcesses = new ArrayList<>();
    }


    // Fügt der Liste ein neuen vorgang hinzu
    public void addProcess(Process process) {
        listOfProcesses.add(process);
    }


    public void overrideListOfProcesses(List<Process> newListOfProcesses) {
        this.listOfProcesses = newListOfProcesses;
    }

    public List<Process> getListOfProcesses() {
        return listOfProcesses;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
