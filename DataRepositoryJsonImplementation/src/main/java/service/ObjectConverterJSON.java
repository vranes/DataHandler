package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

public class ObjectConverterJSON implements IObjectConverter {

    private static ObjectConverterJSON instance;
    private Gson gson = new Gson();

    public synchronized static ObjectConverterJSON get() {
        if (instance == null)
            instance = new ObjectConverterJSON();
        return instance;
    }

    @Override
    public String convertObjectToFormat(Object object) throws Exception {
        String jsonString = gson.toJson(object);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(jsonString);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }

    @Override
    public Object convertFormatToObject(String json, Class<?> classOf) throws Exception {
        return gson.fromJson(json, classOf);
    }

}
