package support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class SupportFunctions {
    private static Response response;
    private static ObjectMapper mapper = new ObjectMapper();

    public static String createFileLocationPath(String Json){
        return "src/test/resources/" + Json + ".json";
    }

    public static Response post(String url, String json) {
        response = RestAssured.given()
                .header("content-type", MyConfig.CONTENT_TYPE)
                .body(json)
                .contentType(ContentType.JSON)
                .when()
                .post(url);

        return response;
    }

    public static ResponseBody put(String url, String json) {
        response = RestAssured.given()
                .header("content-type", MyConfig.CONTENT_TYPE)
                .body(json)
                .contentType(ContentType.JSON)
                .when()
                .put(url);

        return response.getBody();
    }

    public static ResponseBody delete(String url) {
        response = RestAssured.given()
                .header("content-type", MyConfig.CONTENT_TYPE)
                .contentType(ContentType.JSON)
                .when()
                .delete(url);

        return response.getBody();
    }

    public static ResponseBody get(String url){
        response = RestAssured.given()
                .when()
                .get(url);
        return response.getBody();
    }

    public static <T> T convertResponse(Class<T> classObj) throws IOException {
        JSONObject jsonObject = new JSONObject(response.asString());
        return mapper.readValue(jsonObject.toString(), classObj);
    }

    public static <T> T[] convertResponseArray(Class<T[]> classObj) throws IOException {
        JSONArray jsonArray = new JSONArray(response.asString());
        System.out.println(jsonArray);
        return mapper.readValue(jsonArray.toString(), classObj);
    }
    public static String getResponseCode(){
        return response.getHeader("Response" );
    }
}
