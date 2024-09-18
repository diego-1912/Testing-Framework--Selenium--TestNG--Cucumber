package my.project.Test.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import my.project.Test.utils.DriverFactory;
import my.project.Test.utils.ConfigReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    /**
     * This method is executed before each scenario.
     * It sets up the WebDriver instance and navigates to the base URL.
     */
    @Before
    public void setUp(Scenario scenario) {
        // Retrieve the browser type and base URL from system properties or configuration
        String browser = System.getProperty("browser", ConfigReader.getBrowser());
        String baseUrl = System.getProperty("baseUrl", ConfigReader.getBaseUrl());

        // Set context information for logging purposes
        ThreadContext.put("browser", browser);
        ThreadContext.put("scenario", scenario.getName());

        // Log the start of the scenario with relevant details
        logger.info("Starting scenario: '{}' using browser: {} with base URL: {}", scenario.getName(), browser, baseUrl);

        try {
            // Initialize the WebDriver based on the specified browser
            WebDriver driver = DriverFactory.initializeDriver(browser);
            // Store the WebDriver instance in thread-local storage for parallel execution
            driverThread.set(driver);
            // Navigate to the specified base URL
            driver.get(baseUrl);
            logger.info("WebDriver initialized successfully and navigated to base URL: {}", baseUrl);
        } catch (Exception e) {
            // Log and handle exceptions during WebDriver initialization
            logger.error("Failed to initialize WebDriver", e);
            throw new IllegalStateException("WebDriver initialization failed", e);
        }
    }

    /**
     * This method is executed after each scenario.
     * It takes a screenshot if the scenario has failed.
     */
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        // Obtain the WebDriver instance from the thread-local storage
        WebDriver driver = driverThread.get();

        // Check if the scenario has failed and the driver is not null
        if (scenario.isFailed() && driver != null) {
            try {
                // Cast the driver to TakesScreenshot to enable screenshot capture
                TakesScreenshot ts = (TakesScreenshot) driver;
                // Capture the screenshot as a byte array
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

                // Attach the screenshot to the scenario in the Cucumber report
                scenario.attach(screenshot, "image/png", "Screenshot of failure");

                // Log a message indicating successful screenshot capture and attachment
                logger.info("Screenshot captured and attached to the report for scenario: '{}'", scenario.getName());

            } catch (Exception e) {
                // Log an error if the screenshot capture fails
                logger.error("Failed to capture or attach screenshot for scenario: " + scenario.getName(), e);
            }
        }
    }

    /**
     * This method is executed once after all scenarios have been executed.
     * It ensures that all WebDriver instances are properly closed.
     */
    @AfterAll
    public static void tearDownAll() {
        // Log the teardown of all remaining WebDriver instances
        logger.info("Tearing down all remaining WebDrivers");
        // Quit all WebDriver instances created by the DriverFactory
        DriverFactory.quitAllDrivers();
    }
}