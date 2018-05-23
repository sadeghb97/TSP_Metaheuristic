package travellingsalesmanproblem;


import java.util.Stack;

public class StylishPrinter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BOLD_RESET = "\u001B[0;1m";
    public static final String ANSI_BOLD_BLACK = "\u001B[30;1m";
    public static final String ANSI_BOLD_RED = "\u001B[31;1m";
    public static final String ANSI_BOLD_GREEN = "\u001B[32;1m";
    public static final String ANSI_BOLD_YELLOW = "\u001B[33;1m";
    public static final String ANSI_BOLD_BLUE = "\u001B[34;1m";
    public static final String ANSI_BOLD_PURPLE = "\u001B[35;1m";
    public static final String ANSI_BOLD_CYAN = "\u001B[36;1m";
    public static final String ANSI_BOLD_WHITE = "\u001B[37;1m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    public static void print(String str, String fgcolor, String bgcolor){
        if(fgcolor == null) fgcolor = "";
        if(bgcolor == null) bgcolor = "";
        System.out.print(bgcolor + fgcolor + str + ANSI_RESET);
    }
    
    public static void print(String str, String fgcolor){
        print(str, fgcolor, "");
    }
    
    public static void println(String str, String fgcolor, String bgcolor){
        print(str, fgcolor, bgcolor);
        System.out.println("");
    }
    
    public static void println(String str, String fgcolor){
        print(str, fgcolor);
        System.out.println("");
    }
    
    public static void printSpace(int num){
        for(int i=0; num>i; i++) System.out.print(" ");
    }
    
    public static String getFormattedNumber(long number, String splitter){
        long n = number;
        Stack stack = new Stack();
        
        while(n>0){
            String piece = String.valueOf(n%1000);
            if(n>=1000){
                if(piece.length()==1) piece = "00" + piece;
                else if(piece.length()==2) piece = "0" + piece;
            }
            
            stack.add(piece);
            n = n/1000;
        }
        
        StringBuilder strBuilder = new StringBuilder();
        boolean first = true;
        while(!stack.empty()){
            if(first) first=false;
            else strBuilder.append(splitter);
            strBuilder.append(stack.pop());
        }
        
        return strBuilder.toString();
    }
}
