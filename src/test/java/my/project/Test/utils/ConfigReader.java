package my.project.Test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // Properties object to hold the configuration properties loaded from file
    private static Properties props = new Properties();

    // Static block to load properties from the config file at class loading time
    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Check if the config file is found in the classpath
            if (input == null) {
                throw new IOException("Unable to find config.properties in the classpath.");
            }
            // Load properties from the input stream
            props.load(input);
        } catch (IOException e) {
            // Print the stack trace for debugging purposes
            e.printStackTrace();
            // Handle exception as needed (e.g., log error, rethrow exception)
        }
    }

    /**
     * Retrieves the base URL from the configuration properties.
     *
     * @return The base URL as specified in the config.properties file.
     * @throws RuntimeException if the base URL is not specified or is empty.
     */
    public static String getBaseUrl() {
        // Retrieve the 'baseUrl' property from the loaded properties
        String baseUrl = props.getProperty("baseUrl");
        // Check if the base URL is missing or empty, and throw an exception if so
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("baseUrl is not specified in the config.properties file.");
        }
        return baseUrl;
    }

    /**
     * Retrieves the browser type from the configuration properties.
     *
     * @return The browser type as specified in the config.properties file.
     * @throws RuntimeException if the browser type is not specified or is empty.
     */
    public static String getBrowser() {
        // Retrieve the 'browser' property from the loaded properties
        String browser = props.getProperty("browser");
        // Check if the browser type is missing or empty, and throw an exception if so
        if (browser == null || browser.isEmpty()) {
            throw new RuntimeException("browser is not specified in the config.properties file.");
        }
        return browser;
    }

    // Add other getters if you have more configurations to retrieve
}