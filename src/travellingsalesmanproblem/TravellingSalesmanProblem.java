package travellingsalesmanproblem;

import org.fusesource.jansi.AnsiConsole;

public class TravellingSalesmanProblem {
    private static String[] cities;
    private static int[][] distances;
    
    public static final int NORMAL_MODE = 1;
    public static final int ANALYZE_MODE = 2;
    private static int programMode = NORMAL_MODE;
    
    public static void main(String[] args) {
        cities = ProblemDatas.getCitiesArray();
        distances = ProblemDatas.getDistancesArray();
        while(menu());
    }
    
    public static int getProgramMode(){return programMode;}
    
    public static void dynamicSolve(){
        StylishPrinter.println("\nDynamic Programming Solving:", StylishPrinter.BOLD_RED);
        boolean printLogs;
        if(programMode == NORMAL_MODE) printLogs = wantLogs(false);
        else printLogs = false;
        
        TSPRunner tspRunnable = new TSPRunner(TSPRunner.DYNAMIC_PROGRAMMING, cities, distances);
        tspRunnable.setPrintLogs(printLogs);
        tspRunnable.run();
    }
    
    public static void hillClimbingSolve(){
        StylishPrinter.println("\nHill Climbing Solving:", StylishPrinter.BOLD_RED);
        TSPRunner tspRunnable = new TSPRunner(TSPRunner.HILL_CLIMBING, cities, distances);
        tspRunnable.run();
    }
    
    public static void simAnnealingSolve(){
        StylishPrinter.println("\nSimulated Annealing Solving:", StylishPrinter.BOLD_RED);
        int sugMaxIterations, maxIterations;
        double sugCoolingRate, coolingRate;
        
        if(cities.length<=5){
            sugMaxIterations = 500;
            sugCoolingRate = 0.996;
        }
        else if(cities.length<=15){
            sugMaxIterations = 10000;
            sugCoolingRate = 0.9996;
        }
        else{
            sugMaxIterations = 25000;
            sugCoolingRate = 0.9996;
        }
        
        int choose;
        
        System.out.println("Enter Maximum Iterations: ");
        System.out.println("1: " + sugMaxIterations);
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) maxIterations = sugMaxIterations;
        else{
            System.out.println("\nEnter Desired Value: ");
            maxIterations = SbproScanner.inputInt(1, Integer.MAX_VALUE);
        }
        
