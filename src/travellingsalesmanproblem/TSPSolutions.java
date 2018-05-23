package travellingsalesmanproblem;

import java.util.ArrayList;
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
    
    private static void dynSolvePrintDelimiter(){
        System.out.print(" ");
        StylishPrinter.print("//", StylishPrinter.ANSI_BOLD_YELLOW, StylishPrinter.ANSI_CYAN_BACKGROUND);
        System.out.print(" ");        
    }
    
    public static void dynamicSolve(String[] cities, int[][] distances){
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
            
            for(int i=0; newMemory.size()>i; i++){
                System.out.print("S:[");
                for(int j=0; newMemory.get(i).set.length>j; j++){
                    if(j!=0) System.out.print(",");
                    System.out.print(newMemory.get(i).set[j]);
                }
                System.out.print("]");
                
                dynSolvePrintDelimiter();
                
                System.out.print("O:[");
                for(int j=0; newMemory.get(i).orderedSet.length>j; j++){
                    if(j!=0) System.out.print(",");
                    System.out.print(newMemory.get(i).orderedSet[j]);
                }
                System.out.print("]");
                
                dynSolvePrintDelimiter();
                
                System.out.print("D:" + newMemory.get(i).destIndex);
                
                dynSolvePrintDelimiter();
                
                System.out.print("R:");
                printLimitedNumber(newMemory.get(i).result);
                System.out.println();
            }
            System.out.println(newSets.size()+"-"+newMemory.size()+"\n");
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
    
    public static void hillClimbingSolve(String[] cities, int[][] distances){
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
    
    public static void simulatedAnnealingSolve(String[] cities, int[][] distances){
        final int maxIteration = 25000;
        double coolingRate = 0.9996;
        Random rand = new Random();
        
        int[] answer = generateRandomAnswer(cities.length);
        int[] bestAnswer = new int[answer.length];
        for(int i=0; answer.length>i; i++) bestAnswer[i] = answer[i];
        
        double startTemperature = answerEvaluator(answer, distances);
        double T=startTemperature;
        int bestDistance = (int) startTemperature;
        
        for(int algRep=0; maxIteration>algRep; algRep++){
            int recentDistance = answerEvaluator(answer, distances);
            if(algRep%500==0) System.out.println(String.valueOf(recentDistance));
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
        printAnswer(answer, cities, distances);
        printAnswer(bestAnswer, cities, distances);
    }
    
    private static void printLimitedNumber(int number){
        if(number >= (Integer.MAX_VALUE/5)) System.out.print("\u221E");
        else System.out.print(number);
    }
}
