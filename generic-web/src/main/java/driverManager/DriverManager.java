package driverManager;

import driverManager.local.Local_DriverProvider;
import driverManager.remote.browserStack.BrowserStack_DriverProvider;
import driverManager.remote.docker.Docker_DriverProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;

import java.util.Objects;

import static config.factory.ConfigFactory.*;

public class DriverManager {

    private static ThreadLocal<WebDriver> driverThreadLocal =new ThreadLocal<>();
    private static ThreadLocal<WebDriverManager> wdmThreadLocal =new ThreadLocal<>();
    public static WebDriver getDriver() {
        if(Objects.nonNull(driverThreadLocal.get()))
            return driverThreadLocal.get();
        else
            return initializeDriver();
    }

    public static boolean isDriverUp() {
        return Objects.nonNull(driverThreadLocal.get());
    }

    public static WebDriverManager getWebDriverManager() {
        return wdmThreadLocal.get();
    }

    public static void quitDriver(){
        if(Objects.nonNull(driverThreadLocal.get())) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

    public static void unloadDriver() {
        driverThreadLocal.remove();
        wdmThreadLocal.remove();
    }

    public static WebDriver initializeDriver() {
        if(getConfig().runModeBrowser().equals("local")) {
            driverThreadLocal.set(Local_DriverProvider.getDriver());
        }else if(getConfig().runModeBrowser().equals("remote")){
            if(getConfig().remoteBrowserProvider().equalsIgnoreCase("browser_stack")){
                driverThreadLocal.set(BrowserStack_DriverProvider.getDriver());
            }else if(getConfig().remoteBrowserProvider().equalsIgnoreCase("docker")){
                wdmThreadLocal.set(Docker_DriverProvider.getDriverManager());
                driverThreadLocal.set(wdmThreadLocal.get().create());
            }
        }
        driverThreadLocal.get().manage().window().maximize();
        return driverThreadLocal.get();
    }

    public static void launchURL(String url){
        driverThreadLocal.get().get(url);
    }

    public static String screenShotAsBase64(){
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driverThreadLocal.get()).getScreenshotAs(OutputType.BASE64);
        return base64Screenshot;
    }



}
