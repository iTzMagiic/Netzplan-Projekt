public class Logic {



    // Erstellt ein Netzplan, fügt es in die Liste der Static Klasse NetworkplanList
    // und gibt das Objekt zurück.
    public Networkplan addNetworkplan(String name) {
        Networkplan networkplan = new Networkplan(name);
        NetworkplanList.addNetworkplan(networkplan);
        return networkplan;
    }


    // Fügt ein Vorgang zu einem bestimmten Netzplan hinzu
    public void addProcessToNetworkplan(Networkplan networkplan, Process process) {
        networkplan.addProcess(process);
    }


    // Die Methode prüft, ob man überhaupt vorgänger haben kann, wenn nein dann kann man auch keine erstellen.
    public boolean isAllowedToCreateDependencies(Networkplan networkplan) {
        for (Process process : networkplan.getListOfProcesses()) {
            // Wenn mehr als 1 Knoten existiert dann True
            if (process.getNr() > 1) {
                return true;
            }
        }
        return false;
    }


    // Prüft, ob der Vorgänger auch wirklich existiert
    public boolean isCorrectDependencies(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return true;
            }
        }
        return false;
    }
}
