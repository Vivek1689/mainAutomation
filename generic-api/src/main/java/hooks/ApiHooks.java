package hooks;

import config.factory.ConfigFactory;
import generic.BaseRequest;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import logger.ExtentLogger;
import logger.Logger;
import utils.constants.Constants;
import utils.extentReport.ExtentManager;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ApiHooks {

    public static int passCount,failCount,totalCount;

    @Before
    public void beforeMethod(Scenario scenario){
        System.out.println("Before hook");
        ExtentLogger.assignAuthor(getAuthorName(scenario));
        //ExtentManager.createTest(scenario.getName());
        //ExtentManager.getTest().assignAuthor(getAuthorName(scenario));
        //ExtentManager.getTest().assignCategory(System.getProperty("cucumber.filter.tag","Sanity").startsWith("@Regression") ? "Regression": "Smoke");
    }

    @After
    public void afterMethod(Scenario scenario){System.out.println("After hook");
        if(scenario.isFailed()){
            failCount++;
            Logger.fail(String.format("Failed feature file %s, Scenario line no. %s", getFeatureFileName(scenario), scenario.getLine()));
        }else{
            passCount++;
        }
        totalCount++;

    }


    public String getAuthorName(Scenario scenario){
        String name = scenario.getSourceTagNames().stream().filter(e -> e.contains("Author")).collect(Collectors.toList()).get(0).toString();
        return name.split("\\.")[1];
    }

    public String getFeatureFileName(Scenario scenario){
        return Arrays.stream(scenario.getUri().toString().split("/")).filter(e -> e.contains(".feature")).collect(Collectors.toList()).get(0).toString();
    }


}
