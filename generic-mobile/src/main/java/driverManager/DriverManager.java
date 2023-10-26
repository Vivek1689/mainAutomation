package driverManager;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.appium.AppiumDriverRunner;
import com.codeborne.selenide.appium.SelenideAppium;
import driverManager.local.LocalTest_AppProvider;
import driverManager.remote.browserStack.BrowserStack_SauceLab_DemoApp;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

import static config.factory.ConfigFactory.*;


public class DriverManager {

    private static AppiumDriverLocalService appiumDriverLocalService;
    private static AppiumServiceBuilder builder;
    static int default_port = 4723;

    public static void initializeDriver() {
        // Check the Run mode - local or remote
        if (getConfig().RunModeMobile().equalsIgnoreCase("local")) {
            // Get Driver for local
            Configuration.browser = LocalTest_AppProvider.class.getName();

        } else if (getConfig().RunModeMobile().equals("remote")) {
            // Get Driver for remote cloud service providers
            if (getConfig().RemoteMobileProvider().equalsIgnoreCase("browser_stack")) {
                Configuration.browser = BrowserStack_SauceLab_DemoApp.class.getName();
            }
        }
    }

    public static void terminateApp() {
        if (Objects.nonNull(AppiumDriverRunner.getMobileDriver())) {
            String appID;
            if (AppiumDriverRunner.isAndroidDriver()) appID = DriverManager.getDriver().getCapabilities().getCapability("appPackage").toString();
            else appID = DriverManager.getDriver().getCapabilities().getCapability("bundleID").toString();
            SelenideAppium.terminateApp(appID);
        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(AppiumDriverRunner.getMobileDriver())) AppiumDriverRunner.getMobileDriver().quit();
    }

    public static AppiumDriver getDriver() {
        return AppiumDriverRunner.getMobileDriver();
    }

    // Set to start appium in Windows only
    public static void startAppium()  {

        if (getConfig().RunModeMobile().equalsIgnoreCase("local") && !checkIfServerIsRunning(default_port)){
            String os = SystemUtils.OS_NAME.toLowerCase();
            if(os.contains("win")){
                String appiumPath = String.format("C:/Users/%s/AppData/Roaming/npm/node_modules/appium", System.getProperty("user.name"));
                AppiumServiceBuilder builder;
                builder = new AppiumServiceBuilder(); //.withArgument(() -> "--base-path", "/");//.withArgument(() -> "--plugins", "images");
                builder.withTimeout(Duration.ofSeconds(120));
                builder.usingPort(default_port);//usingAnyFreePort();
                builder.usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"));
                builder.withAppiumJS(new File(appiumPath));
                HashMap<String, String> environment = new HashMap();
                environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
                builder.withEnvironment(environment);
                appiumDriverLocalService = AppiumDriverLocalService.buildService(builder);
                appiumDriverLocalService.start();
            }// window only
            else throw new RuntimeException("Not configured to auto start the Appium server in '"+os+"' OS.");
        } // Running test locally
    }

    public static void stopAppium(){
        if((appiumDriverLocalService!=null) && appiumDriverLocalService.isRunning()) appiumDriverLocalService.stop();
    }

    private static void startAppium1() {
        /*//Build the Appium service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service = AppiumDriverLocalService.buildDefaultService();
        service.start(); */
//        try {
//            String appiumExecutablePath = "C:/Users/Pradeep Babu/AppData/Roaming/npm/appium";
//// Create the command to start the Appium server
//            String[] command = {"node", appiumExecutablePath, "--address", "127.0.0.1", "--port", "4723"};
//
//            // Start the Appium server process
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            processBuilder.redirectErrorStream(true);
//            Process process = processBuilder.start();
//
//            // Read the process output
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Wait for the Appium server to start (optional)
//            for (int i = 0; i < 4; i++) {
//                if(checkIfServerIsRunning(default_port)) Thread.sleep(20000);
//            }
//
//            // Close the reader and the process
//            reader.close();
//            process.destroy();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    /*    try {
            String command = "cd C:/Users/Pradeep Babu/AppData/Roaming/npm/";

            // Start the Appium server process
            Process process = Runtime.getRuntime().exec(command );
            process = Runtime.getRuntime().exec("appium");

            // Wait for the Appium server to start (optional)
            Thread.sleep(20000);
            for (int i = 0; i < 4; i++) {
                if(!checkIfServerIsRunning(port)) Thread.sleep(20000);
            }

            // Close the process
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    public static boolean checkIfServerIsRunning(int port) {
        boolean isServerRunning = false;
        String appiumServerURL = getConfig().appiumLocalURL() + "/status";
        try {
            URL url = new URL(appiumServerURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Appium server is running!");
                isServerRunning = true;
            } else {
                System.out.println("Appium server is not running. Response code: " + responseCode);
            }
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return isServerRunning;
    }

    public static String screenShotAsBase64() {
        return "data:image/png;base64," + ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
    }
}


