package utils.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.constants.Constants;
import utils.extentReport.ExtentManager;

import java.util.Objects;

import static utils.extentReport.ExtentManager.getTest;

//import static utils.ExtentReports.ExtentTestManager.getTest;

public class TestListener implements ITestListener {


    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    @Override
    public void onStart(ITestContext iTestContext) {
    }
    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentManager.extentReports.flush();
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("On Test Start");
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        getTest().pass(MarkupHelper.createLabel(getTestMethodName(iTestResult) + " test is passed.",ExtentColor.GREEN));
    }

    /* On test failure we are capturing the screenshot of the browser in execution and attaching that screenshot to the extent report */
    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("In test failure method");
        //Get driver from BaseTest and assign to local webdriver variable.
        WebDriver driver;
//        driver= iTestResult.getTestContext().getAttribute("WebDriver");
        //Take base64Screenshot screenshot for extent reports
        driver = Constants.currentInstance.get();
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        getTest().log(Status.FAIL, "Test Failed",
                getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
        driver.quit();
    }
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        getTest().log(Status.SKIP, "Test Skipped");
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
}