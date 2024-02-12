package enums;


import lombok.Getter;

@Getter
public enum RequestType {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS");

    final String requestType;

    RequestType(String s) {
        this.requestType = s;
    }

}
