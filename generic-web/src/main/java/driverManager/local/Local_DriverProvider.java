package driverManager.local;

import io.cucumber.java.an.E;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import static config.factory.ConfigFactory.getConfig;

public class Local_DriverProvider {

    private Local_DriverProvider() {}

    public static WebDriver getDriver() {
        WebDriver driver;
        String browserName= getConfig().browser();
        System.out.println("[Local]Launching "+browserName+" browser..." );

        switch (browserName.trim().toUpperCase()) {
            case "CHROME":
                try {
                    driver = new ChromeDriver();
                }catch (Exception e) {
                    System.err.println("Error with SeleniumDriverManager: " + e.getMessage());
                    driver = WebDriverManager.chromedriver().create();
                }
                break;
            case "FIREFOX":
                try {
                    driver = new FirefoxDriver();
                }catch (Exception e) {
                    System.err.println("Error with SeleniumDriverManager: " + e.getMessage());
                    driver = WebDriverManager.firefoxdriver().create();
                }
                break;
            case "SAFARI":
                String os = SystemUtils.OS_NAME.toLowerCase();
                if(os.contains("win") || os.contains("nix") || os.contains("nux") || os.contains("aix")){
                    throw new RuntimeException("SAFARI browser is not supported on '"+os+"' OS.");
                }else {
                    try {
                        driver = new SafariDriver();
                    }catch (Exception e) {
                        System.err.println("Error with SeleniumDriverManager: " + e.getMessage());
                        driver = WebDriverManager.safaridriver().create();
                    }
                }
                break;
            case "EDGE":
                try {
                    driver = new EdgeDriver();
                }catch (Exception e) {
                    System.err.println("Error with SeleniumDriverManager: " + e.getMessage());
                    driver = WebDriverManager.edgedriver().create();
                }
                break;
            default:
                throw new RuntimeException("Local browser name is not matching: "+ browserName);
        }
        return driver;
    }


}
