package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            // INSERT YOUR CODE HERE
            
            //Setting up the column headers
            String[] cols = iterator.next();
            JSONArray colHeaders = new JSONArray();
            
            for(String field: cols){
                colHeaders.add(field);
            }
            jsonObject.put("colHeaders",colHeaders);
              
            //Setting up the row headers
            JSONArray id = new JSONArray();
            String[] record;
                        
            while(iterator.hasNext()){
                record = iterator.next();
                for (int i=0; i < record.length; ++i){
                    if (i == 0){
                        id.add(record[0]);
                        jsonObject.put("rowHeaders", id);                      
                    }
                }
            }
            
            //Setting up the data
            JSONArray dataFinal = new JSONArray();
            Integer[][] dataArray = new Integer[8][4];

            for (int i = 1; i < full.size(); i++){
                Integer[] intParse = new Integer[4];
                record = full.get(i);
                for(int j = 1; j < record.length; j++){
                    intParse[j-1] = Integer.parseInt(record[j]);
                    }
                dataArray [i-1] = intParse;
                }
                
            for (int i = 0; i < 8; ++i){
                JSONArray dataKeeper = new JSONArray();
                for (int j = 0; j < 4; ++j){
                    dataKeeper.add(dataArray[i][j]);
                }
                dataFinal.add(dataKeeper);
            }
            
            // Putting the object together
            String jsonString = "";
            jsonObject.put("data",dataFinal);
            jsonString = JSONValue.toJSONString(jsonObject);
            results = jsonString;
        }
        
        
        catch(IOException e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            String[] csvData = null;
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter;
            csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            //JSON Object data collection
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            
            colHeaders = (JSONArray)jsonObject.get("colHeaders");
            rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            data = (JSONArray)jsonObject.get("data");
            
            //Creating containers and storing information within
            String [] cols = new String[5];
            String[] ids = new String[8];
            String[][] dataArray = new String[8][4];
            String[][] rowsFinal = new String[8][5];  //Is used to combine ids and dataArray later on
            
            for (int i = 0; i < cols.length; i++){
                cols[i] = (String)colHeaders.get(i);
            }
            for(int i = 0; i <ids.length; i++){
                ids[i] = (String)rowHeaders.get(i);
            }
            for(int i = 0; i < 8; i++){
                JSONArray dataLine = (JSONArray)data.get(i);
                for(int j = 0; j < 4; j++){
                    dataArray[i][j] = dataLine.get(j).toString();
                }
            }
            
            //Creating the CSV File columns
            csvWriter.writeNext(cols);
            
            //Creating the CSV File Rows
            for (int i= 0; i < 8; i++){
                rowsFinal [i][0] = ids[i];
                for (int j = 1; j < 5; j++){
                    rowsFinal[i][j] = dataArray [i][j-1];
                }
            }
            
            for(String[] field : rowsFinal){
                csvWriter.writeNext(field);
            }
            
            String csvString = writer.toString();
            results = csvString;
            
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}