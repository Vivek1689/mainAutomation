package logger;

public class Logger {

    public static void info(String message){
        message = "-- INFO: "+message;
        System.out.println(message);
        ExtentLogger.logInfo(message);
    }

    public static void pass(String message){
        message = "-- PASS:" + message;
        System.out.println(message);
        ExtentLogger.logPass(message);
    }

    public static void fail(String message){
        message = "-- FAIL:" + message;
        System.out.println(message);
        ExtentLogger.logFail(message);
    }

    public static void fail(String message, boolean isScreenshotNeeded){
        message = "-- FAIL:" + message;
        System.out.println(message);
        ExtentLogger.logFail(message, isScreenshotNeeded);
    }

}
