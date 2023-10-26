package driverManager.remote.browserStack;

import com.codeborne.selenide.WebDriverProvider;
import config.factory.ConfigFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Objects;

import static config.factory.ConfigFactory.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class BrowserStack_SauceLab_DemoApp implements WebDriverProvider {

    String platformName = getConfig().platform().trim();
    String appiumVersion = getConfig().appiumVersion();
    String project = getConfig().projectName();
    //String build = getConfig().buildName() + "-" + getConfig().buildNumber();

    String build = Objects.nonNull(System.getProperty("BROWSERSTACK_BUILD_NAME"))
            ? "CI-CD Build-"+System.getProperty("BROWSERSTACK_BUILD_NAME")
            : "LOCAL-" + getConfig().buildName() + "-" + getConfig().buildNumber();

    String sName = String.format("%s [%s] [%s] %s",
            project, getConfig().environment().toUpperCase(), platformName.toUpperCase(), build);

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities1) {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("appiumVersion", appiumVersion);
        capabilities.setCapability("bstack:options", browserstackOptions);

        capabilities.setCapability(APP, getConfig().appRemote());
        capabilities.setCapability(DEVICE_NAME, getConfig().deviceNameRemote());
        capabilities.setCapability(PLATFORM_NAME, platformName);
        capabilities.setCapability(PLATFORM_VERSION, getConfig().platformVersionRemote());

        capabilities.setCapability("project", project);
        capabilities.setCapability("build", sName);
        capabilities.setCapability("name", platformName.toUpperCase()+"-Test");

        capabilities.setCapability("browserstack.debug", true);

        if(platformName.equalsIgnoreCase("ios")) {

            try {
                return new IOSDriver(getConfig().browserStackURL(), capabilities);
            } catch (SessionNotCreatedException e) {
                // Sometimes WDA session creation freezes unexpectedly on CI
                return new IOSDriver(getConfig().browserStackURL(), capabilities);
            }
        }else {

            capabilities.setCapability("interactiveDebugging", true);
            capabilities.setCapability("video", true);

            return new AndroidDriver(getConfig().browserStackURL(), capabilities);
        }
    }


}
