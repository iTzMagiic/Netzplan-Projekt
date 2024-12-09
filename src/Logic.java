import java.util.ArrayList;
import java.util.Scanner;

public class Logic {

    Scanner scanner;

    public Logic() {
        scanner = new Scanner(System.in);
    }



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

    // Hier wird ein Array aus ArrayList erstellt und zurückgegeben
    public int[] getDependenciesArray(ArrayList<Integer> listOfDependencies) {
        int[] dependenciesArray = new int[listOfDependencies.size()];
        for (int i = 0; i < listOfDependencies.size(); i++) {
            dependenciesArray[i] = listOfDependencies.get(i);
        }
        return dependenciesArray;
    }

    public boolean deleteDependencies(Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(null);
            return true;
        }
        return false;
    }

    private boolean askYesOrNo(String message) {
        int choice;
        do {
            choice = readInt(message + " ('1' Ja, '2' Nein): ");
        } while (choice < 1 || choice > 2);
        return choice == 1;
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Bitte eine gültige Zahl eingeben: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
