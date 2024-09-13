package my.project.Test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BaseClass;
import pages.LoginPage;

@Listeners(utils.TestListener.class)
public class LoginTest extends BaseClass {

    private LoginPage loginPage;
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUp() {
        this.loginPage = new LoginPage(driver);
        this.softAssert = new SoftAssert();
        logger.info("LoginPage created for new test.");

    }
    @Test
    public void loginWithInvalidUsername() {
        logger.info("Starting loginWithInvalidUsername test.");
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        String actualErrorMessage = loginPage.invalidGetUserNameAndPasswordErrorMessage();
        logger.info("Actual error message: {}", actualErrorMessage);
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch for invalid username.");
        softAssert.assertAll();
    }

    @Test
    public void loginWithIncorrectPassword() {
        logger.info("Starting loginWithIncorrectPassword test.");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("incorrect_password");
        loginPage.clickLogin();
        String actualErrorMessage = loginPage.invalidGetUserNameAndPasswordErrorMessage();
        logger.info("Actual error message: {}", actualErrorMessage);
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch for incorrect password.");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyPassword() {
        logger.info("Starting loginWithEmptyPassword test.");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("");
        loginPage.clickLogin();
        String actualErrorMessage = loginPage.emptyGetPasswordErrorMessage();
        logger.info("Actual error message: {}", actualErrorMessage);
        String expectedErrorMessage = "Epic sadface: Password is required";
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch for empty password.");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyUsername() {
        logger.info("Starting loginWithEmptyUsername test.");
        loginPage.enterUsername("");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        String actualErrorMessage = loginPage.emptyGetUserNameErrorMessage();
        logger.info("Actual error message: {}", actualErrorMessage);
        String expectedErrorMessage = "Epic sadface: Username is required";
        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch for empty username.");
        softAssert.assertAll();
    }

    @Test
    public void validUserLogin() {
        logger.info("Starting the validUserLogin test.");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = getCurrentUrl();
        logger.info("Current URL after login: {}", actualUrl);
        softAssert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the inventory page after valid login.");
        softAssert.assertAll();
    }
}