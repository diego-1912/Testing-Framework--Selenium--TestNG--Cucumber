package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import utils.DriverFactory;
import utils.LogDirectoryInitializer;

public abstract class BaseClass {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String baseUrl;
    protected static Logger logger;

    public BaseClass() {
    }

    @Parameters({"browser", "baseUrl"})
    @BeforeClass
    public void setUpClass(String browser, String baseUrl) {
        LogDirectoryInitializer.initializeLogDirectories();
        initializeLogger(browser);
        System.setProperty("log4j2.debug", "true");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        logger.info("Setting up WebDriver for browser: {}", browser);
        driver = DriverFactory.getDriver(browser);
        BaseClass.baseUrl = baseUrl;

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get(BaseClass.baseUrl);
        logger.info("Navigated to base URL: {}", BaseClass.baseUrl);

        waitForPageToLoad();
    }

    private void initializeLogger(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                logger = LoggerFactory.getLogger("ChromeLogger");
                break;
            case "firefox":
                logger = LoggerFactory.getLogger("FirefoxLogger");
                break;
            case "edge":
                logger = LoggerFactory.getLogger("EdgeLogger");
                break;
            default:
                logger = LoggerFactory.getLogger(BaseClass.class);
                break;
        }
    }

    private void waitForPageToLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        logger.info("Page fully loaded");
    }

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    protected void click(By locator) {
        waitForElementClickable(locator).click();
        logger.info("Clicked element: {}", locator);
    }

    protected void sendKeys(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' into element: {}", text, locator);
    }

    protected String getText(By locator) {
        String text = waitForElementVisible(locator).getText();
        logger.info("Retrieved text '{}' from element: {}", text, locator);
        return text;
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            logger.info("Element is present: {}", locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("Element is not present: {}", locator);
            return false;
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByVisibleText(text);
        logger.info("Selected '{}' from dropdown: {}", text, locator);
    }

    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }

    public WebElement waitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getElementText(By locator) {
        WebElement element = waitForElementVisibility(locator);
        return element.getText();
    }

    @AfterClass
    public void tearDownClass() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error occurred while closing the browser: {}", e.getMessage());
            }
        }
    }

    @BeforeMethod
    public void setUpMethod() {
        // Navigate to the base URL before each test method
        driver.get(baseUrl);
        logger.info("Navigated to base URL before test method: {}", baseUrl);
    }

    public void setUp(String browser, String baseUrl) {
    }
}