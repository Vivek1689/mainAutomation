package logger;

public class Logger {

    public static void info(String message){
        message = "-- INFO: "+message;
        System.out.println(message);
        ExtentLogger.logInfo(message);
    }

    public static void info_asJSON(String message){
        String message1 = "-- INFO(JSON): "+message;
        //System.out.println(message1);
        ExtentLogger.logInfo_JSON(message);
    }

    public static void info_asXML(String message){
        String message1 = "-- INFO(XML): "+message;
        System.out.println(message1);
        ExtentLogger.logInfo_XML(message);
    }

    public static void info_asJSON(Object message){
        String message1 = "-- INFO(JSON): "+message;
        System.out.println(message1);
        ExtentLogger.logInfo_JSON(message);
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

//    public static void fail(String message, boolean isScreenshotNeeded){
//        message = "-- FAIL:" + message;
//        System.out.println(message);
//        ExtentLogger.logFail(message, isScreenshotNeeded);
//    }

}
