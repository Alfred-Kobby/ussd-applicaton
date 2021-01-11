package com.example.ussdapplication.utility;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, valueType);
    }

    public static Map<Integer, String> jsonToMap(String json) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        try {

            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(json, new TypeReference<Map<Integer, String>>(){});
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> jsonToMapKeyAsString(String json) {
        Map<String, String> map = new HashMap<String, String>();
        try {

            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }



    public static String mapToJson(Map<Integer, String> map) {
        String json = "";
        try {

            ObjectMapper mapper = new ObjectMapper();

            json = mapper.writeValueAsString(map);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static String mapToJsonString(Map<String, String> map) {
        String json = "";
        try {

            ObjectMapper mapper = new ObjectMapper();

            json = mapper.writeValueAsString(map);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
