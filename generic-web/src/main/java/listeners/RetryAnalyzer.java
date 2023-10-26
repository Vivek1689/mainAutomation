package listeners;

import config.factory.ConfigFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static int retries = ConfigFactory.getConfig().retryCount();

    @Override
    public boolean retry(ITestResult result) {
        boolean value = false;
        if(ConfigFactory.getConfig().retryFailedTests()){
            value = count < retries;
            //String tesCaseName = result.getTestClass().getRealClass().getSimpleName() + "--" + result.getMethod().getMethodName();
            if(value)System.out.println("\t@@@@@@@@@@@@@  Retry failed test["+(count+1)+" of "+retries+"(max retries)]   @@@@@@@@@@@@");
            count ++;
        }
        return value;
    }

}
