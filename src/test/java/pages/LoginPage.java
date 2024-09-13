package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BaseClass {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By emptyUserNameErrorMessage = By.xpath("//*[contains(text(), 'Epic sadface: Username is required')]");
    private final By emptyPasswordErrorMessage = By.xpath("//*[contains(text(), 'Epic sadface: Password is required')]");
    private final By invalidUserNameAndPasswordErrorMessage = By.xpath("//*[contains(text(),'Epic sadface: Username and password do not match any user in this service')]");

    public LoginPage(WebDriver driver) {
        super();
        BaseClass.driver = driver;
        PageFactory.initElements(driver, this);
        logger.info("LoginPage initialized");
    }

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public String emptyGetUserNameErrorMessage() {
        return getText(emptyUserNameErrorMessage);
    }

    public String emptyGetPasswordErrorMessage() {
        return getText(emptyPasswordErrorMessage);
    }

    public String invalidGetUserNameAndPasswordErrorMessage() {
        return getText(invalidUserNameAndPasswordErrorMessage);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        logger.info("Logged in with username: {}", username);
    }
}