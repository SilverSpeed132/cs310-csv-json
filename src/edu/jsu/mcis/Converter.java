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
            
            //LinkedHashMap<String, String> jsonObject = new LinkedHashMap<>();
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
            
            String[] cols = iterator.next();
            JSONArray colHeaders = new JSONArray();
            
            for(String field: cols){
                colHeaders.add(field);
            }
            jsonObject.put("colHeaders",colHeaders);
            
            
            JSONArray id = new JSONArray();
            JSONArray dataKeeper = new JSONArray();
            JSONArray dataFinal = new JSONArray();
            
            String[] record;
            String jsonString = "";
            
            while(iterator.hasNext()){
                record = iterator.next();
                //JSONArray dataKeeper = new JSONArray();
                for (int i=0; i < cols.length; ++i){
                    //JSONArray dataKeeper = new JSONArray();
                    if (i == 0){
                        id.add(record[0]);
                        jsonObject.put("rowHeaders", id);                      
                    }
                    else{                      
                        dataKeeper.add(record[i]);
                        dataFinal.add(dataKeeper);
                        jsonObject.put("data",dataFinal); 
                    }  
                }
            }
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
            
            csvData = new String[] {jsonString};
            
            csvWriter.writeNext(csvData);
            String csvString = writer.toString();
            results = csvString;
            
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}