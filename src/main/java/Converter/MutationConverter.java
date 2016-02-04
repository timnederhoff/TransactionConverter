package Converter;

import java.sql.Connection;

public class MutationConverter {

    public static Connection c = null;

    private static String outputName = "target/test.db";
    private static String[] header;

    public static DbWriter dbWriter;
    public static CsvReader csvReader;

    public static void main(String[] args) {
        try {
            csvReader = new CsvReader();
            csvReader.setInputFilePath(args[0]);

            dbWriter = new DbWriter(outputName);
            header = csvReader.getHeader();
            dbWriter.startConnection();
            dbWriter.createTable(header);
            dbWriter.writeData(csvReader.getData());
            System.out.println(dbWriter.getMutationCounter() + " mutations written to " + outputName);

        } catch (Exception x){
            x.printStackTrace();
        } finally {
            dbWriter.closeConnection();
        }

    }





}
