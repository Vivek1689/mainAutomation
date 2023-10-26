package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Objects;


@CucumberOptions(
        features = "src/test/java/features/"
        ,glue={"hooks","stepdefs", "generic"}
        ,plugin = {"pretty", "json:target/cucumber.json", //"html:target/cucumber-reports.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,tags = "@demo"
        ,monochrome = true
        //,dryRun = true
        //,publish = true
)

public class TestRunner extends AbstractTestNGCucumberTests{

//    @Parameters({"browserName"})
//    @BeforeTest
//    protected void setUpBeforeSuite(@Optional() String browserName){
//        if(Objects.isNull(System.getProperty("browser")) && Objects.nonNull(browserName)){
//            System.out.println("********************************\n"+"Picked browser name("+browserName+") from TestNG XML file" +"\n********************************");
//            System.setProperty("browser", browserName);
//        }
//    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }



}
