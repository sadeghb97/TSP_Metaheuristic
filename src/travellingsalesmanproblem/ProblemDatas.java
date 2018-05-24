package travellingsalesmanproblem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProblemDatas {
    public static String CITIES_JSON = "travellingsalesmanproblem/tinycities.json";
    public static String DISTANCES_JSON = "travellingsalesmanproblem/tinydistances.json";
    
    public static String[] getCitiesArray(){
        ArrayList<String> citiesList = new ArrayList();
        FileReader reader;
        
        try {
            ClassLoader classLoader = TravellingSalesmanProblem.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(CITIES_JSON);
            String jsonString = readFromInputStream(inputStream);
            JSONArray jsonArray = new JSONArray(jsonString);
            
            int i=0;
            while(jsonArray.length()>i) 
                citiesList.add((String) jsonArray.get(i++));
        } 
        catch (Exception ex) {
            Logger.getLogger(ProblemDatas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] cities = new String[citiesList.size()];
        for(int i=0; citiesList.size()>i; i++) cities[i] = citiesList.get(i);
        
        return cities;
    }
    
    public static int[][] getDistancesArray(){
        int[][] tempDistances = new int[100][];
        for(int i=0; 100>i; i++) tempDistances[i] = new int[100];
        FileReader reader;
        int index=0;
        
        try {
            ClassLoader classLoader = TravellingSalesmanProblem.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(DISTANCES_JSON);
            String jsonString = readFromInputStream(inputStream);
            JSONArray jsonArray = new JSONArray(jsonString);
            
            for(index=0; jsonArray.length()>index; index++){
                JSONArray ja = (JSONArray) jsonArray.get(index);
                for(int j=0; ja.length()>j; j++){
                    tempDistances[index][j] = (int)(long) ja.getInt(j);
                }
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(ProblemDatas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int[][] distances = new int[index][];
        for(int i=0; index>i; i++) distances[i] = new int[index];
        for(int i=0; index>i; i++) for(int j=0; index>j; j++) distances[i][j] = tempDistances[i][j];
        
        return distances;
    }
    
    private static String readFromInputStream(InputStream inputStream)
      throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
          = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
      return resultStringBuilder.toString();
    }
}
