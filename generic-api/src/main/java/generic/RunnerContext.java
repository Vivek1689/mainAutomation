package generic;

import io.restassured.response.Response;
import lombok.Data;

import java.util.HashMap;

@Data
public class RunnerContext {
    public static ThreadLocal<BaseRequest> baseRequest = new ThreadLocal<>();
    public static ThreadLocal<Response> response = new ThreadLocal<>();

    public static ThreadLocal<Object> anyData = new ThreadLocal<>();
    private static ThreadLocal<HashMap<String, Object>> dataMap = new ThreadLocal<>();

    public static HashMap<String, Object> getDataMap() {
        if (dataMap.get() == null) return null;
        else return dataMap.get();
    }

    public static Object getDataMapValue(String key) {
        if (dataMap.get() == null) return null;
        else return dataMap.get().get(key);
    }

    public static void setDataMap(String key, Object value) {
        HashMap<String, Object> mapItem = new HashMap<>();
        mapItem.put(key, value);
        if(dataMap.get()!=null){
            HashMap<String, Object> tempMap = dataMap.get();
            tempMap.putAll(mapItem);
            dataMap.set(tempMap);
        }else {
            dataMap.set(mapItem);
        }
    }
}