        System.out.println("\nEnter Cooling Rate: ");
        System.out.println("1: " + sugCoolingRate);
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) coolingRate = sugCoolingRate;
        else{
            System.out.println("\nEnter Desired Value: ");
            coolingRate = SbproScanner.inputDouble(0.0000000001, 1);
        }
        System.out.println();
        
        TSPRunner tspRunnable = new TSPRunner(TSPRunner.SIMULATED_ANNEALING, cities, distances);
        tspRunnable.setMaxIteration(maxIterations);
        tspRunnable.setCoolingRate(coolingRate);
        tspRunnable.run();
    }
    
    public static void geneticSolve(){
        StylishPrinter.println("\nGenetic Solving:", StylishPrinter.BOLD_RED);
        int sugMaxGenerations, maxGenerations, sugPopulations, populations;
        double sugMutationProb, mutationProb;
        int crossoverMode, selectionMode;
        boolean printLogs;
        
        if(cities.length<=5){
            sugMaxGenerations = 50;
            sugPopulations = 10;
            sugMutationProb = 0.1;
        }
        else if(cities.length<=15){
            sugMaxGenerations = 400;
            sugPopulations = 30;
            sugMutationProb = 0.1;
        }
        else{
            sugMaxGenerations = 2500;
            sugPopulations = 40;
            sugMutationProb = 0.1;
        }
        
        int choose;
        
        System.out.println("Enter Maximum Generations: ");
        System.out.println("1: " + sugMaxGenerations);
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) maxGenerations = sugMaxGenerations;
        else{
            System.out.println("\nEnter Desired Value: ");
            maxGenerations = SbproScanner.inputInt(1, Integer.MAX_VALUE);
        }
        
        System.out.println("\nEnter Populations Number: ");
        System.out.println("1: " + sugPopulations);
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) populations = sugPopulations;
        else{
            System.out.println("\nEnter Desired Value: ");
            populations = SbproScanner.inputInt(1, 10000);
        }
        
        System.out.println("\nEnter Mutation Probablity: ");
        System.out.println("1: " + sugMutationProb);
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) mutationProb = sugMutationProb;
        else{
            System.out.println("\nEnter Desired Value: ");
            mutationProb = SbproScanner.inputDouble(0.0000000001, 1);
        }
        
        System.out.println("\nEnter Selection Mode: ");
        System.out.println("1: Rank Selection");
        System.out.println("2: Roulette Wheel Selection");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) selectionMode = TSPSolutions.GeneticTSPSolver.RANK_SELECTION;
        else selectionMode = TSPSolutions.GeneticTSPSolver.ROULETTE_WHEEL_SELECTION;
        
        System.out.println("\nEnter Crossover Mode: ");
        System.out.println("1: Edge Recombination Crossover");
        System.out.println("2: Civil Crossover");
        System.out.print("Your Choice: ");
        choose = SbproScanner.inputInt(1, 2);
        if(choose==1) crossoverMode = TSPSolutions.GeneticTSPSolver.EDGE_CROSSOVER;
        else crossoverMode = TSPSolutions.GeneticTSPSolver.CIVIL_CROSSOVER;
        
        System.out.println();
        if(programMode == NORMAL_MODE) printLogs = wantLogs(false);
        else printLogs=false;
        
        TSPRunner tspRunnable = new TSPRunner(TSPRunner.GENETIC, cities, distances);
        tspRunnable.setMaxGenerations(maxGenerations);
        tspRunnable.setPopulations(populations);
        tspRunnable.setMutationProb(mutationProb);
        tspRunnable.setSelectionMode(selectionMode);
        tspRunnable.setCrossoverMode(crossoverMode);
        tspRunnable.setPrintLogs(printLogs);
        tspRunnable.run();
    }
    
    public static void changeCitiesList(){
        StylishPrinter.println("\nChange Cities List:", StylishPrinter.BOLD_RED);
        boolean firstShow=true;
        
        while(true){
            if(firstShow) firstShow=false;
            else System.out.println();
            
            System.out.println("Cities List: ");
            if(ProblemDatas.getListId() == ProblemDatas.TINY_CITIES_LIST)
                StylishPrinter.println("1: Tiny Lists (Include 5 Cities)", StylishPrinter.BOLD_GREEN);
            else System.out.println("1: Tiny Lists (Include 5 Cities)");

            if(ProblemDatas.getListId() == ProblemDatas.SMALL_CITIES_LIST)
                StylishPrinter.println("2: Small Lists (Include 10 Cities)", StylishPrinter.BOLD_GREEN);
            else System.out.println("2: Small Lists (Include 10 Cities)");

            if(ProblemDatas.getListId() == ProblemDatas.FULL_CITIES_LIST)
                StylishPrinter.println("3: Full Lists (Include 59 Cities)", StylishPrinter.BOLD_GREEN);
            else System.out.println("3: Full Lists (Include 59 Cities)");

            System.out.println("4: Back to main menu");
            System.out.print("Enter Your Choice or Exit: ");
            int choose = SbproScanner.inputInt(1, 4);
            
            if(choose!=4){
                if(choose==1) ProblemDatas.changeCitiesList(ProblemDatas.TINY_CITIES_LIST);
                else if(choose==2) ProblemDatas.changeCitiesList(ProblemDatas.SMALL_CITIES_LIST);
                else ProblemDatas.changeCitiesList(ProblemDatas.FULL_CITIES_LIST);
                
                cities = ProblemDatas.getCitiesArray();
                distances = ProblemDatas.getDistancesArray();
            }
            else break;
        }
    }
    
    public static boolean isNormalMode(){
        return programMode==NORMAL_MODE;
    }
    
    public static boolean wantLogs(boolean typeEnter){
        boolean printLogs;
        if(typeEnter) System.out.println();
        System.out.println("Do you want the logs to be printed?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.print("Your Choice: ");
        int choose = SbproScanner.inputInt(1, 2);
        System.out.println();
        if(choose==1) return true;
        else return false;
    }
    
    public static boolean wantLogs(){
        return wantLogs(true);
    }
    
    public static int howManyRunNumber(boolean typeEnter){
        if(typeEnter) System.out.println();
        System.out.println("How many times does the algorithm run?");
        System.out.println("1: 100");
        System.out.println("2: Enter Another Value");
        System.out.print("Your Choice: ");
        int choose = SbproScanner.inputInt(1, 2);
        if(choose==1) return 100;
        
        System.out.println("\nEnter Desired Value: ");
        return SbproScanner.inputInt(1, 10000);
    }
    
    public static int howManyRunNumber(){
        return howManyRunNumber(true);
    }
    
    public static String getSecRuntimeString(long runtime){
        double secTime = (double)(runtime)/1000000000;
        int decimalsNum;
        if(secTime<0.0001) decimalsNum=5;
        else if(secTime<0.001) decimalsNum=4;
        else decimalsNum=3;
        return SbproPrinter.roundDouble(secTime, decimalsNum);
    }
    
    public static void printRuntime(long runtime, String fgColor, String bgColor){
        StylishPrinter.println("Runtime: " + getSecRuntimeString(runtime), fgColor, bgColor);
    }
    
    public static void printRuntime(long runtime){
        printRuntime(runtime, StylishPrinter.BOLD_YELLOW, StylishPrinter.BG_CYAN);
    }
    
    public static boolean menu(){
        StylishPrinter.println("\nMenu:", StylishPrinter.BOLD_RED);
        System.out.println("1: Dynamic Programming Solve");
        System.out.println("2: Hill Climbing Solve");
        System.out.println("3: Simulated Annealing Solve");
        System.out.println("4: Genetic Solve");
        System.out.println("5: Change Cities List");
        if(programMode == NORMAL_MODE)
            System.out.println("6: Analyse Mode");
        else System.out.println("6: Normal Mode");
        System.out.println("7: Exit");
        System.out.print("\nEnter Your Choice: ");
        int choice = SbproScanner.inputInt(1, 7);
        
        if(choice==1) dynamicSolve();
        else if(choice==2) hillClimbingSolve();
        else if(choice==3) simAnnealingSolve();
        else if(choice==4) geneticSolve();
        else if(choice==5) changeCitiesList();
        else if(choice==6){
            if(programMode == NORMAL_MODE) programMode = ANALYZE_MODE;
            else programMode = NORMAL_MODE;
        }
        else if(choice==7) return false;
        
        return true;
    }
}
