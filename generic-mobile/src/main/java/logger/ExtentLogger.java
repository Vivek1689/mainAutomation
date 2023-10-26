package logger;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import driverManager.DriverManager;

import java.util.Objects;

public class ExtentLogger {

    static boolean isNonNull = Objects.nonNull(ExtentCucumberAdapter.getCurrentStep());


    public static void assignAuthor(String name){
        if(Objects.nonNull(ExtentCucumberAdapter.getCurrentScenario())){
            ExtentCucumberAdapter.getCurrentScenario().assignAuthor(name);
        }
    }

    public static void logInfo(String logMsg){
        //ExtentCucumberAdapter.getCurrentStep().info( " -- INFO: " + logMsg);
        //ExtentCucumberAdapter.addTestStepLog(String.format("%s -- INFO: %s %s", "<mark>", logMsg, "</mark>"));
        if(isNonNull) ExtentCucumberAdapter.getCurrentStep().info(String.format("<mark>" + logMsg + "</mark>"));
    }

    public static void log_format(Status status, String logMsg){
        if(isNonNull) ExtentCucumberAdapter.getCurrentStep().log(status, MarkupHelper.createCodeBlock(logMsg));
    }

    public static void logPass(String logMsg){
        if(isNonNull) ExtentCucumberAdapter.getCurrentStep().
                pass(MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
    }

    public static void logFail(String logMsg) {
        if(isNonNull) ExtentCucumberAdapter.getCurrentStep().
                fail(MarkupHelper.createLabel(logMsg, ExtentColor.RED));
    }

    public static void attachScreen() {
        if(isNonNull) ExtentCucumberAdapter.getCurrentStep()
                .info(MediaEntityBuilder.createScreenCaptureFromBase64String(DriverManager.screenShotAsBase64()).build());
    }

    public static void logFail(String logMsg, boolean isScreenshotNeeded) {
        String highlight = String.format("%s" + logMsg + "%s", "<mark>", "</mark>"); // highlighting with yellow background
        String highlight2 = String.format("%s" + logMsg + "%s", "<span class='badge white-text red'>", "</span>"); // using the class internally used by Extent report
        String highlight3 = String.format("%s" + logMsg + "%s", "<span style='color:Tomato;'>", "</span>"); //Setting custom color
        if(isScreenshotNeeded ) {
            if(isNonNull) ExtentCucumberAdapter.getCurrentStep().
                    fail(highlight2, MediaEntityBuilder.createScreenCaptureFromBase64String(DriverManager.screenShotAsBase64()).build());
        }else {
            logFail(logMsg);
        }
    }



}
