package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import pages.BaseClass;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // Set the maximum retry count

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            // Reinitialize WebDriver before retrying the test
            BaseClass baseClass = (BaseClass) result.getInstance();
            baseClass.setUp(result.getTestContext().getCurrentXmlTest().getParameter("browser"),
                    result.getTestContext().getCurrentXmlTest().getParameter("baseUrl"));
            return true; // Retry the test
        }
        return false; // Do not retry further
    }
}
