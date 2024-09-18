package my.project.Test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage extends BaseClass {

    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);

    // Define locators
    private final By addToCartButton = By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']");
    private final By removeFromCartButton = By.xpath("//button[@id='remove-sauce-labs-bike-light']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By checkoutButton = By.id("checkout");
    private final By sortDropdown = By.xpath("//select[@class='product_sort_container']");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By lowToHighDropdownOption = By.xpath("//option[text()='Price (low to high)']");
    private final By lowPriceItem = By.xpath("//div[normalize-space()='Sauce Labs Fleece Jacket']");
    private final By productDetails = By.xpath("//div[@class='inventory_details_desc large_size']");
    private final By cartCount = By.xpath("//span[@class='shopping_cart_badge']");

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("DashboardPage initialized");
    }

    public boolean addToCar() {
        logger.info("Adding item to cart");
        click(addToCartButton);
        WebElement element = waitForElementVisibility(cartCount);
        boolean itemAdded = element != null;
        logger.info("Item added to cart: {}", itemAdded);
        return itemAdded;
    }

    public void logOut() {
        logger.info("Logging out");
        click(menuButton);
        click(logoutLink);
        logger.info("Logged out successfully");
    }

    public boolean removeFromTheCar() {
        logger.info("Removing item from cart");
        click(removeFromCartButton);
        WebElement element = waitForElementVisibility(addToCartButton);
        boolean itemRemoved = element != null;
        logger.info("Item removed from cart: {}", itemRemoved);
        return itemRemoved;
    }

    public void goToCheckoutPage() {
        logger.info("Navigating to checkout page");
        click(cartIcon);
        click(checkoutButton);
        logger.info("Navigated to checkout page");
    }

    public boolean sortItemsLowToHigh() {
        logger.info("Sorting items by price: low to high");
        click(sortDropdown);
        click(lowToHighDropdownOption);
        WebElement element = waitForElementVisibility(lowToHighDropdownOption);
        boolean sorted = element != null;
        logger.info("Items sorted by price (low to high): {}", sorted);
        return sorted;
    }

    public boolean isTheRemoveButtonVisible() {
        WebElement element = waitForElementVisibility(removeFromCartButton);
        boolean isVisible = element != null;
        logger.info("Remove button visibility: {}", isVisible);
        return isVisible;
    }

    public boolean isTheAddButtonVisible() {
        WebElement element = waitForElementVisibility(addToCartButton);
        boolean isVisible = element != null;
        logger.info("Add button visibility: {}", isVisible);
        return isVisible;
    }

    public boolean isTheLowestPriceProductVisible() {
        WebElement element = waitForElementVisibility(lowPriceItem);
        boolean isVisible = element != null;
        logger.info("Lowest price product visibility: {}", isVisible);
        return isVisible;
    }

    public void getProductDetails() {
        logger.info("Getting product details");
        click(sortDropdown);
        click(lowToHighDropdownOption);
        click(lowPriceItem);
    }

    public boolean isOnDashboardPage() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnPage = currentUrl.contains("https://www.saucedemo.com/inventory.html");
        logger.info("Checking if on dashboard page. Result: {}", isOnPage);
        return isOnPage;
    }

    public boolean isOnCheckOutPage() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnPage = currentUrl.contains("https://www.saucedemo.com/checkout-step-one.html");
        logger.info("Checking if on checkout page. Result: {}", isOnPage);
        return isOnPage;
    }
    public boolean isOnProductDetails() {
        String currentUrl = driver.getCurrentUrl();
        boolean isOnPage = currentUrl.contains("https://www.saucedemo.com/inventory-item.html");
        logger.info("Checking if on product details page. Current URL: {}. Result: {}", currentUrl, isOnPage);
        return isOnPage;
    }

}














