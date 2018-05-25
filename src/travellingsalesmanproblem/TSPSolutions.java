package travellingsalesmanproblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import sun.security.util.Length;

public class TSPSolutions {
    private static class COpt{
        public int[] set;
        public int[] orderedSet;
        public int destIndex;
        public int result;
        
        public COpt(int[] set) {
            this.set = set;
        }
        
        public COpt(int[] set, int destIndex) {
            this.set = set;
            this.destIndex = destIndex;
        }

        public COpt(int[] set, int destIndex, int result) {
            this.set = set;
            this.destIndex = destIndex;
            this.result = result;
        }
        
        public boolean equals(COpt copt){
            if(this.destIndex != copt.destIndex) return false;
            if(this.set.length != copt.set.length) return false;
            for(int i=0; this.set.length>i; i++) if(this.set[i] != copt.set[i]) return false;
            return true;
        }
        
        public int getLastValue(){
            return this.set[this.set.length-1];
        }
        
        public int[] getAppendedSet(int num){
            int[] newSet = new int[this.set.length+1];
            for(int i=0; this.set.length>i; i++) newSet[i] = this.set[i];
            newSet[this.set.length] = num;
            return newSet;
        }
        
        public int[] getAppendedOrderedSet(int num){
            int[] newSet = new int[this.orderedSet.length+1];
            for(int i=0; this.orderedSet.length>i; i++) newSet[i] = this.orderedSet[i];
            newSet[this.orderedSet.length] = num;
            return newSet;
        }
        
        public int[] getCuttedSet(int num){
            int[] newSet = new int[this.set.length-1];
            for(int i=0,j=0; this.set.length>i; i++,j++){
                if(num==this.set[i]) j--;
                else newSet[j] = this.set[i];
            }
            
            return newSet;
        }
    }
    
    private static void printDelimiter(String delStr){
        System.out.print(" ");
        StylishPrinter.print(delStr, StylishPrinter.ANSI_BOLD_YELLOW, StylishPrinter.ANSI_CYAN_BACKGROUND);
        System.out.print(" ");        
    }
    
    private static void printDelimiter(){
        printDelimiter("//");
    }
    
