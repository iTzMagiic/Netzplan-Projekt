import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Process {

    private String name;
    private final int nr;
    private final int processID;
    private int duration;
    private int faz, fez, saz, sez, fp, gp;
    private List<Process> listOfDependenciesProcesses = new ArrayList<>();
    private List<Process> listOfSuccessors = new ArrayList<>();


    public Process(String name, int nr, int duration, int processID) {
        this.name = name;
        this.duration = duration;
        this.nr = nr;
        this.processID = processID;
    }


    public void addSuccessor(Process process) {
        listOfSuccessors.add(process);
    }

    public void deleteSuccessor(Process toDeleteProcess) {
        listOfSuccessors.removeIf(successor -> successor.equals(toDeleteProcess));
    }

    public List<Process> getListOfSuccessors() {
        return listOfSuccessors;
    }

    public void setDependencies(List<Process> dependenciesProcess) {
        this.listOfDependenciesProcesses = dependenciesProcess;
    }

    public void setSuccessors(List<Process> newSuccessors) {
        this.listOfSuccessors = newSuccessors;
    }

    public String getDependenciesAsString() {
        if (listOfDependenciesProcesses != null) {
            return listOfDependenciesProcesses.stream()
                    .map(dependency -> String.valueOf(dependency.getNr())) // Nr als String umwandeln
                    .collect(Collectors.joining(", "));
        }
        return "Keine Vorgänger";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public int getProcessID() {
        return processID;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFaz() {
        return faz;
    }

    public void setFaz(int faz) {
        this.faz = faz;
    }

    public int getFez() {
        return fez;
    }

    public void setFez(int fez) {
        this.fez = fez;
    }

    public int getSaz() {
        return saz;
    }

    public void setSaz(int saz) {
        this.saz = saz;
    }

    public int getSez() {
        return sez;
    }

    public void setSez(int sez) {
        this.sez = sez;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    @Override
    public String toString() {
        return "╭────────────┬───────────┬────────────╮\n" +
                String.format("│     %-6s │     %-5s │     %-6s │\n", getFaz(), getDuration(), getFez()) +
                "├────────────┴───────────┴────────────┤\n" +
                String.format("│ %-35s │\n", getName()) +
                "├─────────┬────────┬────────┬─────────┤\n" +
                String.format("│   %-5s │   %-4s │   %-4s │   %-5s │\n", getSaz(), getFp(), getGp(), getSez()) +
                "╰─────────┴────────┴────────┴─────────╯\n";
    }

    public List<Process> getListOfDependencies() {
        return listOfDependenciesProcesses;
    }

    public int getNr() {
        return nr;
    }
}
