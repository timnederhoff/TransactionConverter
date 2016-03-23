package Converter;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

class Configuration {
//    private static Configuration instance = new Configuration();
    static Config bank;

    private Configuration() { }

    private static String chooseBank(final Config defaultConfig) {
        final Config bank = ConfigFactory.systemProperties();
        final boolean bankConfigured = bank.hasPath("bank");
        return bankConfigured ? bank.getString("bank") : defaultConfig.getString("bank");
    }

    static void reloadConfig(File configFile) {
        final Config config = ConfigFactory.parseFile(configFile);

        final Config defaultConfig = config.getConfig("default");
        final String bankValue = chooseBank(defaultConfig);
        bank = config.getConfig("bank").getConfig(bankValue).withFallback(defaultConfig);
    }
}