    public static void solveWithDynamicSolve(String[] cities, int[][] distances, boolean printLogs){
        ArrayList<COpt> sets = new ArrayList();
        ArrayList<COpt> memory = new ArrayList();
        sets.add(new COpt(new int[]{0}));
        COpt firstC = new COpt(new int[]{0}, 0, 0);
        firstC.orderedSet = new int[]{0};
        memory.add(firstC);
        
        while(cities.length > sets.get(0).set.length){
            ArrayList<COpt> newMemory = new ArrayList();
            ArrayList<COpt> newSets = new ArrayList();
            
            for(int memI=0; sets.size()>memI; memI++){
                COpt makerC = sets.get(memI);
                for(int upNum=makerC.getLastValue()+1; cities.length>upNum; upNum++){
                    newSets.add(new COpt(makerC.getAppendedSet(upNum)));
                    COpt nowSetC = new COpt(makerC.getAppendedSet(upNum), 0, Integer.MAX_VALUE/5);
                    nowSetC.orderedSet = new int[0];
                    newMemory.add(nowSetC);
                    for(int j=1; nowSetC.set.length>j; j++){
                        COpt subResC = null;
                        for(int i=0; nowSetC.set.length>i; i++){
                            if(nowSetC.set[i]==nowSetC.set[j]) continue;
                            COpt subC = new COpt(nowSetC.getCuttedSet(nowSetC.set[j]), nowSetC.set[i]);
                            for(int f=0; memory.size()>f; f++){
                                if(memory.get(f).equals(subC)){
                                    subC.result = memory.get(f).result;
                                    subC.orderedSet = memory.get(f).orderedSet;
                               
                                    COpt subResChanceC = new COpt(nowSetC.set, nowSetC.set[j],
                                            subC.result + distances[nowSetC.set[i]][nowSetC.set[j]]);
                                    subResChanceC.orderedSet = subC.getAppendedOrderedSet(nowSetC.set[j]);
                                    
                                    if(i==0 || subResChanceC.result<subResC.result)
                                        subResC = subResChanceC;
                                    
                                    break;
                                }
                            }
                        }
                        newMemory.add(subResC);
                    }
                }
            }
            memory = newMemory;
            sets = newSets;
            
            if(printLogs){
                for(int i=0; newMemory.size()>i; i++){
                    System.out.print("S:[");
                    for(int j=0; newMemory.get(i).set.length>j; j++){
                        if(j!=0) System.out.print(",");
                        System.out.print(newMemory.get(i).set[j]);
                    }
                    System.out.print("]");

                    printDelimiter();

                    System.out.print("O:[");
                    for(int j=0; newMemory.get(i).orderedSet.length>j; j++){
                        if(j!=0) System.out.print(",");
                        System.out.print(newMemory.get(i).orderedSet[j]);
                    }
                    System.out.print("]");

                    printDelimiter();

                    System.out.print("D:" + newMemory.get(i).destIndex);

                    printDelimiter();

                    System.out.print("R:");
                    printLimitedNumber(newMemory.get(i).result);
                    System.out.println();
                }
                System.out.println("("+newSets.size()+"-"+newMemory.size()+")\n");
            }
        }
        
        COpt resultC = null;
        for(int i=0; memory.size()>i; i++){
            if(i==0 || (memory.get(i).result + distances[memory.get(i).destIndex][0]) < 
                    (resultC.result + distances[resultC.destIndex][0])) 
                resultC=memory.get(i);
        }
        
        System.out.println("Shortest Path: " + (resultC.result  + distances[resultC.destIndex][0]));
        for(int i=0; resultC.orderedSet.length>i; i++){
            if(i!=0) System.out.print("-");
            System.out.print(cities[resultC.orderedSet[i]]);
        }
        System.out.println("-" + cities[0]);
    }
    
    private static int[] generateRandomAnswer(int length){
        int[] answer = new int[length];
        for(int i=0; answer.length>i; i++) answer[i]=i;
        for(int i=0; 200>i; i++){
            Random rand = new Random();
            int first = rand.nextInt(answer.length);
            int second = rand.nextInt(answer.length);
            int temp = answer[first];
            answer[first] = answer[second];
            answer[second] = temp;
        }
        return answer;
    }
    
    private static void printAnswer(int[] answer, String[] cities, int[][] distances){
        int resultPath = answerEvaluator(answer, distances);
        System.out.println("Shortest Path: " + resultPath);
        for(int i=0; answer.length>i; i++){
            if(i!=0) System.out.print("-");
            System.out.print(String.valueOf(answer[i]));
        }  
        System.out.println();
        for(int i=0; answer.length>i; i++){
            if(i!=0) System.out.print("-");
            System.out.print(cities[answer[i]]);
        }
        System.out.println("-" + cities[answer[0]]);        
    }
    
    private static int answerEvaluator(int[] answer, int[][] distances){
        int out=0;
        for(int i=0; answer.length>i; i++){
            if(answer.length>i+1) out+=(distances[answer[i]][answer[i+1]]);
            else out+=(distances[answer[i]][answer[0]]);
        }
        return out;
    }
    
    public static void solveWithHillClimbing(String[] cities, int[][] distances){
        int[] answer = generateRandomAnswer(cities.length);
        
        int bestI, bestJ;
        do{
            bestI=-1;
            bestJ=-1;
            int bestResult = answerEvaluator(answer, distances);

            for(int i=0; answer.length>i; i++){
                for(int j=i+1; answer.length>j; j++){
                    int temp = answer[i];
                    answer[i] = answer[j];
                    answer[j] = temp;

                    int nowResult = answerEvaluator(answer, distances);
                    if(nowResult<bestResult){
                        bestResult=nowResult;
                        bestI = i;
                        bestJ = j;
                    }

                    temp = answer[i];
                    answer[i] = answer[j];
                    answer[j] = temp;
                }            
            }
            
            if(bestI!=-1 && bestJ!=-1){
                int temp = answer[bestI];
                answer[bestI] = answer[bestJ];
                answer[bestJ] = temp;              
            }
        } while(bestI!=-1 && bestJ!=-1);
        
        printAnswer(answer, cities, distances);
    }
    
