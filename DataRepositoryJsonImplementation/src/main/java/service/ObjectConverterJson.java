package service;

import Exceptions.FormatException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

public class ObjectConverterJson implements IObjectConverter {

    private static ObjectConverterJson instance;
    private Gson gson = new Gson();

    public synchronized static IObjectConverter getInstance() {
        if (instance == null)
            instance = new ObjectConverterJson();
        return instance;
    }

    @Override
    public String objectToFormat(Object object) throws FormatException{
        try {
            String jsonString = gson.toJson(object);
            if (jsonString == null) throw new FormatException("Unexpected object format");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            JsonNode tree = objectMapper.readTree(jsonString);
            String formattedJson = objectMapper.writeValueAsString(tree);
            return formattedJson;
        } catch (Exception e) {
            throw new FormatException("Unexpected object format");
        }
    }

    @Override
    public Object formatToObject(String json, Class<?> classOf) throws FormatException{
        try {
            Object o =  gson.fromJson(json, classOf);
            if (o == null) throw new FormatException("Expected JSON format not found");
            return o;
        }
        catch (Exception e) {
            throw new FormatException("Expected JSON format not found");
        }
    }

}
