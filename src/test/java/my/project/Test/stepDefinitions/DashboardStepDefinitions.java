package my.project.Test.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my.project.Test.pages.DashboardPage;
import my.project.Test.pages.LoginPage;
import my.project.Test.utils.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertTrue;

public class DashboardStepDefinitions {
    // Declare variables for WebDriver, Page objects, and logger
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private static final Logger logger = LogManager.getLogger(DashboardStepDefinitions.class);
    private SoftAssert softAssert;

    // Constructor initializes WebDriver and Page objects
    public DashboardStepDefinitions() {
        logger.info("Initializing DashboardStepDefinitions");
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.softAssert = new SoftAssert();
    }

    /**
     * Step definition to ensure the user is logged in and on the Dashboard page.
     */
    @Given("the user is logged in and on the Dashboard page")
    public void the_user_is_logged_in_and_on_the_dashboard_page() {
        // Perform login action with predefined credentials
        loginPage.login("standard_user","secret_sauce");
        logger.info("Verifying user is logged in and on the Dashboard page");

        // Assert that the user is indeed on the Dashboard page
        assertTrue(dashboardPage.isOnDashboardPage(), "User is not on the dashboard page");
    }

    /**
     * Step definition for adding an item to the cart.
     */
    @When("the user adds an item to the cart")
    public void the_user_adds_an_item_to_the_cart() {
        logger.info("Adding item to cart");
        dashboardPage.addToCar();
    }

    /**
     * Step definition to verify that the item is added to the cart.
     */
    @Then("the item should be added to the cart")
    public void the_item_should_be_added_to_the_cart() {
        logger.info("Verifying item is added to cart");
        assertTrue(dashboardPage.isTheRemoveButtonVisible(), "Item was not added to the cart");
    }

    /**
     * Step definition for removing an item from the cart.
     */
    @When("the user removes the item from the cart")
    public void the_user_removes_the_item_from_the_cart() {
        logger.info("Removing item from cart");
        dashboardPage.removeFromTheCar();
    }

    /**
     * Step definition to verify that the item is removed from the cart.
     */
    @Then("the item should be removed from the cart")
    public void the_item_should_be_removed_from_the_cart() {
        logger.info("Verifying item is removed from cart");
        assertTrue(dashboardPage.isTheAddButtonVisible(), "Item was not removed from the cart");
    }

    /**
     * Step definition for navigating to the checkout page.
     */
    @When("the user navigates to the checkout page")
    public void the_user_navigates_to_the_checkout_page() {
        logger.info("Navigating to checkout page");
        dashboardPage.goToCheckoutPage();
    }

    /**
     * Step definition to verify that the user is on the checkout page.
     */
    @Then("the user should be on the checkout page")
    public void the_user_should_be_on_the_checkout_page() {
        logger.info("Verifying user is on the checkout page");
        assertTrue(dashboardPage.isOnCheckOutPage(), "User is not on the checkout page");
    }

    /**
     * Step definition for sorting items from low to high price.
     */
    @When("the user sorts items from low to high price")
    public void the_user_sorts_items_from_low_to_high_price() {
        logger.info("Sorting items from low to high price");
        dashboardPage.sortItemsLowToHigh();
    }

    /**
     * Step definition to verify that items are sorted from low to high price.
     */
    @Then("the items should be sorted from low to high price")
    public void the_items_should_be_sorted_from_low_to_high_price() {
        logger.info("Verifying items are sorted from low to high price");
        assertTrue(dashboardPage.isTheLowestPriceProductVisible(), "Items are not sorted correctly");
    }

    /**
     * Step definition for viewing product details.
     */
    @When("the user views the product details")
    public void the_user_views_the_product_details() {
        logger.info("Viewing product details");
        dashboardPage.getProductDetails();
        // Assert to verify that the user is on the Product Details page
        assertTrue(dashboardPage.isOnProductDetails());
    }

    /**
     * Step definition to verify that product details are displayed correctly.
     */
    @Then("the product details should be displayed correctly")
    public void the_product_details_should_be_displayed_correctly() {
        logger.info("Verifying product details are displayed correctly");
        assertTrue(dashboardPage.isOnProductDetails(), "Product details are not displayed correctly");
    }

    /**
     * Step definition for logging out the user.
     */
    @When("the user logs out")
    public void the_user_logs_out() {
        logger.info("Logging out");
        dashboardPage.logOut();
    }

    /**
     * Step definition to verify that the user is logged out and redirected to the login page.
     */
    @Then("the user should be logged out and redirected to the login page")
    public void the_user_should_be_logged_out_and_redirected_to_the_login_page() {
        logger.info("Verifying user is logged out and on the login page");
        assertTrue(loginPage.verifyLoginPageIsDisplayed(), "User is not on the login page");
    }
}