//import java.util.List;
//
//public class CalculationProcess {
//
//
//    /*TODO::  Die Methode calculateStart muss vor allen anderen aufgerufen werden
//     */
//    public static void calculateStart(List<Process> listOfProcesses) {
//        // Prüfen ob jemand kein Vorgänger hat wenn ja Faz und Fez setzten
//        for (Process process : listOfProcesses) {
//            if (process.getListOfDependencies() == null) {
//                process.setFez(process.getDuration());
//                process.setFaz(0);
//            }
//        }
//    }
//
//
//    public static void calculateFAZ(List<Process> listOFProcesses) {
//        for (Process process : listOFProcesses) {
//            if (process.getListOfDependencies() == null) {continue;}
//
//            int maxFEZ = 0;
//
//            for (Process predecessor : process.getListOfDependencies()) {
//                if (predecessor.getFez() > maxFEZ) {
//                    maxFEZ = predecessor.getFez();
//                }
//            }
//            process.setFaz(maxFEZ);
//        }
//    }
//
//    public static void calculateFEZ(List<Process> listOFProcesses) {
//        for (Process process : listOFProcesses) {
//            if (process.getListOfDependencies() == null) {continue;}
//            process.setFez(process.getFaz() + process.getDuration());
//        }
//    }
//
//
//    public static void calculateSEZ(List<Process> listOFProcesses) {
//        for (int i = listOFProcesses.size() - 1; i >= 0; i--) {
//            Process process = listOFProcesses.get(i);
//
//            if (i == listOFProcesses.size() - 1) {
//                process.setSez(process.getFez());
//            } else {
//                int minSAZ = Integer.MAX_VALUE;
//                for (Process sucessor : listOFProcesses) {
//                    //TODO:: Nullpoint fehler ohne die If bedingung
//                    if (sucessor.getListOfDependencies() == null) {continue;}
//                    for (Process dependencyIndex : sucessor.getListOfDependencies()) {
//
//                        if (dependencyIndex.getNr() == i) {
//                            if (sucessor.getSaz() < minSAZ) {
//                                minSAZ = sucessor.getSaz();
//                            }
//                        }
//                    }
//                }
//                process.setSez(minSAZ);
//            }
//        }
//    }
//
//
//    public static  void calculateSAZ(List<Process> listOFProcesses) {
//        for (Process process : listOFProcesses) {
//            process.setSaz(process.getSez() - process.getDuration());
//        }
//    }
//
//    //alte Fp Berechnung
//    /*
//    public void calculateFP(List<Process> listOFProcesses) {
//        Process successor = null;
//        int index = 0;
//
//        for (Process process : listOFProcesses) {
//            index++;
//
//            // Das erste Arbeitspaket wird anders behandelt
//            if (index == 1) {
//                process.setGp(0);
//                process.setFp(0);
//                continue;
//            }
//            //TODO:: Was ist wenn wir zwei Nachfolger haben
//            process.setFp(listOFProcesses.get(index).getFaz() - process.getFez());
//        }
//    }
//
//     */
//
//    public static void calculateGP(List<Process> listOFProcesses) {
//        for (Process process : listOFProcesses) {
//            process.setGp(process.getSaz() - process.getFaz());
//        }
//    }
//
//    public static void calculateFP(List<Process> listOFProcesses) {
//        for (int i = 0; i < listOFProcesses.size(); i++) {
//            Process process = listOFProcesses.get(i);
//
//            int minFAZSuccessor = Integer.MAX_VALUE;
//            for (Process sucessor : listOFProcesses) {
//                //TODO:: Nullpoint Fehler ohne die If bedingung
//                if (sucessor.getListOfDependencies() == null) {continue;}
//                for (Process dependencyIndex : sucessor.getListOfDependencies()) {
//                    if (dependencyIndex.getNr() == i) {
//                        if (sucessor.getFaz() < minFAZSuccessor) {
//                            minFAZSuccessor = sucessor.getFaz();
//                        }
//                    }
//                }
//            }
//            if (minFAZSuccessor == Integer.MAX_VALUE) {
//                process.setFp(0);
//            } else {
//                process.setFp(minFAZSuccessor - process.getFez());
//            }
//        }
//    }
//
//}



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculationProcess {

    /*TODO:: Die Methode calculateStart muss vor allen anderen aufgerufen werden */
    public static void calculateStart(List<Process> listOfProcesses) {
        // Prüfen, ob jemand kein Vorgänger hat. Wenn ja, FAZ und FEZ setzen
        for (Process process : listOfProcesses) {
            if (process.getListOfDependencies() == null) {
                process.setFez(process.getDuration());
                process.setFaz(0);
                process.setSaz(process.getFaz());
                process.setSez(process.getFez());
            }
        }
    }

    public static void calculateFAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            if (process.getListOfDependencies() == null || process.getListOfDependencies().isEmpty()) {
                continue;
            }

            int maxFEZ = 0;

            for (Process predecessor : process.getListOfDependencies()) {
                if (predecessor.getFez() > maxFEZ) {
                    maxFEZ = predecessor.getFez();
                }
            }
            process.setFaz(maxFEZ);
        }
    }

    public static void calculateFEZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            if (process.getListOfDependencies() == null) {continue;}
            process.setFez(process.getFaz() + process.getDuration());
        }
    }

