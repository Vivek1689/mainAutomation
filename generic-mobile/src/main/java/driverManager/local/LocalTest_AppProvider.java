package driverManager.local;

import com.codeborne.selenide.WebDriverProvider;
import constants.Constants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.time.Duration;

import static config.factory.ConfigFactory.*;

public class LocalTest_AppProvider implements WebDriverProvider {

    String appPath = Constants.APP_PATH + getConfig().appLocal();
    String platformName = getConfig().platform().trim();

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        if(platformName.equalsIgnoreCase("android")) {
            UiAutomator2Options options = new UiAutomator2Options();
            //options.setPlatformName(MobilePlatform.ANDROID); // Optional By default this is "Android"
            //options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2); // Optional By default this is "UIAutomator2"
            // ## Reduce the app launch time and optimize your test execution in Appium.
            options.setFullReset(false); // Optional : False -> Not to reset the app
            //options.setNoReset(true); // Optional : True -> Not to close the app
            //options.setSkipDeviceInitialization(true); // Optional : True -> skip the device initialization process, which can save time during app launch

            // Required
            //options.setUdid("emulator-5554"); // Required to direct to particular device Only if multiple devices are connected
            //options.setPlatformVersion("");//Optional
            options.setApp(appPath);
            try {
                return new AndroidDriver(getConfig().appiumLocalURL(), options);
            } catch (SessionNotCreatedException e) {
                throw new RuntimeException("Failed to create Android driver: " + e.getMessage());
            }

        }else{
            XCUITestOptions options = new XCUITestOptions();
            // ------ Optional setting --------------------------------
            //options.setDeviceName("iPhone 13 Pro Max");
            options.setWdaLaunchTimeout(Duration.ofMinutes(10));
            options.setFullReset(false); // Optional : False -> Not to reset the app
            //options.setNoReset(true); // Optional : True -> Not to close the app
            //options.setPlatformName("14.0"); // Optional : To set the IOS version
            // ------ Optional setting --------------------------------
            options.setApp(appPath);
            try {
                return new IOSDriver(getConfig().appiumLocalURL(), options);
            } catch (SessionNotCreatedException e) {
                // Sometimes WDA session creation freezes unexpectedly on CI
                options.useNewWDA();
                return new IOSDriver(getConfig().appiumLocalURL(), options);
            }
        }
    }
}