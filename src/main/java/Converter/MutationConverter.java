package Converter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.util.Map;

public class MutationConverter {

    private static ConverterParameters params;

    public static void main(String[] args) throws Exception {
        params = new ConverterParameters();
        JCommander jCommander = new JCommander(params);
        jCommander.setProgramName("Mutation Converter");
        try {
            jCommander.parse(args);

            File customConfig = params.getCustomConfig();

            if (!customConfig.getName().equals("")) {
                System.out.println("Custom configuration file is set.");
                if (customConfig.exists()) {
                    Configuration.reloadConfig(customConfig);
                } else {
                    System.out.println("The location of the custom configuration could not be found: " + customConfig.getAbsolutePath());
                }
            }

            run();
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
            jCommander.usage();
        }
    }

    private static void run(){
        DbWriter dbWriter = new DbWriter(params.getOutputName());
        try {
            System.out.println("Input file: " + params.getInputName());
            System.out.println("Output file: " + params.getOutputName());
            System.out.println("Configuration file: " + params.getCustomConfig().getAbsolutePath());

            CsvReader csvReader = new CsvReader(params.getInputName());
            Map header = csvReader.getHeader();

            dbWriter.startConnection();
            dbWriter.createTable(header);
            dbWriter.writeData(csvReader.getData());

            System.out.println("Done!");

        } catch (Exception x){
            x.printStackTrace();
        } finally {
            dbWriter.closeConnection();
        }
    }
}
