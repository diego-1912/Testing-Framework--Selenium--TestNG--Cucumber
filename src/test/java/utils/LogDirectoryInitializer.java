package utils;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDirectoryInitializer {
    private static final Logger logger = LoggerFactory.getLogger(LogDirectoryInitializer.class);

    public static void initializeLogDirectories() {
        String[] browsers = {"chrome", "firefox", "edge"};
        String baseLogDir = "logs";

        for (String browser : browsers) {
            createDirectory(baseLogDir + File.separator + browser);
            createDirectory(baseLogDir + File.separator + "archive" + File.separator + browser);
        }
    }

    private static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Created directory: {}", path);
            } else {
                logger.warn("Failed to create directory: {}", path);
            }
        }
    }
}