    public static void solveWithSimulatedAnnealing(String[] cities, int[][] distances, 
            int maxIteration, double coolingRate){
        
        Random rand = new Random();
        
        int[] answer = generateRandomAnswer(cities.length);
        int[] bestAnswer = new int[answer.length];
        for(int i=0; answer.length>i; i++) bestAnswer[i] = answer[i];
        
        double startTemperature = answerEvaluator(answer, distances);
        double T=startTemperature;
        int bestDistance = (int) startTemperature;
        
        int algRep;
        for(algRep=0; maxIteration>algRep; algRep++){
            int recentDistance = answerEvaluator(answer, distances);
            if(algRep%500==0) System.out.println("Iteration " + algRep + ": " + recentDistance);
            int first = rand.nextInt(answer.length);
            int second = rand.nextInt(answer.length);
            int temp = answer[first];
            answer[first] = answer[second];
            answer[second] = temp;
            
            int nextDistance = answerEvaluator(answer, distances);
            int deltaE = nextDistance-recentDistance;
            if(deltaE<0){
                if(nextDistance<bestDistance){
                    bestDistance=nextDistance;
                    bestAnswer = new int[answer.length];
                    for(int i=0; answer.length>i; i++) bestAnswer[i] = answer[i];
                }
                
                T*=coolingRate;
                continue;
            }
            
            double probablity = Math.exp(((double)-deltaE)/T);
            double randP = Math.random();
            if(randP<probablity){
                T*=coolingRate;
                continue;
            }
            
            temp = answer[first];
            answer[first] = answer[second];
            answer[second] = temp;
            
            T*=coolingRate;
        }
        
        if(algRep>=500) System.out.println();
        printAnswer(answer, cities, distances);
        System.out.println();
        printAnswer(bestAnswer, cities, distances);
    }
    
    private static void printLimitedNumber(int number){
        if(number >= (Integer.MAX_VALUE/5)) System.out.print("\u221E");
        else System.out.print(number);
    }
    
    public static void solveWithGenetic(String[] cities, int[][] distances, 
            int maxGenerations, int populations, double mutationProb, 
            int selectionMode, int crossoverMode, boolean printLogs){

        GeneticTSPSolver geneticSolver = 
                new GeneticTSPSolver(cities, distances, populations, mutationProb, selectionMode, crossoverMode);
        
        geneticSolver.equalityCoefficient = 1.001;
        geneticSolver.printLogs = printLogs;
        geneticSolver.init();
        for(int i=0; maxGenerations>i; i++){
            geneticSolver.nextGeneration();
            //System.out.println(String.valueOf(geneticSolver.bestGen.distance));
        }
        GeneticTSPSolver.Gen bestGen = geneticSolver.bestGen;
        
        printAnswer(bestGen.fullPath, cities, distances);
    }
    
    public static void solveWithGenetic(String[] cities, int[][] distances, 
            int maxGenerations, int populations, double mutationProb, 
            int selectionMode, int crossoverMode){
        
        solveWithGenetic(cities, distances, maxGenerations, populations,
                mutationProb, selectionMode, crossoverMode, true);
    }
    
    public static class GeneticTSPSolver{
        String[] cities;
        int[][] distances;
        int population;
        double mutationProb;
        double equalityCoefficient;
        Gen[] gens;
        Gen bestGen;
        Gen worstGen;
        int generationsNum;
        int crossoverMode;
        int selectionMode;
        boolean printLogs;
        Random random;
        
        final static int EDGE_CROSSOVER = 1;
        final static int CIVIL_CROSSOVER = 2;
        
        final static int ROULETTE_WHEEL_SELECTION = 1;
        final static int RANK_SELECTION = 2;

