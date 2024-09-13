package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage extends BaseClass {

    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);

    // Define locators
    private final By addToCartButton = By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']");

    private final By removeFromCartButton = By.xpath("//button[@id='remove-sauce-labs-bike-light']");

    private final By cartIcon = By.className("shopping_cart_link");
    private final By checkoutButton = By.id("checkout");
    private final By sortDropdown = By.className("product_sort_container");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By lowToHighDropdownOption = By.xpath("//option[text()='Price (low to high)']");
    private final By lowPriceItem = By.xpath("//div[normalize-space()='Sauce Labs Fleece Jacket']");
    private final By productDetails = By.xpath("//div[@class='inventory_details_desc large_size']");

    public DashboardPage(WebDriver driver) {
        super();
    }

    public void addToCar() {
        logger.info("Adding item to cart");
        click(addToCartButton);
        waitForElementVisibility(removeFromCartButton);
        logger.info("Item added to cart");
    }

    public void logOut() {
        logger.info("Logging out");
        click(menuButton);
        click(logoutLink);
        logger.info("Logged out successfully");
    }

    public void removeFromTheCar() {
        logger.info("Removing item from cart");
        click(addToCartButton);
        click(removeFromCartButton);
        waitForElementVisibility(addToCartButton);
        logger.info("Item removed from cart");
    }

    public void goToCheckoutPage() {
        logger.info("Navigating to checkout page");
        click(cartIcon);
        click(checkoutButton);
        logger.info("Navigated to checkout page");
    }

    public void sortItemsLowToHigh() {
        logger.info("Sorting items by price: low to high");
        click(sortDropdown);
        click(lowToHighDropdownOption);
        waitForElementVisibility(lowToHighDropdownOption);
        logger.info("Items sorted by price: low to high");
    }

    public void getProductDetails() {
        logger.info("Getting product details");
        click(sortDropdown);
        click(lowToHighDropdownOption);
        click(lowPriceItem);
        waitForElementVisibility(productDetails);
        String details = getElementText(productDetails);
        logger.info("Product details: {}", details);
    }

    public boolean isOnDashboardPage() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnPage = currentUrl.contains("https://www.saucedemo.com/inventory.html");
        logger.info("Checking if on login page. Result: {}", isOnPage);
        return isOnPage;
    }

    public boolean isOnCheckOutPage() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnPage = currentUrl.contains("https://www.saucedemo.com/checkout-step-one.html");
        logger.info("Checking if on login page. Result: {}", isOnPage);
        return isOnPage;
    }
}


















