package com.example.sudokudbapi.staticMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public final class JsonHandling {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <G> G jsonToObject(final String json, final G dataType) {
        G res = null;

        try {
            Object tmp = mapper.readValue(json,dataType.getClass());
            res = tmp != null && tmp.getClass() == dataType.getClass() ? (G) tmp : null;
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static <G> String objectToJson(final G object) {
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
