package travellingsalesmanproblem;

public class TravellingSalesmanProblem {
    public static void main(String[] args) {
        String[] cities = ProblemDatas.getCitiesArray();
        int[][] distances = ProblemDatas.getDistancesArray();
        
        //TSPSolutions.dynamicSolve(cities, distances);
        //TSPSolutions.hillClimbingSolve(cities, distances);
        TSPSolutions.simulatedAnnealingSolve(cities, distances);
    }
}
