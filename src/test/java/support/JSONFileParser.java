package support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.FileReader;

public class JSONFileParser {
    private static final ObjectMapper objectMapper=new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                                     .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static String getJSONFromFile(String path){
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try {
            Object obj = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "JSON load failed";
    }

    public static <T> String mapPOJOtoJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
