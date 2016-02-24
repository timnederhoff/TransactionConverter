package Converter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class MutationConverter {

    static String[] header;

    public static DbWriter dbWriter;
    public static CsvReader csvReader;
    public static ConverterParameters params;

    public static void main(String[] args) throws Exception {
        params = new ConverterParameters();
        JCommander jCommander = new JCommander(params);
        jCommander.setProgramName("Mutation Converter");
        try {
            jCommander.parse(args);
            run();
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
            jCommander.usage();
        }
    }

    public static void run(){
        try {
            System.out.println("inputname is " + params.getInputName());
            System.out.println("output name is " + params.getOutputName());
            csvReader = new CsvReader();
            csvReader.setInputFilePath(params.getInputName());

            header = csvReader.getHeader();

            dbWriter = new DbWriter(params.getOutputName());
            dbWriter.startConnection();
            dbWriter.createTable(header);
            dbWriter.writeData(csvReader.getData());

            System.out.println(dbWriter.getMutationCounter() + " mutations written to " + params.getOutputName());

        } catch (Exception x){
            x.printStackTrace();
        } finally {
            dbWriter.closeConnection();
        }
    }
}
