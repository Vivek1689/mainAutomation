package utils.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
//    int retryLimit = Integer.parseInt(Constants.retryLimit);
int retryLimit = 3;
    /*
     * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
     * This method decides how many times a test needs to be rerun.
     * TestNg will call this method every time a test fails. So we can put some code in here to decide when to rerun the test.
     * Note: This method will return true if a tests needs to be retried and false it not.
     */

    @Override
    public boolean retry(ITestResult result) {
        boolean flag = false;
            if (!result.isSuccess()) {                      //Check if test not succeed
                if (counter < retryLimit) {                 //Check if retryLimit count is reached
                    counter++;                              //Increase the retryLimit count by 1
                    result.setStatus(ITestResult.FAILURE);  //Mark test as failed
                    flag = true;                            //Tells TestNG to re-run the test
                } else {
                    result.setStatus(ITestResult.FAILURE);  //If maxCount reached,test marked as failed
                }
            } else {
                result.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
            }
        return flag;
    }
}