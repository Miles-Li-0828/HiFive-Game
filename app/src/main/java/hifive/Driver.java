package hifive;

import java.util.Properties;

public class Driver
{
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game4.properties";

    public static void main(String[] args)
    {
        final Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
        HiFive hiFive = new HiFive(properties);
        String logResult = hiFive.runApp();
        System.out.println("logResult = " + logResult);
    }

}
