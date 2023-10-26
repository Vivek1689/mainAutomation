package driverManager.remote.browserStack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Objects;

import static config.factory.ConfigFactory.getConfig;

public final class BrowserStack_DriverProvider {

    private BrowserStack_DriverProvider() { }

    static String sProjectName = getConfig().projectName();

    static String build = Objects.nonNull(System.getProperty("BROWSERSTACK_BUILD_NAME"))
            ? "CI-CD Build-"+System.getProperty("BROWSERSTACK_BUILD_NAME")
            : "LOCAL-" + getConfig().buildName() + "-" + getConfig().buildNumber();

    static String sBuildName = String.format("%s [%s] %s",
            sProjectName, getConfig().environment().toUpperCase(), build);

    // Working : 3-Jul-23
    public static WebDriver getDriver(){
        //String username = System.getenv("BROWSERSTACK_USERNAME");
        //String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        //String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
        // You must pass local and localIdentifier capabilities to test on your local development servers.
        //String local = System.getenv("BROWSERSTACK_LOCAL");
        //String localidentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");

        String browserName= getConfig().browserStack_Browser_Name();
        System.out.println("[BrowserStack]Launching "+browserName+" browser..." );
        DesiredCapabilities capabilities = new DesiredCapabilities();

        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", getConfig().browserStack_OS());
        browserstackOptions.put("osVersion", getConfig().browserStack_OS_Ver());
        browserstackOptions.put("browserVersion", getConfig().browserStack_Browser_Ver());
        browserstackOptions.put("projectName", sProjectName);
        browserstackOptions.put("buildName", sBuildName);
        //browserstackOptions.put("localIdentifier", localIdentifier);
        //browserstackOptions.put("seleniumVersion", "4.0.0");
        capabilities.setCapability("bstack:options", browserstackOptions);

        switch (browserName.trim().toUpperCase()) {
            case "CHROME":
                capabilities.setCapability("browserName", "Chrome");
                break;
            case "FIREFOX":
                capabilities.setCapability("browserName", "Firefox");
                break;
            case "SAFARI":
                if(getConfig().browserStack_OS().equalsIgnoreCase("OS X")) capabilities.setCapability("browserName", "Safari");
                else throw new RuntimeException("Browser Stack OS set is not Mac(OS X) to run test in Safari, please check your configuration");
                break;
            case "EDGE":
                capabilities.setCapability("browserName", "Edge");
                break;
            default:
                throw new RuntimeException("Browser Stack browser name is not matching: "+browserName);
        }
        return new RemoteWebDriver(getConfig().browserStackURL(), capabilities);
    }

}
