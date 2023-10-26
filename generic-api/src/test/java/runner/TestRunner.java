package runner;

import com.aventstack.extentreports.ExtentReports;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import utils.extentReport.ExtentManager;

import java.io.IOException;

@CucumberOptions(
        features = "src/test/java/features/"
        ,glue={"hooks","stepdefs"}
        ,plugin = {"html:target/site/cucumber-pretty", "json:target/cucumber.json",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,tags = "@update"
        ,monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests{

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }


}
