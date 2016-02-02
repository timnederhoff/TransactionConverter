package Converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class GiroReader {

    public static Connection c = null;

    private static String outputName = "test.db";

    public static DbWriter dbWriter;
    public static CsvReader csvReader = new CsvReader();

    public static void main(String[] args) {
        try {
            csvReader.setInputFilePath(args[0]);

            String line, mutatie[], colString ="", header[];

            dbWriter = new DbWriter("test.db");
            dbWriter.createTable(csvReader.getHeader());
            dbWriter.writeData(csvReader.getData());

            dbWriter.closeConnection();
        }catch (Exception x){
            x.printStackTrace();
        }

    }





}