        public GeneticTSPSolver(String[] cities, int[][] distances, int population, 
                double mutationProb, int selectionMode, int crossoverMode) {
            
            if((population%2)!=0) population++;
            this.cities = cities;
            this.distances = distances;
            this.population = population;
            this.mutationProb = mutationProb;
            this.crossoverMode = crossoverMode;
            this.selectionMode = selectionMode;
            equalityCoefficient = 1.1;
            printLogs = true;
            random = new Random();
        }
        
        public void init(){
            gens = new Gen[population];
            for(int i=0; gens.length>i; i++){
                gens[i] = new Gen();
                gens[i].fullPath = generateRandomAnswer(cities.length);
            }
            generationsNum=0;
            calculateDistances();
        }
        
        private void calculateDistances(){
            for(int i=0; gens.length>i; i++){
                gens[i].distance = answerEvaluator(gens[i].fullPath, distances);
                if(bestGen==null || worstGen==null){
                    bestGen = gens[i];
                    worstGen = gens[i];
                }
                else{
                    if(worstGen.distance<gens[i].distance) worstGen=gens[i];
                    if(bestGen.distance>gens[i].distance) bestGen=gens[i];
                }
            }
        }
        
        private Gen select(Gen pair){
            while(true){
                double sumProb=0;
                double randomProb = Math.random();
                
                for(int i=0; gens.length>i; i++){
                    sumProb+=gens[i].probablity;
                    if(randomProb<=sumProb){
                        if(pair == gens[i]){
                            if(printLogs)
                                System.out.println("CH: " + i + " (" + 
                                        SbproPrinter.roundDouble(randomProb, 4) + ") - Failed");
                            break;
                        }
                        
                        if(printLogs)
                            System.out.println("CH: " + i + " (" +
                                    SbproPrinter.roundDouble(randomProb, 4) + ")");
                        return gens[i];
                    }
                }
            }
        }
        
        private Gen select(){
            return select(null);
        }
        
        private void readyForRouletteWheelSelection(){
            int maxWorth = (int)(worstGen.distance * equalityCoefficient);
            int sumWorthes=0;
            for(int i=0; gens.length>i; i++){
                gens[i].worth = maxWorth - gens[i].distance;
                sumWorthes+=(gens[i].worth);
            }
            
            double worthUnitProb = ((double)1)/sumWorthes;
            for(int i=0; gens.length>i; i++)
                gens[i].probablity = gens[i].worth*worthUnitProb;           
        }
        
        private void readyForRankSelection(){
            int sumWorthes=0;
            PriorityQueue<Gen> queue = 
                new PriorityQueue<Gen>(new DistanceComparator());
            for(int i=0; gens.length>i; i++) queue.add(gens[i]);
            for(int i=0; !queue.isEmpty(); i++){
                queue.poll().worth = i;
                sumWorthes+=i;
            }
            
            double worthUnitProb = ((double)1)/sumWorthes;
            for(int i=0; gens.length>i; i++)
                gens[i].probablity = gens[i].worth*worthUnitProb;
        }
        
        private void readyForSelection(){
            if(selectionMode == ROULETTE_WHEEL_SELECTION) readyForRouletteWheelSelection();
            else readyForRankSelection();
            
            if(!printLogs) return;
                
            System.out.println("Generations: " + generationsNum);
            for(int i=0; population>i; i++){
                System.out.print(i+": ");
                gens[i].printPathWithIds();
                
                printDelimiter("|");
                System.out.print("D: " + gens[i].distance);
                printDelimiter("|");
                System.out.print("W: " + gens[i].worth);
                printDelimiter("|");
                System.out.println("P: " + SbproPrinter.roundDouble(gens[i].probablity, 3));
            }
            System.out.println("-----------------\n");
        }
        
