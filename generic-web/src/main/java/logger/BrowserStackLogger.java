package logger;

import driverManager.DriverManager;
import org.openqa.selenium.JavascriptExecutor;

import static config.factory.ConfigFactory.*;

// https://www.browserstack.com/docs/automate/selenium/set-name-and-status-of-test#setting-the-test-status
public class BrowserStackLogger {


    // Setting name of the test
    public static void testName(String testName) {
        if (getConfig().runModeBrowser().equalsIgnoreCase("remote") && getConfig().remoteBrowserProvider().equalsIgnoreCase("browser_stack")) {
            JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
            jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\"%s\" }}", testName));
        }
    }

    //Setting the status of test as 'passed' or 'failed'
    public static void logPass(String msg) {
        if (getConfig().runModeBrowser().equalsIgnoreCase("remote") && getConfig().remoteBrowserProvider().equalsIgnoreCase("browser_stack")) {
            JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
            jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"%s\"}}", msg));
        }
    }

    public static void logFail(String msg) {
        if (getConfig().runModeBrowser().equalsIgnoreCase("remote") && getConfig().remoteBrowserProvider().equalsIgnoreCase("browser_stack")) {
            JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
            jse.executeScript(String.format("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"%s\"}}", msg));
        }
    }

}
