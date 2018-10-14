package com.buzzshelter.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV File Parser
 */
public class CSVFile {
    private final InputStream inputStream;

    /**
     * Method constructor for CSVFile
     * @param inputStream input stream object
     */
    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    /**
     * Reads in data from given list.
     * @return list return by reading
     */
    public List read(){
        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                resultList.add(row);
            }
            inputStream.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        return resultList;
    }
}