        private Gen[] pmxCrossover(Gen parentOne, Gen parentTwo){
            Gen[] childs = new Gen[2];
            for(int i=0; 2>i; i++){
                childs[i] = new Gen();
                childs[i].fullPath = new int[cities.length];
            }
            for(int k=0; cities.length>k; k++){
                childs[0].fullPath[k] = parentOne.fullPath[k];
                childs[1].fullPath[k] = parentTwo.fullPath[k];
            }
            
            int pmxNumber = cities.length/2;
            
            for(int i=0; pmxNumber>i; i++){
                int randIndex = random.nextInt(cities.length);
                
                int childsOneNumber = childs[0].fullPath[randIndex];
                int childsTwoNumber = childs[1].fullPath[randIndex];
                int coFirstIndex=-1, coSecondIndex=-1, ctFirstIndex=-1, ctSecondIndex=-1;
                
                for(int k=0; cities.length>k; k++){
                    if(childs[0].fullPath[k] == childsOneNumber) coFirstIndex=k;
                    if(childs[0].fullPath[k] == childsTwoNumber) coSecondIndex=k;
                    if(childs[1].fullPath[k] == childsOneNumber) ctFirstIndex=k;
                    if(childs[1].fullPath[k] == childsTwoNumber) ctSecondIndex=k;
                }
                
                int temp = childs[0].fullPath[coFirstIndex];
                childs[0].fullPath[coFirstIndex] = childs[0].fullPath[coSecondIndex];
                childs[0].fullPath[coSecondIndex] = temp;
                
                temp = childs[1].fullPath[ctFirstIndex];
                childs[1].fullPath[ctFirstIndex] = childs[1].fullPath[ctSecondIndex];
                childs[1].fullPath[ctSecondIndex] = temp;
            }
            
            return childs;
        }
        
        private boolean containsCity (int[] path, int city, int length){
            for(int i=0; length>i; i++)
                if(path[i]==city) return true;
            return false;
        }
        
        public Gen civilCrossover(Gen parentOne, Gen parentTwo){
            Gen child = new Gen();
            child.fullPath = new int[cities.length];

            int firstHead = 0;
            int secondHead = 0;

            for(int j=0; cities.length>j; j++){
                if(Math.random()>0.5){
                    while(containsCity(child.fullPath, parentOne.fullPath[firstHead], j))
                        firstHead++;
                    child.fullPath[j] = parentOne.fullPath[firstHead];
                }
                else{
                    while(containsCity(child.fullPath, parentTwo.fullPath[secondHead], j))
                        secondHead++;
                    child.fullPath[j] = parentTwo.fullPath[secondHead];                        
                }
            }
            
            return child;
        }
        
        private static void ecRemoveCityFromNeighbours(ArrayList[] neighbours, int city){
            for(int i=0; neighbours.length>i; i++)
                neighbours[i].remove((Object) city);
        }
        
