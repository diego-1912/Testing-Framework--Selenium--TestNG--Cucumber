package my.project.Test.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import my.project.Test.utils.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "my.project.Test.stepDefinitions", // Path to your step definitions
        plugin = {
                "pretty", // For readable console output
                "html:target/cucumber-reports/cucumber-pretty.html", // HTML report generation
                "json:target/cucumber-reports/CucumberTestReport.json", // JSON report for Extent Adapter
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent Reports plugin
        },
        monochrome = true // Make console output readable
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        // Make log4j2 context map inheritable by threads to support parallel execution logging
        System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        // Enables parallel execution of scenarios
        return super.scenarios();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownSuite() {
        // Ensure all WebDriver instances are closed after all test scenarios are executed
        DriverFactory.quitAllDrivers();
    }
}