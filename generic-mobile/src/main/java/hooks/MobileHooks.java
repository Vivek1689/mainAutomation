package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.appium.SelenideAppium;
import com.codeborne.selenide.webdriver.HttpClientTimeouts;
import config.factory.ConfigFactory;
import constants.Constants;
import driverManager.DriverManager;
import io.cucumber.java.*;
import logger.BrowserStackLogger;
import logger.Logger;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;
import logger.ExtentLogger;

import static config.factory.ConfigFactory.getConfig;

public class MobileHooks {
    public static int passCount,failCount,totalCount;


    @Before
    public final void beforeMethod(Scenario scenario) {
        System.out.println("In Before hook");
        //
        DriverManager.initializeDriver();

        Configuration.timeout = ConfigFactory.getConfig().waitTimeout();
        Configuration.pageLoadTimeout = -1;
        Configuration.remoteConnectionTimeout = Duration.ofSeconds(10).toMillis();
        Configuration.remoteReadTimeout = Duration.ofSeconds(30).toMillis();
        HttpClientTimeouts.defaultLocalReadTimeout = Duration.ofSeconds(20);

        SelenideAppium.launchApp();
        ExtentLogger.assignAuthor(getAuthorName(scenario));

    }

    @After
    public final void afterMethod(Scenario scenario) {
        System.out.println("After hook");

        if(scenario.isFailed()) {
            failCount++;

            Logger.info(String.format("Failed feature file '%s', Scenario line no. %s", getFeatureFileName(scenario), scenario.getLine()));
            Logger.fail("Failed test screenshot", true);

            // Log status for Remote browserstack test
                BrowserStackLogger.logFail(String.format("Scenario: %s | Feature File: %s | Line No.: %s"
                        ,scenario.getName(), getFeatureFileName(scenario), scenario.getLine()));

        }else{
            passCount++;

            // Log status for Remote browserstack test
                BrowserStackLogger.logPass(String.format("Scenario: %s | Status: %s"
                        ,scenario.getName(),scenario.getStatus().toString()));
        }
        totalCount++;

         // Terminate the app
        if(getConfig().terminateApp()) {
            DriverManager.terminateApp();
        }
        // Quit the driver
        DriverManager.quitDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if(getConfig().eachStepsScreenshot() && (!scenario.isFailed())){
            ExtentLogger.attachScreen();
        }
    }

    @BeforeAll
    public static void beforeAll() {
        DriverManager.startAppium();
    }
    @AfterAll
    public static void afterAll() {
        DriverManager.stopAppium();
    }

    public String getAuthorName(Scenario scenario){
        String name = scenario.getSourceTagNames().stream().filter(e -> e.contains("Author")).collect(Collectors.toList()).get(0).toString();
        return name.split("\\.")[1];
    }

    public String getFeatureFileName(Scenario scenario){
        return Arrays.stream(scenario.getUri().toString().split("/")).filter(e -> e.contains(".feature")).collect(Collectors.toList()).get(0).toString();
    }
}
