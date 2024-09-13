package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Starting Test Suite: " + context.getName());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "ExtentReport_" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        // Configure ExtentSparkReporter
        spark.config().setDocumentTitle("Test Report");
        spark.config().setReportName("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        System.out.println("Extent report initialized at: " + reportPath);
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting Test: " + result.getName());
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass("Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        ExtentTest test = extentTest.get();

        if (test != null) {
            Throwable throwable = result.getThrowable();

            if (throwable != null) {
                test.fail(throwable);
                String exceptionMessage = throwable.getMessage();
                test.fail("Exception Message: " + exceptionMessage);
                String stackTrace = org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(throwable);
                test.fail("Stack Trace: " + stackTrace);
            } else {
                test.fail("Test failed without throwing an exception");
            }
        } else {
            System.err.println("Failed to log test result; ExtentTest instance is null.");
        }

        // Capture screenshot
        captureScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.skip(result.getThrowable());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Failed Within Success Percentage: " + result.getName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.warning("Test failed but within success percentage");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Ending Test Suite: " + context.getName());
        try {
            if (extent != null) {
                extent.flush();
                System.out.println("Extent report generated successfully at: " + reportPath);
            }
        } catch (Exception e) {
            System.err.println("Failed to generate Extent report: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extentTest.remove(); // Clean up ThreadLocal
        }
    }

    private void captureScreenshot(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = null;

        try {
            driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);
        } catch (Exception e) {
            System.err.println("Failed to get WebDriver instance: " + e.getMessage());
            return;
        }

        if (driver != null && driver instanceof TakesScreenshot) {
            String screenshotPath = "screenshots" + File.separator + result.getName() + "_" + System.currentTimeMillis() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                org.apache.commons.io.FileUtils.copyFile(screenshot, new File(screenshotPath));
                ExtentTest test = extentTest.get();
                if (test != null) {
                    test.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
}
