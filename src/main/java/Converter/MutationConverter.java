package Converter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.Map;

public class MutationConverter {

    static Map header;

    public static DbWriter dbWriter;
    public static CsvReader csvReader;
    public static ConverterParameters params;

    public static void main(String[] args) throws Exception {
        params = new ConverterParameters();
        JCommander jCommander = new JCommander(params);
        jCommander.setProgramName("Mutation Converter");
        try {
            jCommander.parse(args);
            if (!params.getConfigPath().equals("")) {
                //TODO: set location of config file
            }
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

            csvReader = new CsvReader(params.getInputName());
            header = csvReader.getHeader();

            dbWriter = new DbWriter(params.getOutputName());
            dbWriter.startConnection();
            dbWriter.createTable(header);
            dbWriter.writeData(csvReader.getData());

        } catch (Exception x){
            x.printStackTrace();
        } finally {
            dbWriter.closeConnection();
        }
    }
}
