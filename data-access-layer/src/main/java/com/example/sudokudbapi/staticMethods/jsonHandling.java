package com.example.sudokudbapi.staticMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonHandling {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Object jsonToObject(String json, Object dataType) {
        Object res = null;

        try {
            res = mapper.readValue(json,dataType.getClass());
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static <G> String objectToJson(G object) {
        String res = "";

        try {
            res = mapper.writeValueAsString(object);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        return res;
    }
}
