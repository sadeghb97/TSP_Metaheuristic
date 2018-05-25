package travellingsalesmanproblem;

public class SbproPrinter {
    public static String roundDouble(double fNum, int decimals){
        int coef = (int) Math.pow(10, decimals);
        return String.valueOf((double)((int)(fNum*coef))/coef);
    }    
}
