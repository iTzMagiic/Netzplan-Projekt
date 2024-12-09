//import java.util.List;
//
//public class Main2 {
//    public static void main(String[] args) {
//        Process processA = new Process("A", 5, new int[]{});
//        Process processB = new Process("B", 3, new int[]{0});
//        Process processC = new Process("C", 4, new int[]{0});
//        Process processD = new Process("D", 6, new int[]{1, 2});
//
//        CalculationProcess calculator = new CalculationProcess();
//
//        List<Process> processes = List.of(processA, processB, processC, processD);
//
//        calculator.calculateFAZ(processes);
//        calculator.calculateFEZ(processes);
//        calculator.calculateSEZ(processes);
//        calculator.calculateSAZ(processes);
//        calculator.calculateGP(processes);
//        calculator.calculateFP(processes);
//
//        for (Process process : processes) {
//            System.out.println(process.getName() + " -> FAZ: " + process.getFaz() + ", FEZ: " + process.getFez()
//                    + ", SAZ: " + process.getSaz() + ", SEZ: " + process.getSez()
//                    + ", GP: " + process.getGp() + ", FP: " + process.getFp());
//        }
//
//        for (Process process : processes) {
//            System.out.println("Process: " + process.getName());
//            System.out.println("Dependencies: ");
//            for (int dependencyIndex : process.getDependencies()) {
//                System.out.println("  Depends on process at index: " + dependencyIndex);
//            }
//        }
//    }
//}
