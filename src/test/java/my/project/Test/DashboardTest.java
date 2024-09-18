//package my.project.Test;
//
//import my.project.Test.utils.TestListener;
//import org.testng.Assert;
//import org.testng.annotations.*;
//import org.testng.asserts.SoftAssert;
//import my.project.Test.pages.BaseClass;
//import my.project.Test.pages.DashboardPage;
//import my.project.Test.pages.LoginPage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Listeners(TestListener.class)
//public class DashboardTest extends BaseClass {
//    private DashboardPage dashboardPage;
//    private LoginPage loginPage;
//    private SoftAssert softAssert;
//    private static final Logger logger = LoggerFactory.getLogger(DashboardTest.class);
//
//    @BeforeClass
//    public void setUpClass() {
//        this.loginPage = new LoginPage(driver);
//        this.dashboardPage = new DashboardPage(driver);
//        this.softAssert = new SoftAssert();
//    }
//
//    @BeforeMethod
//    public void setUp() {
//        loginPage.login("standard_user", "secret_sauce");
//        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Failed to login to the dashboard");
//        logger.info("Logged into Dashboard");
//    }
//
//    @Test
//    public void testAddItemToCart() {
//        logger.info("Testing add item to cart functionality");
//        dashboardPage.addToCar();
//        // Example assertion: Assert.assertTrue(dashboardPage.isItemAddedToCart(), "Item was not added to cart");
//        logger.info("Add item to cart test completed");
//    }
//
//    @Test(priority = 1)
//    public void testRemoveItemFromCart() {
//        logger.info("Testing remove item from cart functionality");
//        dashboardPage.addToCar();
//        dashboardPage.removeFromTheCar();
//        logger.info("Remove item from cart test completed");
//    }
//
//    @Test
//    public void testGoToCheckoutPage() {
//        logger.info("Testing navigation to checkout page");
//        dashboardPage.goToCheckoutPage();
//        Assert.assertTrue(dashboardPage.isOnCheckOutPage(), "Failed to login to the dashboard");
//        logger.info("Navigation to checkout page test completed");
//    }
//
//    @Test
//    public void testSortItemsLowToHigh() {
//        logger.info("Testing sort items low to high functionality");
//        dashboardPage.sortItemsLowToHigh();
//        // Example assertion: Assert.assertTrue(dashboardPage.isItemsSortedLowToHigh(), "Items are not sorted low to high");
//        logger.info("Sort items low to high test completed");
//    }
//
//    @Test
//    public void testGetProductDetails() {
//        logger.info("Testing get product details functionality");
//        dashboardPage.getProductDetails();
//        // Example assertion: Assert.assertTrue(dashboardPage.areProductDetailsCorrect(), "Product details are incorrect");
//        logger.info("Get product details test completed");
//    }
//
//    @Test
//    public void testLogout() {
//        logger.info("Testing logout functionality");
//        dashboardPage.logOut();
//        Assert.assertFalse(dashboardPage.isOnDashboardPage(), "Failed to logout");
//        logger.info("Logout test completed");
//    }
//
//    @AfterMethod
//    public void returnToDashboard() {
//        if (!dashboardPage.isOnDashboardPage()) {
//            driver.get("https://www.saucedemo.com/inventory.html");
//            logger.info("Returned to Dashboard");
//        }
//    }
//
//    @AfterClass
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//            logger.info("Driver shut down");
//        }
//    }
//}
