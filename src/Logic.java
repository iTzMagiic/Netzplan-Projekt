import java.util.*;

public class Logic {

    Scanner scanner;
    Database database;
    UserSession userSession;


    public Logic() {
        scanner = new Scanner(System.in);
        userSession = UserSession.getUserSession();
        database = new Database();
    }

//    public void loadAllObjects() {
//        NetworkplanList.setNetworkplanList(database.getAllNetworkplans(userSession.getUserID()));
//        List<Process> listOfProcesses;
//
//        if (NetworkplanList.getAllNetworkplans() == null) {return;}
//
//        // Lädt alle Knoten in ein Netzplan, falls vorhanden
//        for (Networkplan networkplan : NetworkplanList.getAllNetworkplans()) {
//            listOfProcesses = database.getAllProcesses(networkplan.getNetworkplanID());
//
//            if (listOfProcesses == null) {continue;}
//
//            networkplan.setListOfProcesses(listOfProcesses);
//
//            for (Process process : listOfProcesses) {
//                List<Integer> listOfDependenciesInteger = database.getAllDependencies(process.getProcessID());
//                if (listOfDependenciesInteger == null) {continue;}
//
//                List<Process> listOfDependenciesProcesses = new ArrayList<>();
//
//                for (Integer dependency : listOfDependenciesInteger) {
//                    // Suche den Prozess in networkplan.getListOfProcesses()
//                    networkplan.getListOfProcesses().stream()
//                            .filter(p -> p.getProcessID() == dependency)
//                            .findFirst() // Hole den ersten passenden Prozess, falls vorhanden
//                            .ifPresent(listOfDependenciesProcesses::add); // Füge ihn zur Liste hinzu, falls er existiert
//                }
//                process.setDependencies(listOfDependenciesProcesses);
//            }
//
//            for (Process process : listOfProcesses) {
//                List<Integer> listOfSuccessorInteger = database.getAllSuccessor(process.getProcessID());
//                if (listOfSuccessorInteger == null) {continue;}
//
//                List<Process> listOfSuccessorProcesses = new ArrayList<>();
//
//                for (Integer successor : listOfSuccessorInteger) {
//                    networkplan.getListOfProcesses().stream()
//                            .filter(p -> p.getProcessID() == successor)
//                            .findFirst() // Hole den ersten passenden Prozess, falls vorhanden
//                            .ifPresent(listOfSuccessorProcesses::add); // Füge ihn zur Liste hinzu, falls er existiert
//                }
//                process.setSuccessors(listOfSuccessorProcesses);
//            }
//        }
//    }

    public void loadAllObjects() {
        // Lade alle Netzwerkpläne des Benutzers
        NetworkplanList.setNetworkplanList(database.getAllNetworkplans(userSession.getUserID()));
        if (NetworkplanList.getAllNetworkplans() == null || NetworkplanList.getAllNetworkplans().isEmpty()) {

            return;
        }

        // Verarbeite jeden Netzwerkplan
        for (Networkplan networkplan : NetworkplanList.getAllNetworkplans()) {
            List<Process> listOfProcesses = database.getAllProcesses(networkplan.getNetworkplanID());
            if (listOfProcesses.isEmpty()) { continue; }

            networkplan.setListOfProcesses(listOfProcesses);

            // Lade Abhängigkeiten und Nachfolger
            for (Process process : listOfProcesses) {
                List<Integer> dependenciesIds = database.getAllDependencies(process.getProcessID());
                process.setDependencies(mapDependenciesOrSuccessors(dependenciesIds, listOfProcesses));

                List<Integer> successorIds = database.getAllSuccessor(process.getProcessID());
                process.setSuccessors(mapDependenciesOrSuccessors(successorIds, listOfProcesses));
            }
        }
    }