        public Gen edgeCrossover(Gen parentOne, Gen parentTwo){
            Gen child = new Gen();
            child.fullPath = new int[cities.length];
            ArrayList[] neighbours = new ArrayList[cities.length];
            for(int j=0; cities.length>j; j++) neighbours[j] = new ArrayList();
            
            for(int j=0; cities.length>j; j++){
                int poLeft, poRight, ptLeft, ptRight;
                if(j==0){
                    poLeft = parentOne.fullPath[parentOne.fullPath.length-1];
                    poRight = parentOne.fullPath[j+1];
                    ptLeft = parentTwo.fullPath[parentTwo.fullPath.length-1];
                    ptRight = parentTwo.fullPath[j+1];
                }
                else if(j==(cities.length-1)){
                    poLeft = parentOne.fullPath[j-1];
                    poRight = parentOne.fullPath[0];
                    ptLeft = parentTwo.fullPath[j-1];
                    ptRight = parentTwo.fullPath[0];
                }
                else{
                    poLeft = parentOne.fullPath[j-1];
                    poRight = parentOne.fullPath[j+1];
                    ptLeft = parentTwo.fullPath[j-1];
                    ptRight = parentTwo.fullPath[j+1];                    
                }
                
                if(!neighbours[parentOne.fullPath[j]].contains(poLeft))
                    neighbours[parentOne.fullPath[j]].add(poLeft);
                
                if(!neighbours[parentOne.fullPath[j]].contains(poRight))
                    neighbours[parentOne.fullPath[j]].add(poRight);
                
                if(!neighbours[parentTwo.fullPath[j]].contains(ptLeft))
                    neighbours[parentTwo.fullPath[j]].add(ptLeft);
                
                if(!neighbours[parentTwo.fullPath[j]].contains(ptRight))
                    neighbours[parentTwo.fullPath[j]].add(ptRight);
            }

            int city;
            if(Math.random()>0.5) city = parentOne.fullPath[0];
            else city = parentTwo.fullPath[0];
            ArrayList notInChild = new ArrayList();
            for(int i=0; cities.length>i; i++) notInChild.add(i);
            
            for(int j=0; cities.length>j; j++){
                child.fullPath[j] = city;
                ecRemoveCityFromNeighbours(neighbours, city);
                notInChild.remove((Object) city);
                
                if(j == (cities.length-1)) break;
                
                int altCity;
                if(neighbours[city].isEmpty()){
                    altCity = (int) notInChild.get(random.nextInt(notInChild.size()));
                }
                else{
                    ArrayList candidates = new ArrayList();
                    int fewestLength=-1;
                    
                    for(int i=0; neighbours[city].size()>i; i++){
                        int neighbourCity = (int) neighbours[city].get(i);
                        if(i==0 || fewestLength > neighbours[neighbourCity].size()){
                            fewestLength = neighbours[neighbourCity].size();
                            candidates.clear();
                            candidates.add(neighbourCity);
                        }
                        else if(fewestLength == neighbours[neighbourCity].size()){
                            fewestLength = neighbours[neighbourCity].size();
                            candidates.add(neighbourCity);
                        }
                    }
                    
                    altCity = (int) candidates.get(random.nextInt(candidates.size()));
                }
                
                city = altCity;
            }
            
            return child;
        }
        
        public Gen crossover(Gen parentOne, Gen parentTwo){
            if(crossoverMode == CIVIL_CROSSOVER)
                return civilCrossover(parentOne, parentTwo);
            return edgeCrossover(parentOne, parentTwo);
        }
        
        public void mutation(){
            for(int i=0; gens.length>i; i++){
                if(Math.random()>mutationProb) continue;

                int first = random.nextInt(gens[i].fullPath.length);
                int second = random.nextInt(gens[i].fullPath.length);
                int temp = gens[i].fullPath[first];
                gens[i].fullPath[first] = gens[i].fullPath[second];
                gens[i].fullPath[second] = temp;
            }            
        }
        
        public void nextGeneration(){
            readyForSelection();
            Gen[] newGens = new Gen[population];
            
            for(int i=0; newGens.length>i; i++){
                Gen father = select();
                Gen mother = GeneticTSPSolver.this.select(father);
                Gen child = crossover(father, mother);
                newGens[i] = child;
                
                if(printLogs){
                    System.out.print("\nFather: ");
                    father.printPathWithIds();
                    System.out.println();

                    System.out.print("Mother: ");
                    mother.printPathWithIds();
                    System.out.println();

                    System.out.print("Child: ");
                    child.printPathWithIds();
                    System.out.println("\n");
                }
            }
            
            if(printLogs) System.out.println("*****************\n\n");
            
            gens=newGens;
            mutation();
            generationsNum++;
            calculateDistances();
        }
        
        public static class DistanceComparator implements Comparator<Gen>{
            @Override
            public int compare(Gen x, Gen y){
                if (x.distance < y.distance)
                    return 1;
                if (x.distance > y.distance)
                    return -1;
                return 0;
            }
        }
        
        public class Gen{
            public int[] fullPath;
            public int distance;
            public int worth;
            public double probablity;
            
            public void printPathWithIds(){
                for(int i=0; fullPath.length>i; i++){
                    if(i!=0) System.out.print("-");
                    System.out.print(String.valueOf(fullPath[i]));
                }                
            }
        }
    }
}