//    public static void calculateSEZ(List<Process> listOFProcesses) {
//        for (int i = listOFProcesses.size() - 1; i >= 0; i--) {
//            Process process = listOFProcesses.get(i);
//
//            if (i == listOFProcesses.size() - 1) {
//                // Letzter Prozess: SEZ = FEZ
//                process.setSez(process.getFez());
//            } else {
//                int minSAZ = Integer.MAX_VALUE;
//
//                for (Process successor : getSuccessors(listOFProcesses, process)) {
//                    if (successor.getSaz() < minSAZ) {
//                        minSAZ = successor.getSaz();
//                    }
//                }
//                process.setSez(minSAZ);
//            }
//        }
//    }

    public static void calculateSEZ(List<Process> listOFProcesses) {
        for (int i = listOFProcesses.size() - 1; i >= 0; i--) {
            Process process = listOFProcesses.get(i);
            if (process.getListOfDependencies() == null) {continue;}

            // Prüfe Nachfolger
            List<Process> successors = getSuccessors(listOFProcesses, process);
            if (!successors.isEmpty()) {
                int minSAZ = Integer.MAX_VALUE;
                for (Process successor : successors) {
                    if (successor.getSaz() < minSAZ) {
                        minSAZ = successor.getSaz();
                    }
                }
                process.setSez(minSAZ);
            } else {
                // Kein Nachfolger: SEZ = FEZ
                process.setSez(process.getFez());
            }
        }
    }


    public static void calculateSAZ(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            if (process.getListOfDependencies() == null) {continue;}
            process.setSaz(process.getSez() - process.getDuration());
        }
    }


    public static void calculateGP(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            process.setGp(process.getSez() - process.getFez());
        }
    }


    // Methode, um spätesten Anfangszeitpunkt und spätesten Endzeitpunkt zu berechnen
    public static void berechnenSZ(List<Process> arbeitspaketListe) {

        Process letztesPaket = arbeitspaketListe.get(arbeitspaketListe.size() - 1);                                    // Initialisierung für das letzte Arbeitspaket
        letztesPaket.setSez(letztesPaket.getFez());                                                                         // berechnet Sez des letzten APs
        letztesPaket.setSaz(letztesPaket.getSez() - letztesPaket.getDuration());                                               // berechnet Saz des letzten APs

        for (int i = arbeitspaketListe.size() - 2; i >= 0; i--) {                                                           // Berechnen von SEZ und SAZ für alle Arbeitspakete rückwärts
            Process aktuellesPaket = arbeitspaketListe.get(i);
            double minSez = Double.MAX_VALUE;

            if (aktuellesPaket.getListOfDependencies() == null) {continue;}// setzt größtmöglichen double Wert

            for (Process nachfolgerPaket : aktuellesPaket.getListOfDependencies()) {                                                    // Finden des kleinsten SAZ der Nachfolger
                minSez = Math.min(minSez, nachfolgerPaket.getSaz());
            }

            if (minSez == Double.MAX_VALUE) {                                                                               // wenn minSez entspricht größtmöglichem double Wert -> minSez entspricht Fez des letzten APs
                minSez = letztesPaket.getFez();
            }
            // kleinste Sez wird verwendet, um Saz des aktuellen APs zu berechnen
            aktuellesPaket.setSez((int)minSez);
            aktuellesPaket.setSaz((int)minSez - aktuellesPaket.getDuration());
        }
    }


    public static void berechnenPuffer(List<Process> arbeitspaketListe) {

        Process letztesPaket = arbeitspaketListe.get(arbeitspaketListe.size() - 1);                                   // Initialisierung des letzten Arbeitspakets
        letztesPaket.setGp(0);                                                                                             // GP des letzten AP wird auf 0 gesetzt
        letztesPaket.setFp(0);                                                                                             // FP des letzten AP wird auf 0 gesetzt

        for (int i = arbeitspaketListe.size() - 1; i >= 0; i--) {                                                          // Schleife über die ArbeitspaketListe von hinten
            Process aktuellesPaket = arbeitspaketListe.get(i);

            aktuellesPaket.setGp(aktuellesPaket.getSaz() - aktuellesPaket.getFaz());                                       // Berechnung des gesamten Puffers (GP)

            if (aktuellesPaket.getListOfDependencies() != null) {                                                               // Initialisierung des freien Puffers (FP)
                double minFp = Double.MAX_VALUE;

                for (Process nachfolgerPaket : aktuellesPaket.getListOfDependencies()) {                                               // Berechnung des freien Puffers (FP)
                    double fpZwischen = nachfolgerPaket.getFaz() - aktuellesPaket.getFez();

                    if (fpZwischen < minFp) {                                                                              // Aktualisieren des minimalen freien Puffers
                        minFp = fpZwischen;
                    }
                }

                aktuellesPaket.setFp((int)minFp);
            } else {
                aktuellesPaket.setFp(0);                                                                                   // Falls keine Nachfolger, setze den FP auf 0
            }
        }
    }

    public static void calculateFP(List<Process> listOFProcesses) {
        for (Process process : listOFProcesses) {
            int minFAZSuccessor = Integer.MAX_VALUE;

            for (Process successor : getSuccessors(listOFProcesses, process)) {
                if (successor.getFaz() < minFAZSuccessor) {
                    minFAZSuccessor = successor.getFaz();
                }
            }

            if (minFAZSuccessor == Integer.MAX_VALUE) {
                process.setFp(0); // Kein Nachfolger, also kein freier Puffer
            } else {
                process.setFp(minFAZSuccessor - process.getFez());
            }
        }
    }

    private static List<Process> getSuccessors(List<Process> processes, Process current) {
        return processes.stream()
                .filter(process -> process.getListOfDependencies() != null && process.getListOfDependencies().contains(current))
                .collect(Collectors.toList());
    }


    // Methode, die alle Berechnungen in der richtigen Reihenfolge ausführt
    public static void calculateAll(List<Process> processes) {
        calculateStart(processes);

        calculateFAZ(processes);
        calculateFEZ(processes);
//        calculateSEZ(processes);
//        calculateSAZ(processes);
        berechnenSZ(processes);
        berechnenPuffer(processes);
//        calculateFP(processes);
//        calculateGP(processes);
    }
}

