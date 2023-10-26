package constants;

import config.factory.ConfigFactory;

public class ApiConstants {

    /***
     *  End Points
     */
    public final static String BASE_URI = ConfigFactory.getConfig().api_baseURI();
    public final static String GET_BOOKINGS="/booking";
    public final static String CREATE_BOOKING="/booking";


    /**
     *  COMMON HEADERS
     */
    public final static String API_KEY="apiKey";
    public final static String SECRET_KEY="secretKey";
    public final static String VERSION="version";
    public final static String DEVICE_ID="deviceid";
    public final static String APP_SOURCE="appsource";
    public final static String APP_VERSION="appversion";
    public final static String DEVICE_NAME="devicename";
    public final static String CLOVIA_APP_ID="cloviaappid";


    public final static String ACCESS_TOKEN="accessToken";

    public final static String ACCEPT_DEFAULT = "*/*";
    public final static String CONTENT_TYPE_DEFAULT = "text/plain";
    public final static String CONTENT_TYPE_TEXT_XML = "text/xml";
    public final static String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public final static String CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public final static String CONTENT_TYPE_MULTI_PART_FORM_DATA = "multipart/form-data";
}
