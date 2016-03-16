package Converter;

import com.beust.jcommander.Parameter;

public class ConverterParameters {
    @Parameter(names = { "-i", "-inputfile" }, description = "The location of the input file", required = true)
    private String inputName;

    @Parameter(names = { "-o", "-outputfile" }, description = "The location of the output file", required = false)
    private String outputName  = "target/generated_db.db";

    @Parameter(names = { "-c", "-configuration" }, description = "Location of the configuration file", required = false)
    private String configPath = "";

    public String getInputName() {
        return inputName;
    }

    public String getOutputName() {
        return outputName;
    }

    public String getConfigPath() { return configPath; }

}
