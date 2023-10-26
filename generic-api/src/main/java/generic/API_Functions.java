package generic;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import logger.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class API_Functions {

    ////////////////////// Read | GET  /////////////////////
    public static Response _get(String endpoint, RequestSpecification reqSpec) {
        Response response = given()
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .spec(reqSpec).when().get(endpoint);
//        assertThat(response.getStatusCode())
//                .as("GET: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        logResponse(response);
        return response;
    }

    public static Response _get(String endpoint) {
        return _get(endpoint, null, null);
    }

    public static Response _get(String endpoint, Map headersMap) {
        return _get(endpoint, headersMap, null);
    }

    public static Response _get(String endpoint, Map headersMap, Object payLoad) {
        Response response = returnRequestSpecification("GET", endpoint, headersMap, payLoad)
                .get(endpoint);
        logResponse(response);
//        assertThat(response.getStatusCode())
//                .as("GET: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        return response;
    }

    ////////////////////// Create | POST  /////////////////////

    public static Response _post(String endpoint, Map headerMap, Object body) {
        Response response = returnRequestSpecification("POST", endpoint, headerMap, body)
                .post(endpoint);//.then().log().all().extract().response();
//        assertThat(response.getStatusCode())
//                .as("POST: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        logResponse(response);
        return response;
    }

    public static Response _post(String endpoint, Object body) {
        return _post(endpoint, null, body);
    }

    ////////////////////// Update | PUT  /////////////////////
    public static Response _put(String endpoint, Map headerMap, Object body) {
        Response response = returnRequestSpecification("PUT", endpoint, headerMap, body)
                .put(endpoint);//.then().log().all().extract().response();
//        assertThat(response.getStatusCode())
//                .as("PUT: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        logResponse(response);
        return response;
    }

    public static Response _put(String endpoint, Object body) {
        return _put(endpoint, null, body);
    }

    ////////////////////// Partial Update | PATCH  /////////////////////
    public static Response _patch(String endpoint, Map headerMap, Object body) {
        Response response = returnRequestSpecification("PATCH", endpoint, headerMap, body)
                .patch(endpoint);//.then().log().all().extract().response();
//        assertThat(response.getStatusCode())
//                .as("PATCH: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        logResponse(response);
        return response;
    }

    public static Response _patch(String endpoint, Object body) {
        return _patch(endpoint, null, body);
    }

    ////////////////////// DELETE  /////////////////////
    public static Response _del(String endpoint) {
        return _del(endpoint, null, null);
    }

    public static Response _del(String endpoint, Map headersMap) {
        return _del(endpoint, headersMap, null);
    }

    public static Response _del(String endpoint, Map headersMap, Object payLoad) {
        Response response = returnRequestSpecification("DEL", endpoint, headersMap, payLoad)
                .delete(endpoint);
        logResponse(response);
//        assertThat(response.getStatusCode())
//                .as("DELETE: StatusCode to be 2XX :\n" + response.asPrettyString())
//                .isBetween(200, 210);
        return response;
    }

  /*  public static Response _get1(String endpoint) {
        RequestSpecification requestSpecification = given().log().all();

        ValidatableResponse validatableResponse = given().get(endpoint).then();

        Response response = validatableResponse.extract().response();
        System.out.println("----------------------------------------");
        assertThat(response.getStatusCode())
                .as("GET: StatusCode to be 2XX :\n" + response.asPrettyString())
                .isBetween(200, 210);
        logRequest(new JSONObject().put("Request Type", "GET").put("Request URL", endpoint));
        logResponse(response);
        return response;
    }
*/

    private static RequestSpecification returnRequestSpecification(String reqType, String endPoint, Map headersMap, Object bodyObj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RequestURL", endPoint);
        jsonObject.put("RequestType", reqType);
        RequestSpecification rs = given().filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        rs.contentType(ContentType.JSON);
        if (headersMap != null) {
            rs.headers(headersMap);
            jsonObject.put("Headers", headersMap);
        }
        if (bodyObj != null) {
            rs.body(bodyObj);
            jsonObject.put("Body", bodyObj);
        }
        logRequest(jsonObject);
        return rs;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Note: Fetches ONLY FIRST data matches the JsonPath. Not recommended if JsonPath matches more than one
     *
     * @param jsonAsString
     * @param jsonPath
     * @return String : any number, boolean or date as String. Returns null as null
     */
    public static String _fetchDataFromJson_asString(String jsonAsString, String jsonPath) {
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST);
            List<?> list = JsonPath.using(conf).parse(jsonAsString).read(jsonPath);
            if (list.isEmpty())
                throw new RuntimeException("Error fetch(as String) from JSON, check the JsonPath: " + jsonPath);
            if (Objects.isNull(list.get(0))) return null;
            else return list.get(0).toString();
            //return Objects.requireNonNullElse(list.get(0),"null").toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Error to fetch(as String) from JSON, jsonPath(%s) \n", jsonPath) + e.getMessage());
        }
    }

    public static List<?> _fetchDataFromJson_alwaysAsList(String jsonAsString, String jsonPath) {
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST);
            return JsonPath.using(conf).parse(jsonAsString).read(jsonPath);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error to fetch(always as List) from JSON, jsonPath(%s) \n", jsonPath) + e.getMessage());
        }
    }

    /// Logging the API calls
    private static void logRequest(JSONObject reqDetails) {
        Logger.info("------------- Request --------------");
        Logger.info_asJSON(reqDetails.toString());
    }

    private static void logResponse(Response response) {
        Logger.info("------------- Response --------------");
        JSONObject resDetails = new JSONObject();
        resDetails.put("StatusCode", response.statusCode());
        resDetails.put("ContentType", response.contentType());
        resDetails.put("ResponseBody", response.body().asString());
        resDetails.put("ResTime(ms)", response.getTime());
        Logger.info_asJSON(resDetails.toString());
    }

}
