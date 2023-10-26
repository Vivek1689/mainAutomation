package utils.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import utils.constants.Constants;

import javax.swing.text.View;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    public static final ExtentReports extentReports = new ExtentReports();
    static Map<Integer,ExtentTest> extentTestMap= new HashMap<>();

    /* Create extent object */
    public static ExtentReports createExtentReport() throws IOException {
        ExtentSparkReporter reports = new ExtentSparkReporter("./extentreports/"+System.getProperty("BUILD_NUMBER", Constants.DEFAULT_BUILD_NUMBER))
                .viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{ViewName.DASHBOARD,ViewName.TEST,ViewName.AUTHOR, ViewName.CATEGORY}).apply();
        File file = new File("").getCanonicalFile();
        System.out.println("Parent directory : " + file.getParent());
        final File CONF = new File(file.getParent()+"/generic-core/extentconfig.json");
        reports.loadJSONConfig(CONF);
        System.out.println("Extent report created");
        extentReports.attachReporter(reports);
        extentReports.setSystemInfo("Company Name","Robosoft");
        extentReports.setSystemInfo("Platform",System.getProperty("platform","api"));
        return extentReports;
    }

    /* Create node for extent report which represents a test case */
    public static ExtentTest createTest(String testName){
        ExtentTest test = extentReports.createTest(testName);
        System.out.println("Extent test created");
        extentTestMap.put((int)Thread.currentThread().getId(),test);
        return test;
    }

    /* Get the current test node to log something */
    public static ExtentTest getTest(){
        return extentTestMap.get((int)Thread.currentThread().getId());
    }
}
