package driverManager.remote.docker;

import io.github.bonigarcia.wdm.WebDriverManager;

import static config.factory.ConfigFactory.getConfig;
import static io.github.bonigarcia.wdm.WebDriverManager.isDockerAvailable;

// TODO Refer https://bonigarcia.dev/webdrivermanager/#browsers-in-docker for more info on docker
// Docker images list https://aerokube.com/images/latest/#_selenium
public class Docker_DriverProvider {

    private Docker_DriverProvider() {}

    public static WebDriverManager getDriverManager() {

        if(isDockerAvailable()) {
            String browserName = getConfig().browser();
            System.out.println("[Docker]Launching "+browserName+" browser..." );
            boolean enableRec = false;
            boolean enableVnc = getConfig().dockerEnableVNC();
            String browserVer = getConfig().dockerBrowserVersion();

            WebDriverManager wdm = null;

            switch (browserName.trim().toUpperCase()) {
                case "CHROME":
                    wdm = WebDriverManager.chromedriver().browserInDocker();
                    break;
                case "FIREFOX":
                    wdm = WebDriverManager.firefoxdriver().browserInDocker();
                    break;
                case "SAFARI":
                    wdm = WebDriverManager.safaridriver().browserInDocker();
                    break;
                case "EDGE":
                    wdm = WebDriverManager.edgedriver().browserInDocker();
                    break;
                default:
                    throw new RuntimeException("Docker browser name is not matching: " + browserName);
            }
            wdm.browserVersion(browserVer);
            if (enableRec) wdm.enableRecording();
            if (enableVnc) wdm.enableVnc();
            return wdm;
        }else {
            throw new RuntimeException("Docker may not be available or active, please check! ");
        }


    }



}
