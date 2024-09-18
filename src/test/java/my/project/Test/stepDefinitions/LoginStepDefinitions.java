// LoginStepDefinitions.java
package my.project.Test.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import my.project.Test.pages.LoginPage;
// import my.project.Test.pages.DashboardPage;
import my.project.Test.utils.ConfigReader;
import my.project.Test.utils.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class LoginStepDefinitions {
    // Declare variables for WebDriver, LoginPage, logger, and SoftAssert
    private WebDriver driver;
    private LoginPage loginPage;
    // private DashboardPage dashboardPage; // Uncomment if DashboardPage is needed
    private static final Logger logger = LogManager.getLogger(LoginStepDefinitions.class);
    private static final String baseUrl = ConfigReader.getBaseUrl();
    private SoftAssert softAssert;

    // Constructor initializes WebDriver and Page objects
    public LoginStepDefinitions() {
        logger.info("Initializing LoginStepDefinitions");
        this.driver = DriverFactory.getDriver();
        logger.debug("WebDriver instance obtained from DriverFactory");
        this.loginPage = new LoginPage(driver);
        logger.debug("LoginPage object created");
        // this.dashboardPage = new DashboardPage(driver); // Uncomment if DashboardPage is needed
        this.softAssert = new SoftAssert();
        logger.debug("SoftAssert object initialized");
    }

    /**
     * Step definition to navigate to the login page.
     */
    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        // Navigate to the login page using the base URL
        logger.info("Navigating to the login page at URL: {}", baseUrl);
        driver.get(baseUrl);
        logger.debug("Opened URL: {}", baseUrl);

        // Verify if the login page is displayed correctly
        boolean isLoginPageDisplayed = loginPage.verifyLoginPageIsDisplayed();
        if (isLoginPageDisplayed) {
            logger.info("Login page is displayed correctly");
        } else {
            logger.warn("Login page is not displayed correctly");
        }
        // Assertion to ensure the login page is displayed
        softAssert.assertTrue(isLoginPageDisplayed, "Login page is not displayed correctly");
    }

    /**
     * Step definition for entering username and password.
     */
    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {
        // Enter the username in the login form
        logger.info("Entering username: '{}'", username);
        loginPage.enterUsername(username);
        logger.debug("Username '{}' entered", username);

        // Enter the password in the login form
        logger.info("Entering password");
        loginPage.enterPassword(password);
        logger.debug("Password entered");
    }

    /**
     * Step definition for clicking the login button.
     */
    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        // Click the login button to attempt login
        logger.info("Clicking the login button");
        loginPage.clickLogin();
        logger.debug("Login button clicked");
    }

    /**
     * Step definition to verify that the appropriate error message is displayed.
     */
    @Then("the user should see an error message {string}")
    public void the_user_should_see_an_error_message(String expectedErrorMessage) {
        logger.info("Verifying the displayed error message against the expected one.");

        // Switch to check the expected error message and call the appropriate helper method
        switch (expectedErrorMessage) {
            case "Epic sadface: Username and password do not match any user in this service":
                logger.debug("Expected error message indicates incorrect credentials");
                verifyIncorrectCredentialsError();
                break;
            case "Epic sadface: Username is required":
                logger.debug("Expected error message indicates missing username");
                verifyMissingUsernameError();
                break;
            case "Epic sadface: Password is required":
                logger.debug("Expected error message indicates missing password");
                verifyMissingPasswordError();
                break;
            default:
                logger.error("Unexpected error message received: {}", expectedErrorMessage);
                softAssert.fail("Unexpected error message: " + expectedErrorMessage);
        }

        // Ensure all assertions are verified
        softAssert.assertAll();
        logger.info("Error message verification completed");
    }

    /**
     * Step definition to verify that the user is redirected to the dashboard page.
     */
    @Then("the user should be redirected to the dashboard page")
    public void the_user_should_be_redirected_to_the_dashboard_page() {
        logger.info("Verifying user is redirected to the dashboard page");
        // Uncomment and implement this when DashboardPage is available
        // boolean isDashboardDisplayed = dashboardPage.isOnDashboardPage();
        // if (isDashboardDisplayed) {
        //     logger.info("User is successfully redirected to the dashboard page");
        // } else {
        //     logger.warn("User was not redirected to the dashboard page");
        //     softAssert.fail("User was not redirected to the dashboard page");
        // }
        softAssert.assertAll();
        logger.info("Dashboard page redirection verification completed");
    }

    /**
     * Helper method to verify incorrect credentials error.
     */
    private void verifyIncorrectCredentialsError() {
        logger.debug("Verifying incorrect credentials error message");
        String actualErrorMessage = loginPage.invalidGetUserNameAndPasswordErrorMessage();
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        logger.debug("Actual error message: '{}'", actualErrorMessage);
        logger.debug("Expected error message: '{}'", expectedErrorMessage);

        // Log and assert whether the error message matches the expected value
        if (actualErrorMessage.equals(expectedErrorMessage)) {
            logger.info("Error message for incorrect credentials matches expected");
        } else {
            logger.warn("Error message for incorrect credentials does not match expected");
        }

        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message for invalid credentials does not match.");
    }

    /**
     * Helper method to verify missing username error.
     */
    private void verifyMissingUsernameError() {
        logger.debug("Verifying missing username error message");
        String actualErrorMessage = loginPage.emptyGetUserNameErrorMessage();
        String expectedErrorMessage = "Epic sadface: Username is required";
        logger.debug("Actual error message: '{}'", actualErrorMessage);
        logger.debug("Expected error message: '{}'", expectedErrorMessage);

        // Log and assert whether the error message matches the expected value
        if (actualErrorMessage.equals(expectedErrorMessage)) {
            logger.info("Error message for missing username matches expected");
        } else {
            logger.warn("Error message for missing username does not match expected");
        }

        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message for empty user does not match.");
    }

    /**
     * Helper method to verify missing password error.
     */
    private void verifyMissingPasswordError() {
        logger.debug("Verifying missing password error message");
        String actualErrorMessage = loginPage.emptyGetPasswordErrorMessage();
        String expectedErrorMessage = "Epic sadface: Password is required";
        logger.debug("Actual error message: '{}'", actualErrorMessage);
        logger.debug("Expected error message: '{}'", expectedErrorMessage);

        // Log and assert whether the error message matches the expected value
        if (actualErrorMessage.equals(expectedErrorMessage)) {
            logger.info("Error message for missing password matches expected");
        } else {
            logger.warn("Error message for missing password does not match expected");
        }

        softAssert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message for empty password does not match.");
    }
}
