package travellingsalesmanproblem;

public class TravellingSalesmanProblem {
    private static String[] cities;
    private static int[][] distances;
    
    public static void main(String[] args) {
        cities = ProblemDatas.getCitiesArray();
        distances = ProblemDatas.getDistancesArray();
        while(menu());
    }
    
    public static void dynamicSolve(){
        boolean printLogs = wantLogs();
        long startTime = System.nanoTime();
        StylishPrinter.println("\nDynamic Programming Solving:", StylishPrinter.ANSI_BOLD_RED);
        TSPSolutions.solveWithDynamicSolve(cities, distances, printLogs);
        long endTime = System.nanoTime();
        double secTime = (double)(endTime - startTime)/1000000000;
        printRuntime(secTime);
    }
    
    public static void hillClimbingSolve(){
        long startTime = System.nanoTime();
        StylishPrinter.println("\nHill Climbing Solving:", StylishPrinter.ANSI_BOLD_RED);
        TSPSolutions.solveWithHillClimbing(cities, distances);
        long endTime = System.nanoTime();
        double secTime = (double)(endTime - startTime)/1000000000;
        printRuntime(secTime);
    }
    
    public static void simAnnealingSolve(){
        StylishPrinter.println("\nSimulated Annealing Solving:", StylishPrinter.ANSI_BOLD_RED);
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
        
        long startTime = System.nanoTime();
        TSPSolutions.solveWithSimulatedAnnealing(cities, distances, maxIterations, coolingRate);
        long endTime = System.nanoTime();
        double secTime = (double)(endTime - startTime)/1000000000;
        printRuntime(secTime);
    }
    
    public static void geneticSolve(){
        StylishPrinter.println("\nGenetic Solving:", StylishPrinter.ANSI_BOLD_RED);
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
        
        printLogs = wantLogs();

        System.out.println();
        
        long startTime = System.nanoTime();
        TSPSolutions.solveWithGenetic(cities, distances, maxGenerations, populations, 
                mutationProb, selectionMode, crossoverMode, printLogs);
        
        long endTime = System.nanoTime();
        double secTime = (double)(endTime - startTime)/1000000000;
        printRuntime(secTime);
    }
    
    public static void changeCitiesList(){
        StylishPrinter.println("\nChange Cities List:", StylishPrinter.ANSI_BOLD_RED);
        boolean firstShow=true;
        
        while(true){
            if(firstShow) firstShow=false;
            else System.out.println();
            
            System.out.println("Cities List: ");
            if(ProblemDatas.getListId() == ProblemDatas.TINY_CITIES_LIST)
                StylishPrinter.println("1: Tiny Lists (Include 5 Cities)", StylishPrinter.ANSI_BOLD_GREEN);
            else System.out.println("1: Tiny Lists (Include 5 Cities)");

            if(ProblemDatas.getListId() == ProblemDatas.SMALL_CITIES_LIST)
                StylishPrinter.println("2: Small Lists (Include 10 Cities)", StylishPrinter.ANSI_BOLD_GREEN);
            else System.out.println("2: Small Lists (Include 10 Cities)");

            if(ProblemDatas.getListId() == ProblemDatas.FULL_CITIES_LIST)
                StylishPrinter.println("3: Full Lists (Include 59 Cities)", StylishPrinter.ANSI_BOLD_GREEN);
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
    
    public static boolean wantLogs(){
        boolean printLogs;
        System.out.println("\nDo you want the logs to be printed?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.print("Your Choice: ");
        int choose = SbproScanner.inputInt(1, 2);
        if(choose==1) return true;
        else return false;
    }
    
    public static void printRuntime(double secTime){
        int decimalsNum;
        if(secTime<0.0001) decimalsNum=5;
        else if(secTime<0.001) decimalsNum=4;
        else decimalsNum=3;
        
        StylishPrinter.println("Runtime: " + SbproPrinter.roundDouble(secTime, decimalsNum),
                StylishPrinter.ANSI_BOLD_YELLOW, StylishPrinter.ANSI_CYAN_BACKGROUND);
    }
    
    public static boolean menu(){
        StylishPrinter.println("\nMenu:", StylishPrinter.ANSI_BOLD_RED);
        System.out.println("1: Dynamic Programming Solve");
        System.out.println("2: Hill Climbing Solve");
        System.out.println("3: Simulated Annealing Solve");
        System.out.println("4: Genetic Solve");
        System.out.println("5: Change Cities List");
        System.out.println("6: Exit");
        System.out.print("\nEnter Your Choice: ");
        int choice = SbproScanner.inputInt(1, 6);
        
        if(choice==1) dynamicSolve();
        else if(choice==2) hillClimbingSolve();
        else if(choice==3) simAnnealingSolve();
        else if(choice==4) geneticSolve();
        else if(choice==5) changeCitiesList();
        else if(choice==6) return false;
        
        return true;
    }
}
