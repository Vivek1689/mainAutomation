package utils.jsonUtil;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import utils.extentReport.ExtentManager;

import java.io.*;
import java.util.*;

public class JsonUtil {

    private JsonUtil() {
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJsonStringFromFile(String path) throws IOException {
        return getObjectMapper().readTree(new File(path));
    }

    public static <T> Map<String, T> getMapFromJson(String json) throws Exception {
        return getObjectMapper().readValue(json, new TypeReference<HashMap<String, T>>() {
        });
    }

    public static String getJsonStringFromObject(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        StringWriter writer = new StringWriter();
        getObjectMapper().writeValue(writer, obj);
        return writer.toString();
    }

    public static JsonNode getJsonNodeFromJsonString(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        JsonNode retval = null;
        if (StringUtils.isNotEmpty(jsonString)) {
            retval = objectMapper.readValue(jsonString, JsonNode.class);
        }
        return retval;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static Object[][] getDataObject(ITestContext ctx, String paramName, String filePath, String jsonHeader) {
        JSONArray arr;
        Object[][] obj = null;
        Integer threadCount;
        String tdFileName;

        try {
            String[] paramValues = ctx.getCurrentXmlTest().getParameter(paramName.trim()).split(",");
            if (paramValues.length == 1) {
                tdFileName = paramValues[0];
                threadCount = 1;
            } else {
                threadCount = Integer.valueOf(paramValues[0].trim());
                tdFileName = paramValues[1];
            }
            ctx.getCurrentXmlTest().getSuite().setDataProviderThreadCount(threadCount);
            File file = new File(filePath + File.separator + tdFileName.trim());
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject json = new JSONObject(content);
            arr = json.getJSONArray(jsonHeader);
            obj = new Object[arr.length()][1];
            for (int i = 0; i < arr.length(); i++) {
                obj[i][0] = (JSONObject) arr.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T extends Object> T getGenericData(Class<T> type, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(new File(filePath));
            return type.cast(mapper.readValue(node.toString(), type));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Object> T getGenericDataForString(Class<T> type, String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (null == jsonString || "".equalsIgnoreCase(jsonString)) {
                ExtentManager.getTest().fail("getGenericDataForString - jsonString is empty/null !");
            }
            return type.cast(mapper.readValue(jsonString, type));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ExtentManager.getTest().fail("getGenericDataForString - Unable to read value !");
        }
        return null;
    }

    public static String readJsonFileAsString(String jsonPath) throws IOException {
        InputStream is = new FileInputStream(jsonPath);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        return fileAsString;
    }

    public static void writeJsonToFile(String filePath, String data) throws IOException {
        JSONArray jsonObj = new JSONArray();
        if (null != data)
            jsonObj.put(convertStringToJson(data));
        else
            ExtentManager.getTest().fail("Data to insert is null !");
        FileWriter file = new FileWriter(filePath);
        if (null != file) {
            BufferedWriter bufferedWriter = new BufferedWriter(file);
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } else
            ExtentManager.getTest().fail("File path is not correct !");
    }

    public static String convertStringToJson(String json) {
        Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
        String pretty = prettyJson.toJson(json);
        return pretty;
    }

    public static JSONObject getDataObject(String filePath, String jsonHeader, String index) {
        try {
            File file = new File(filePath.trim());
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject json = new JSONObject(content);
            org.json.JSONArray arr = json.getJSONArray(jsonHeader);
            if (null != arr.getJSONObject(Integer.parseInt(index)))
                return arr.getJSONObject(Integer.parseInt(index));
            else
                throw new Exception("Data is not present for the index : "+index);
        } catch (Exception ex) {
            ex.printStackTrace();
            ExtentManager.getTest().fail("Failed to parse the json file !");
        }
        return null;
    }

    public static JSONArray getDataObject(String filePath, String jsonHeader) {
        try {
            File file = new File(filePath.trim());
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject json = new JSONObject(content);
            return json.getJSONArray(jsonHeader);
        } catch (Exception ex) {
            ex.printStackTrace();
            ExtentManager.getTest().fail("Failed to parse the json file !");
        }
        return null;
    }

    public static void replaceJsonFileData (Object objectToReplace, String key, String existingValue, String replaceValue) {

        if (null == objectToReplace || null == key || null == existingValue || null == replaceValue)
            ExtentManager.getTest().fail("objectToReplace, key, existingValue, replaceValue can't be null. key can be empty !");
        if (objectToReplace instanceof JSONObject) {
            JSONObject jsonObjectToReplace = (JSONObject) objectToReplace ;
            Object keyValue = jsonObjectToReplace.opt(key);

            if (null != keyValue) {
                if (jsonObjectToReplace.getString(key).toLowerCase().contains(existingValue.toLowerCase())) {
                    String updatedValue = keyValue.toString().replace(existingValue, replaceValue);
                    jsonObjectToReplace.put(key, updatedValue);
                }
            }
        } else if (objectToReplace instanceof JSONArray) {
            JSONArray jsonArrayToReplace = (JSONArray) objectToReplace;

            for (int i = 0; i < jsonArrayToReplace.length(); i++) {
                replaceJsonFileData(jsonArrayToReplace.opt(i), key, existingValue, replaceValue);
            }
        } else if (objectToReplace instanceof String) {
            String stringObjectToReplace = (String) objectToReplace ;
            stringObjectToReplace.replace(existingValue, replaceValue);
        }
    }
}
