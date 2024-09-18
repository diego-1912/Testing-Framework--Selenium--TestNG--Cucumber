package my.project.Test.utils;

import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverFactory {

    // Logger for the DriverFactory class
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    // ThreadLocal to maintain a WebDriver instance per thread
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // List to keep track of all WebDriver instances
    private static final List<WebDriver> allDrivers = Collections.synchronizedList(new ArrayList<>());

    // Configuration settings for browser type and base URL
    private static final String browser = ConfigReader.getBrowser();
    private static final String baseUrl = ConfigReader.getBaseUrl();

    /**
     * Retrieves the WebDriver instance for the current thread, initializing it if necessary.
     *
     * @return The WebDriver instance for the current thread.
     */
    public static synchronized WebDriver getDriver() {
        // Check if the WebDriver is not initialized
        if (driverThreadLocal.get() == null) {
            logger.info("Driver not initialized. Initializing with configured browser.");
            initializeDriver(browser); // Initialize the driver with the configured browser
        }
        return driverThreadLocal.get();
    }

    /**
     * Initializes the WebDriver based on the specified browser type.
     *
     * @param browser The type of browser to initialize.
     * @return The initialized WebDriver instance.
     */
    public static synchronized WebDriver initializeDriver(String browser) {
        // Set context information for logging
        ThreadContext.put("browser", browser);

        WebDriver newDriver;

        // Switch statement to determine which browser to initialize
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--incognito");
                newDriver = new ChromeDriver(chromeOptions); // Initialize ChromeDriver
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                newDriver = new FirefoxDriver(firefoxOptions); // Initialize FirefoxDriver
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("headless", "inprivate");
                newDriver = new EdgeDriver(edgeOptions); // Initialize EdgeDriver
                break;
            default:
                // Default to Chrome if an invalid browser is specified
                logger.error("Invalid browser specified: {}. Defaulting to Chrome.", browser);
                WebDriverManager.chromedriver().setup();
                ChromeOptions defaultOptions = new ChromeOptions();
                defaultOptions.addArguments("--headless", "--incognito");
                newDriver = new ChromeDriver(defaultOptions); // Initialize default ChromeDriver
        }

        // Maximize the browser window and navigate to the base URL
        newDriver.manage().window().maximize();
        newDriver.get(baseUrl);
        logger.info("Navigated to base URL: {}", baseUrl);

        // Store the WebDriver in thread-local storage and add it to the list of all drivers
        driverThreadLocal.set(newDriver);
        allDrivers.add(newDriver);
        logger.info("WebDriver initialized for thread: {}", Thread.currentThread().getId());
        return newDriver;
    }

    /**
     * Quits the WebDriver instance associated with the current thread.
     */
    public static synchronized void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                // Quit the WebDriver instance
                driver.quit();
                logger.info("WebDriver quit for thread: {}", Thread.currentThread().getId());
            } catch (Exception e) {
                logger.error("Error quitting WebDriver", e);
            } finally {
                // Remove the driver from the list and thread-local storage
                allDrivers.remove(driver);
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Quits all WebDriver instances that have been created.
     */
    public static synchronized void quitAllDrivers() {
        logger.info("Attempting to quit all {} WebDriver instances", allDrivers.size());

        // Iterate over all WebDriver instances and quit each one
        for (WebDriver driver : new ArrayList<>(allDrivers)) {
            try {
                driver.quit();
                driver.close();
                logger.info("Successfully quit a WebDriver instance");
            } catch (Exception e) {
                logger.error("Error quitting WebDriver instance", e);
            } finally {
                // Ensure the driver is removed from the list
                allDrivers.remove(driver);
            }
        }
        // Clear thread-local storage
        driverThreadLocal.remove();
        logger.info("All WebDriver instances have been processed. Remaining instances: {}", allDrivers.size());
    }
}
