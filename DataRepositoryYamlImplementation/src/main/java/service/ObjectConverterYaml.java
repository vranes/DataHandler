package service;

import Exceptions.FormatException;
import org.yaml.snakeyaml.Yaml;

public class ObjectConverterYaml implements IObjectConverter {

    private static ObjectConverterYaml instance;
    Yaml yaml = new Yaml();

    public static ObjectConverterYaml getInstance(){
        if (instance == null)
            instance = new ObjectConverterYaml();
        return instance;
    }

    @Override
    public String objectToFormat(Object object) throws FormatException {
        try{
            String s = yaml.dump(object);
            if (s == null) throw new FormatException("Unexpected object format");
            return s;
        }
        catch (Exception e) {
            throw new FormatException("Unexpected object format");
        }
    }

    @Override
    public Object formatToObject(String entity, Class<?> classOf) throws FormatException {
        try {
            Object o = yaml.loadAs(entity, classOf);
            if (o == null) throw new FormatException("Expected YAML format not found");
            return o;
        }
        catch (Exception e) {
            throw new FormatException("Expected YAML format not found");
        }
    }
}