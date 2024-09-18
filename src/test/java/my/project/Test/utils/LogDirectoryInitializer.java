package my.project.Test.utils;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDirectoryInitializer {
    // Logger for logging messages related to directory creation
    private static final Logger logger = LoggerFactory.getLogger(LogDirectoryInitializer.class);

    /**
     * Initializes the log directories for different browsers.
     * Creates main log directories and archive directories for each browser.
     */
    public static void initializeLogDirectories() {
        // Array of browser names for which log directories need to be created
        String[] browsers = {"chrome", "firefox", "edge"};
        // Base directory where logs will be stored
        String baseLogDir = "logs";

        // Iterate over each browser and create the necessary directories
        for (String browser : browsers) {
            // Create the main log directory for the browser
            createDirectory(baseLogDir + File.separator + browser);
            // Create the archive log directory for the browser
            createDirectory(baseLogDir + File.separator + "archive" + File.separator + browser);
        }
    }

    /**
     * Creates a directory at the specified path if it does not already exist.
     *
     * @param path The path of the directory to be created.
     */
    private static void createDirectory(String path) {
        // Create a File object for the specified directory path
        File directory = new File(path);
        // Check if the directory does not already exist
        if (!directory.exists()) {
            // Attempt to create the directory and log the result
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Created directory: {}", path); // Log success
            } else {
                logger.warn("Failed to create directory: {}", path); // Log failure
            }
        }
    }
}