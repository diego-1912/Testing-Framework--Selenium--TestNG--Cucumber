package my.project.Test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger;

    public BaseClass(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    protected void waitForPageToLoad() {
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

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String getElementText(By locator) {
        WebElement element = waitForElementVisibility(locator);
        return element.getText();
    }
}