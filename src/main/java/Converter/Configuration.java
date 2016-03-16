package Converter;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Configuration {
    private static Configuration instance;
    public static Config bank;

    private Configuration() {
        final Config config = ConfigFactory.load();
        final Config defaultConfig = config.getConfig("default");

        final String bankValue = chooseBank(defaultConfig);
        bank = config.getConfig("bank").getConfig(bankValue).withFallback(defaultConfig);
    }

    public static Configuration get() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    private String chooseBank(final Config defaultConfig) {
        final Config bank = ConfigFactory.systemProperties();
        final boolean bankConfigured = bank.hasPath("bank");
        return bankConfigured ? bank.getString("bank") : defaultConfig.getString("bank");
    }
}
