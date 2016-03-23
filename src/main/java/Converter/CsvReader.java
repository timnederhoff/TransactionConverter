package Converter;

import com.typesafe.config.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class CsvReader {

    private String inputFilePath;
    private BufferedReader br;
    private Map<String, String> header;

    CsvReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    Map getHeader() throws IOException {
        header = new LinkedHashMap<>();

        br = new BufferedReader(new FileReader(inputFilePath));

        for (Config col : Configuration.bank.getConfigList("columnproperties")) {
            header.put(col.getString("column.name"), col.getString("data.type"));

        }
        return header;
    }

    List<List<String>> getData() throws IOException {
        List<Integer> indices = new ArrayList<>();
        List<String> csvHeader = Arrays.asList(br.readLine().replace("\"", "").split(","));

        for (String column : header.keySet()) {
            for (Integer i = 0; i < csvHeader.size(); i++) {
                if (csvHeader.get(i).equals(column)) {
                    indices.add(i);
                }
            }
        }

        String line;
        String[] lineArray;
        List<String> values;
        List<List<String>> mutaties = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            values = new ArrayList<>();
            line = line.replace("\'", "");
            lineArray = line.split("(?<=\"),(?=\")");
            for (Integer i : indices) {
                values.add(lineArray[i].replace(",", ""));
            }
            mutaties.add(values);
        }
        br.close();
        return mutaties;
    }
}
