package service;

import model.Entity;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.HashMap;
import java.util.Map;

public class ObjectConverterYaml implements IObjectConverter {

    private static ObjectConverterYaml instance;
    Yaml yaml = new Yaml();

    public static ObjectConverterYaml getInstance(){
        if (instance == null)
            instance = new ObjectConverterYaml();
        return instance;
    }

    @Override
    public String objectToFormat(Object object) throws Exception {
        return yaml.dump(object);
    }

    @Override
    public Object formatToObject(String entity, Class<?> classOf) throws Exception {
        return  yaml.loadAs(entity, classOf);
    }
}

/*
    @Override
    public String objectToFormat(Object object) throws Exception {
        String jsonString = gson.toJson(object);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(jsonString);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }

    @Override
    public Object formatToObject(String json, Class<?> classOf) throws Exception {
        return gson.fromJson(json, classOf);
    }*/