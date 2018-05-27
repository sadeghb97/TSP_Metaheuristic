package travellingsalesmanproblem;

import static travellingsalesmanproblem.TravellingSalesmanProblem.NORMAL_MODE;
import static travellingsalesmanproblem.TravellingSalesmanProblem.getSecRuntimeString;
import static travellingsalesmanproblem.TravellingSalesmanProblem.howManyRunNumber;
import static travellingsalesmanproblem.TravellingSalesmanProblem.printRuntime;
import static travellingsalesmanproblem.TravellingSalesmanProblem.wantLogs;

public class TSPRunnable {
    public static final int DYNAMIC_PROGRAMMING = 1;
    public static final int HILL_CLIMBING = 2;
    public static final int SIMULATED_ANNEALING = 3;
    public static final int GENETIC = 4;
    
    private int algorithm;
    private String[] cities;
    private int[][] distances;
    
    private boolean printLogs;
    private int maxIteration;
    private double coolingRate;
    private int maxGenerations;
    private int populations;
    private double mutationProb;
    private int selectionMode;
    private int crossoverMode;

    public TSPRunnable(int algorithm, String[] cities, int[][] distances) {
        this.algorithm = algorithm;
        this.cities = cities;
        this.distances = distances;
    }

    public void setPrintLogs(boolean printLogs){ this.printLogs = printLogs;}
    public void setMaxIteration(int maxIteration) { this.maxIteration = maxIteration;}
    public void setCoolingRate(double coolingRate) { this.coolingRate = coolingRate;}
    public void setMaxGenerations(int maxGenerations) { this.maxGenerations = maxGenerations;}
    public void setPopulations(int populations) { this.populations = populations;}
    public void setMutationProb(double mutationProb) { this.mutationProb = mutationProb;}
    public void setSelectionMode(int selectionMode) { this.selectionMode = selectionMode;}
    public void setCrossoverMode(int crossoverMode) { this.crossoverMode = crossoverMode;}    
    
    public void run(){
        boolean normalMode = TravellingSalesmanProblem.isNormalMode();
        
        if(normalMode){
            long startTime = System.nanoTime();
            
            if(algorithm == DYNAMIC_PROGRAMMING)
                TSPSolutions.solveWithDynamicSolve(cities, distances, printLogs);
            else if(algorithm == HILL_CLIMBING)
                TSPSolutions.solveWithHillClimbing(cities, distances);
            else if(algorithm == SIMULATED_ANNEALING)
                TSPSolutions.solveWithSimulatedAnnealing(cities, distances, maxIteration, coolingRate);
            else if(algorithm == GENETIC)
                TSPSolutions.solveWithGenetic(cities, distances, maxGenerations, populations,
                        mutationProb, selectionMode, crossoverMode, printLogs);
            
            long endTime = System.nanoTime();
            printRuntime(endTime - startTime);
        }
        else{
            boolean typeEnter;
            int runNumbers = howManyRunNumber(false);
            System.out.println();
            long sumRunTime=0;
            long sumDistances=0;
            
            for(int i=0; runNumbers>i; i++){
                long startTime = System.nanoTime();
                int[] path = null;
                
                if(algorithm == DYNAMIC_PROGRAMMING)
                    path = TSPSolutions.solveWithDynamicSolve(cities, distances, false);
                else if(algorithm == HILL_CLIMBING)
                    path = TSPSolutions.solveWithHillClimbing(cities, distances);
                else if(algorithm == SIMULATED_ANNEALING)
                    path = TSPSolutions.solveWithSimulatedAnnealing(cities, distances, maxIteration, coolingRate);
                else if(algorithm == GENETIC)
                    path = TSPSolutions.solveWithGenetic(cities, distances, maxGenerations, populations,
                            mutationProb, selectionMode, crossoverMode, false);
                
                int distance = TSPSolutions.answerEvaluator(path, distances);
                long endTime = System.nanoTime();
                long runTime = endTime - startTime;
                sumRunTime += runTime;
                sumDistances += distance;
                System.out.print((i+1) + ": D: " + distance);
                SbproPrinter.printDelimiter();
                System.out.println("R: " + getSecRuntimeString(runTime));
            }
            
            long avgDistance = sumDistances/runNumbers;
            long avgRuntime = sumRunTime/runNumbers;
            System.out.print("\nAvg Distance: ");
            StylishPrinter.print(String.valueOf(avgDistance), StylishPrinter.ANSI_BOLD_CYAN);
            SbproPrinter.printDelimiter("||");
            System.out.print("Avg Runtime: ");
            printRuntime(avgRuntime, StylishPrinter.ANSI_BOLD_CYAN, "");
        }        
    }
}
