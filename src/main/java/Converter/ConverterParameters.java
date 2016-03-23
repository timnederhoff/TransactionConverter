package Converter;

import com.beust.jcommander.Parameter;

import java.io.File;

class ConverterParameters {
    @Parameter(names = { "-i", "-inputfile" }, description = "The location of the input file", required = true)
    private String inputName;

    @Parameter(names = { "-o", "-outputfile" }, description = "The location of the output file", required = false)
    private String outputName  = "target/generated_db.db";

    @Parameter(names = { "-c", "-configuration" }, description = "Location of the configuration file", required = false)
    private String configPath = "";

    String getInputName() {
        return inputName;
    }

    String getOutputName() {
        return outputName;
    }

    File getCustomConfig() { return new File(configPath); }

}
