package generic;

import enums.RequestType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.urlEncodingEnabled;

@Accessors(chain = true)
@Setter
@Getter
public class BaseRequest {
    private String url;
    private RequestType requestType;
    private int responseCode = 0;
    private Object body;
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> pathParams = new HashMap<>();
    private Map<String, Object> formParams = new HashMap<>();
    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> basicAuth = new HashMap<>();
    private Map<String, Object> multiPart = new HashMap<>();
    private RestClient restClient2 = new RestClient();

    public BaseRequest(RequestType requestType, String url){
        this.requestType = requestType;
        this.url = url;
    }

    public BaseRequest setResponseCode (int responseCode){
        this.responseCode = responseCode;
        return this;
    }
    public BaseRequest setHeader(String key, Object value){
        this.headers.put(key,value);
        return this;
    }
    public BaseRequest setPathParam(String key, Object value){
        this.pathParams.put(key,value);
        return this;
    }
    public BaseRequest setFormParam(String key, Object value){
        this.formParams.put(key,value);
        return this;
    }
    public BaseRequest setQueryParam(String key, Object value){
        this.queryParams.put(key,value);
        return this;
    }
    public BaseRequest setMultiPart(String key, Object value){
        this.multiPart.put(key,value);
        return this;
    }
    public BaseRequest setBasicAuth(String key, Object value){
        this.basicAuth.put(key,value);
        return this;
    }

//    public BaseRequest setBody(String jsonFilePath, Map<String,Object> valuesMap) throws Exception {
//        if(Utilities.isNull(jsonFilePath)) throw new Exception("Payload file path is null or empty");
//        URL url = BaseRequest.class.getClassLoader().getResource(jsonFilePath);
//        String payload = "";
//        if(url.toString().contains("jar:")){
//            payload = new Scanner(getClass().getClassLoader().getResourceAsStream(jsonFilePath), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
//        }
//        else {
//            File f = new File(url.getPath());
//            payload = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
//        }
//        if(valuesMap != null && !valuesMap.isEmpty()){
//            StringSubstitutor sub = new StringSubstitutor(valuesMap);
//            //sub.setVariablePrefix("\"");
//            //sub.setVariableSuffix("\"");
//            payload = sub.replace(payload);
//        }
//        this.setBody(payload);
//        return this;
//    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Method = ").append(this.requestType).append("\n");
        s.append("Url = ").append(this.url).append("\n");
        s.append("Headers = ").append(this.headers.toString());
        s.append("QueryParams = ").append(this.queryParams.toString()).append("\n" );
        s.append("PathParams = ").append(this.pathParams.toString()).append("\n");
        s.append("FormParams = ").append(this.formParams.toString()).append("\n");
        s.append("MultiPart file = ").append(this.multiPart.toString()).append("\n");
        s.append("RequestBody = ").append(this.body).append("\n");
        return s.toString();
    }


}