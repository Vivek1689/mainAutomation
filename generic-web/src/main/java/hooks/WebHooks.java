package hooks;


import com.aventstack.extentreports.Status;
import driverManager.DriverManager;
import generic.NetworkLogsCapture;
import io.cucumber.java.*;
import logger.BrowserStackLogger;
import logger.ExtentLogger;
import logger.Logger;
import org.assertj.core.api.Assertions;
//import utils.constants.Constants;

import java.awt.*;
import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;

import static config.factory.ConfigFactory.getConfig;


public class WebHooks {

    public static int passCount, failCount, totalCount;

    NetworkLogsCapture networkLogCapture = new NetworkLogsCapture();

    @Before
    public void beforeMethod(Scenario scenario)  {
        System.out.println("Before hook");
        launchDriver(); //DriverManager.initializeDriver();
        //Constants.currentInstance.set(DriverManager.getDriver());
        ExtentLogger.assignAuthor(getAuthorName(scenario));

        // Start the Chrome Dev tool network capture listener
        if(getConfig().networkCallLogCapture()) networkLogCapture.logNetworkFailure(DriverManager.getDriver());
        // Open VNC url for Docker server in default system browser to view the test execution
        if(getConfig().runModeBrowser().equalsIgnoreCase("remote")
                && getConfig().remoteBrowserProvider().equalsIgnoreCase("docker")
                && getConfig().dockerEnableVNC() && getConfig().dockerEnableVNC_View())
        {
            String vncURL = DriverManager.getWebDriverManager().getDockerNoVncUrl().toString();
            if (Desktop.isDesktopSupported()) {
                try {
                    System.out.println("Scenario: "+ scenario.getName() + " | VNC URL: " + vncURL);
                    Desktop.getDesktop().browse(new URI(vncURL));
                } catch (Exception ex){
                    System.err.println("Exception in viewing VNC URL in browser: " + ex.getLocalizedMessage());
                }
            }
        }
    }

    @After
    public void afterMethod(Scenario scenario) {
        System.out.println("After hook");
        if (scenario.isFailed()) {
            failCount++;

            Logger.info(String.format("Failed feature file '%s', Scenario line no. %s", getFeatureFileName(scenario), scenario.getLine()));
            Logger.fail("Failed test screenshot", true);

            // Log status for Remote browserstack test
                BrowserStackLogger.logFail(String.format("Scenario: %s | Feature File: %s | Line No.: %s"
                        , scenario.getName(), getFeatureFileName(scenario), scenario.getLine()));

        } else {
            passCount++;

            // Log status for Remote browserstack test
                BrowserStackLogger.logPass(String.format("Scenario: %s | Status: %s"
                        , scenario.getName(), scenario.getStatus().toString()));
        }
        totalCount++;

        closeDriver();//DriverManager.quitDriver(); //

        if (getConfig().networkCallLogCapture()) {
            if (networkLogCapture.getNetworkLogs().isEmpty()) {
                Logger.pass("No Network call errors found");
            } else {
                ExtentLogger.log_format(Status.FAIL, "Network console logs: \n" + networkLogCapture.getNetworkLogs());
                Assertions.fail("Network call errors found");
            }
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if(getConfig().eachStepsScreenshot() && (!scenario.isFailed())){
            ExtentLogger.attachScreen();
        }
    }

    @AfterAll
    public static void afterAll() {
        DriverManager.unloadDriver();
    }

    boolean reuseDriver = getConfig().reuseDriverInstance();
    private void launchDriver() {
        if (reuseDriver) {
            if(DriverManager.isDriverUp()){
                System.out.println("############################### Re-using the WebDriver instance #################################");
            }else DriverManager.getDriver();
        } else DriverManager.getDriver();
    }

    private void closeDriver() {
        if (!reuseDriver) DriverManager.quitDriver();
    }

    public String getAuthorName(Scenario scenario) {
        String name = scenario.getSourceTagNames().stream().filter(e -> e.contains("Author")).collect(Collectors.toList()).get(0).toString();
        return name.split("\\.")[1];
    }

    public String getFeatureFileName(Scenario scenario) {
        return Arrays.stream(scenario.getUri().toString().split("/")).filter(e -> e.contains(".feature")).collect(Collectors.toList()).get(0).toString();
    }

}
