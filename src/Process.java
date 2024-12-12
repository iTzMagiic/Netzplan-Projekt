import java.util.List;
import java.util.stream.Collectors;

public class Process {

    private static int counter;

    private String name;
    private int nr;
    private int duration;
    private int faz, fez, saz, sez, fp, gp;
    private List<Process> listOfDependenciesProcesses;


    // Konstruktor
    public Process(String name, int nr, int duration) {
        this.name = name;
        this.duration = duration;
        this.nr = nr;
    }


    public void setDependencies(List<Process> dependenciesProcess) {
        this.listOfDependenciesProcesses = dependenciesProcess;
    }

    public String getDependenciesAsString() {
        // Wandelt die Liste der Dependencies in einen String um
        if (listOfDependenciesProcesses != null) {
            return listOfDependenciesProcesses.stream()
                    .map(dependency -> String.valueOf(dependency.getNr())) // Nr als String umwandeln
                    .collect(Collectors.joining(", "));
        }
        return "Keine Vorgänger";
    }


    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
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
        return "Paket: " + nr;
    }

    public List<Process> getListOfDependencies() {
        return listOfDependenciesProcesses;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }
}
