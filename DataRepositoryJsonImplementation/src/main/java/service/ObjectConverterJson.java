package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

public class ObjectConverterJson implements IObjectConverter {

    private static ObjectConverterJson instance;
    private Gson gson = new Gson();

    public synchronized static ObjectConverterJson getInstance() {
        if (instance == null)
            instance = new ObjectConverterJson();
        return instance;
    }

    @Override
    public String objectToFormat(Object object) throws Exception {
        String jsonString = gson.toJson(object);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(jsonString);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }

    public JsonNode objectToNode(Object object) throws Exception {
        String jsonString = gson.toJson(object);
//TODO pitati da li implementacija sme da ima nenasledjene metode u implementiranom interfejsu
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode node = objectMapper.readTree(jsonString);

        return node;
    }

    @Override
    public Object formatToObject(String json, Class<?> classOf) throws Exception {
        return gson.fromJson(json, classOf);
    }

}