    private List<Process> mapDependenciesOrSuccessors(List<Integer> ids, List<Process> allProcesses) {
        return ids.stream()
                .map(id -> allProcesses.stream()
                        .filter(p -> p.getProcessID() == id)
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }


    public void deleteNetworkplan(Networkplan networkplan) {
        database.deleteNetworkplanFromDatabase(networkplan.getNetworkplanID());
        NetworkplanList.getAllNetworkplans().removeIf(networkplanSearching -> networkplanSearching.getNetworkplanID() == networkplan.getNetworkplanID());
    }

    public boolean loginToDatabase(String username, String password) {
        int userID = database.getUserID(username, password);

        if (userID == -1) {return false;}

        userSession = UserSession.getUserSession();
        userSession.setUserID(userID);
        loadAllObjects();
        return true;
    }

    public boolean createAccount(String username, String password) {

        if (!Rules.isUsernameValid(username) && !Rules.isPasswordValid(password)) {
            System.out.println("Username oder Passwort nicht lang genug.");
            return false;
        }

        userSession.setUserID(database.createAccount(username, password));
        if (userSession.getUserID() == -1) {return false;}

        return true;
    }


    public Networkplan addNetworkplan(String networkplanName) {
        database.createNetworkplan(userSession.getUserID(), networkplanName);
        int networkplanID = database.getNetwokrplanID(userSession.getUserID(), networkplanName);

        Networkplan networkplan = new Networkplan(networkplanName, networkplanID);
        NetworkplanList.addNetworkplan(networkplan);

        return networkplan;
    }


    public void addProcessToNetworkplan(Networkplan networkplan, Process process) {
        networkplan.addProcess(process);
    }

    public Process createProcess(Networkplan networkplan, String processName, int processNumber, int duration) {
        database.addProcessToNetworkplan(networkplan.getNetworkplanID(), processName, duration, processNumber);
        int processID = database.getProcessID(networkplan.getNetworkplanID(), processName);

        Process process = new Process (processName, processNumber, duration, processID);
        networkplan.addProcess(process);
        return process;
    }




    public boolean isAllowedToCreateDependencies(Networkplan networkplan) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() > 1) {
                return true;
            }
        }
        return false;
    }


    public boolean isCorrectDependencies(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return true;
            }
        }
        return false;
    }


    public boolean isDeletingAllDependenciesAndSuccessorFromProcess(Networkplan networkplan, Process process) {
        if (askYesOrNo("Möchten Sie alle Vorgänger löschen?")) {
            process.setDependencies(new ArrayList<>());
            database.deleteDependencies(process.getProcessID());
            deleteSelectedProcessFromAllDependenciesList(networkplan, process);
            return true;
        }
        return false;
    }


    public void deleteSelectedProcessFromAllDependenciesList(Networkplan networkplan, Process deleteProcess) {
        for (Process process : networkplan.getListOfProcesses()) {
            for (Process successor : process.getListOfSuccessors()) {
                if (successor.getNr() == deleteProcess.getNr()) {
                    process.deleteSuccessor(successor);
                    database.deleteSelectedProcessFromSuccessor(process.getProcessID(), successor.getProcessID());
                    break;
                }
            }
        }
    }


    public void addDependencies(Networkplan networkplan, Process process) {
        List<Process> listOfDependencies = new ArrayList<>(); // Liste für die Angegebenen Vorgänger
        int dependencie;

        do {
            do {
                if (!listOfDependencies.isEmpty()) { // Gibt die Aktuellen Vorgänger wieder als übersicht
                    System.out.print("Aktuelle Vorgänger: ");
                    for (int i = 0; i < listOfDependencies.size(); i++) {
                        System.out.printf("%d, ", listOfDependencies.get(i).getNr());
                    }
                    System.out.println("\n");
                }
                //TODO:: Eine Liste aller möglichen Vorgänger hier angeben im Sysout
                dependencie = readInt("Bitte geben Sie ein Vorgänger an ('0' zum Abbrechen): ");


                if (dependencie == process.getNr()) {
                    consoleClear();
                    System.out.println("Bitte nicht den eigenen Knoten angeben!");
                    dependencie = -1;
                    continue;
                }

                if (dependencie > process.getNr()) {
                    consoleClear();
                    System.out.println("Nur Vorgänger keine Nachfolger!");
                    dependencie = -1;
                    continue;
                }

                if (dependencie != 0 && !isCorrectDependencies(networkplan, dependencie)) {
                    consoleClear();
                    System.out.println("Bitte nur ein Existierenden Vorgänge angeben!");
                    dependencie = -1;
                    continue;
                }

                if (listOfDependencies.contains(getSelectedProcessForDependenciesList(networkplan, dependencie))) {
                    consoleClear();
                    System.out.println("Bitte nicht den gleichen Vorgänger angeben!");
                    dependencie = -1;   // damit die Schleife wiederholt wird
                    continue;
                }
                consoleClear();
            } while (dependencie < 0);

            if (dependencie == 0) {
                break;
            } // 0 == Abbrechen

            listOfDependencies.add(getSelectedProcessForDependenciesList(networkplan, dependencie));


            consoleClear();

        } while (isMoreDependenciesAllowed(networkplan, process, listOfDependencies) && askYesOrNo("Möchten Sie ein weiteren Vorgänger hinzufügen?"));
        consoleClear();

        if (listOfDependencies.isEmpty()) {
            return;
        }
        listOfDependencies.sort(Comparator.comparingInt(Process::getNr));

        setSuccessor(listOfDependencies, process);

        process.setDependencies(listOfDependencies);
        for (Process dependency : process.getListOfDependencies()) {
            database.addDependenciesToProcess(process.getProcessID(), dependency.getProcessID());
        }
    }


    public void editProcessDependencies(Networkplan networkplan, Process process) {
        if (process.getNr() == 1) {
            return;
        }
        if (process.getListOfDependencies() != null) {
            if (!isDeletingAllDependenciesAndSuccessorFromProcess(networkplan, process)) {
                return;
            }
            consoleClear();
        }
        addDependencies(networkplan, process);
    }

    public void editProcessDuration(Process process) {
        while (true) {
            consoleClear();
            System.out.printf("Bearbeiten der Dauer : %d von dem Knoten : %S\n", process.getDuration(), process.getName());
            int newDuration = readInt("Neue Dauer ('0' Zurück) : ");
            if (newDuration == 0) {
                break;
            } else if (newDuration < 0) {
                System.out.println("Bitte nur echte Angaben!");
                continue;
            }
            process.setDuration(newDuration);
            database.changeProcessDuration(process.getProcessID(), newDuration);
            break;
        }
    }

    public void editProcessName(Process process) {
        System.out.printf("Bearbeiten des Namen von : %S\n", process.getName());
        String newProcessName = readString("Neuer Name ('0' Zurück) : ");
        if (newProcessName.length() == 1 && newProcessName.charAt(0) == '0') {
            return;
        }
        process.setName(newProcessName);
        database.changeProcessName(process.getProcessID(), newProcessName);
    }


    public void setSuccessor(List<Process> listOfDependencies, Process successor) {
        for (Process process : listOfDependencies) {
            //TODO:: HIER IST EIN FEHLER !!! MIT ADDSUCCESSOR()
            process.addSuccessor(successor);
            database.addSuccessorToProcess(process.getProcessID(), successor.getProcessID());
        }
    }


    public boolean isMoreDependenciesAllowed(Networkplan networkplan, Process process, List<Process> listOfDependencies) {
        for (Process checkProcess : networkplan.getListOfProcesses()) {
            if (checkProcess.getNr() != process.getNr() && !listOfDependencies.contains(checkProcess) && checkProcess.getNr() < process.getNr()) {
                return true;
            }
        }
        return false;
    }


    public Process getSelectedProcessForDependenciesList(Networkplan networkplan, int nr) {
        for (Process process : networkplan.getListOfProcesses()) {
            if (process.getNr() == nr) {
                return process;
            }
        }
        return null;
    }


    public String readString(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
        return scanner.nextLine();
    }


    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Bitte eine gültige Zahl eingeben: ");
            scanner.next();
        }
        return scanner.nextInt();
    }


    public boolean askYesOrNo(String message) {
        int choice;
        do {
            choice = readInt(message + " ('1' Ja, '2' Nein): ");
        } while (choice < 1 || choice > 2);
        return choice == 1;
    }


    public void consoleClear() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }


    public void startCalculate(Networkplan networkplan) {
        List<Process> listOfProcesses = networkplan.getListOfProcesses();

        CalculationProcess.calculateAll(listOfProcesses);
    }
}
