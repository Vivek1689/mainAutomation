package enums;


public enum RequestType {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS");

    String requestType;

    RequestType(String s) {
        this.requestType = s;
    }

    public String getRequestType() {
        return this.requestType;
    }
}
