package Converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private String inputFilePath;
    private BufferedReader br;

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String[] getHeader() throws IOException {
        br = new BufferedReader(new FileReader(inputFilePath));
        String[] header = br.readLine().split("\",\"");
        for (int i = 0; i < header.length; i++) {
            header[i] = header[i].replaceAll("[()\"]", "");
            header[i] = header[i].replace(" ", "_");
        }
        return header;
    }

    public List<String[]> getData() throws IOException {
        String line;
        List<String[]> mutaties = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            line = line.replace("\'", "");
            mutaties.add(line.split("(?<=\"),(?=\")"));
        }
        br.close();
        return mutaties;
    }
}
