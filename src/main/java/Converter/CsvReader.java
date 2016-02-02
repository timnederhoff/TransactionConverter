package Converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private String inputFilePath;
    private String[] header;
    BufferedReader br;

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getHeader() throws IOException {
        String colString = "";
        br = new BufferedReader(new FileReader(inputFilePath));
        header = br.readLine().split("\",\"");
        for (String x : header) {
            colString += x.toUpperCase().replace("\"","") + " TEXT,";
        }
        colString = colString.substring(0,colString.length()-1);
        colString = colString.replace(" ", "_");
        colString = colString.replace("(", "");
        colString = colString.replace(")", "");
        return colString;
    }

    public List<String[]> getData() throws IOException {
        String line;
        List<String[]> mutaties = new ArrayList<String[]>();
        while ((line = br.readLine()) != null) {
            mutaties.add(line.split("\",\""));
        }
        return mutaties;
    }
}
