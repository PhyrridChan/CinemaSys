package JSON;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangfan PhyrridChan@outlook.com
 * @see com.alibaba.fastjson
 */
public class JSONhandle {
    static JSONObject jsonObject = new JSONObject();
    private static final String JsonFilePath = "/Users/zhangfan/Documents/CinemaSys/src/JSON/locationInfo.json";
    static Charset charset = StandardCharsets.UTF_8;
    static JSONArray refList = new JSONArray();

    public static void main(String[] args) throws IOException {
        System.out.println(readJSONData());
    }


    public JSONhandle() throws IOException {
        setJsonObject(JsonFilePath, charset);
        refList = getJsonArray("ref");
    }

    /**
     * @param path :JsonFilePath
     * @return JsonFileContent
     */
    public static String readJSONData(String path, Charset charset) throws IOException {
        StringBuilder strBuffer = new StringBuilder();
        File JsonFile = new File(path);
        if (!JsonFile.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream jsonInputStream = new FileInputStream(JsonFile);
        InputStreamReader inputStreamReader = new InputStreamReader(jsonInputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = reader.readLine()) != null) {
            strBuffer.append(str);
        }
        reader.close();
        return strBuffer.toString();
    }

    /**
     * {@link #readJSONData(String, Charset)}
     */
    public static String readJSONData() throws IOException {
        return readJSONData(JsonFilePath, StandardCharsets.UTF_8);
    }

    public static String readJSONData(Charset charset) throws IOException {
        return readJSONData(JsonFilePath, charset);
    }

    public static String readJSONData(String path) throws IOException {
        return readJSONData(path, charset);
    }

    /**
     * @param path
     * @param charset
     * @throws IOException
     */
    public void setJsonObject(String path, Charset charset) throws IOException {
        jsonObject = JSONObject.parseObject(readJSONData(path, charset));
    }

    /**
     * {@link #setJsonObject(String, Charset)}
     */
    public void setJsonObject(String path) throws IOException {
        setJsonObject(path, charset);
    }

    public void setJsonObject(Charset charset) throws IOException {
        setJsonObject(JsonFilePath, charset);
    }

    public void setJsonObject(JSONObject jsonObject) {
        JSONhandle.jsonObject = jsonObject;
    }

    /**
     * @return JSONObject
     */
    public static JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * get JSONArray from a JSONObject
     *
     * @param key
     * @return
     */
    public static JSONArray getJsonArray(String key) {
        return jsonObject.getJSONArray(key);
    }

    /**
     * get a str at a specific index from an array whose name is $(key)
     *
     * @param key
     * @param index
     * @return str
     */
    public static String searchJsonArray(String key, int index) {
        JSONArray jsonArray = getJsonArray(key);
        return jsonArray.getString(index);
    }

    public static String searchJsonArray(String key, String index) {
        return searchJsonArray(key, Integer.parseInt(index));
    }

    public static String searchJsonArray(String key, int x, int y) {
        JSONArray jsonArray = getJsonArray(key).getJSONArray(x);
        return jsonArray.getString(y);
    }

    public static String searchJsonArray(String key, String x, String y) {
        return searchJsonArray(key, Integer.parseInt(x), Integer.parseInt(y));
    }
}
