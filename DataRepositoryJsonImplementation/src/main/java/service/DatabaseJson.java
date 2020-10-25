package service;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Entity;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseJson extends Database {

    public void load(String filePath) {
        IImportExport importExport = ImportExportJson.getInstance();
        try {
            importExport.importFile(filePath);
        } catch (IOException e) {
            System.out.println("greska pri ucitavanju");
            // TODO gui ERROR HANDLER
        }
/*
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        String color = jsonNode.

        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){}); // za json array

        Map<String, Object> map
                = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
*/
        try {
            JsonReader jr = new JsonReader(new FileReader(filePath));
            while (jr.peek() == JsonToken.BEGIN_OBJECT) {
                Entity newEntity = new Entity();
                while (jr.peek() != JsonToken.END_OBJECT) {
                    String name = jr.nextName();
                    if (name == "id")
                        newEntity.setId(jr.nextString());
                    else if (name == "type")
                        newEntity.setType(jr.nextString());
                    else if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                        Entity nestedEntity = new Entity();
                        while (jr.peek() != JsonToken.END_OBJECT) {
                            String nestedName = jr.nextName();
                            if (nestedName == "id")
                                nestedEntity.setId(jr.nextString());
                            else if (name == "type")
                                nestedEntity.setType(jr.nextString());
                            else {
                                nestedEntity.addAttribute(jr.nextName(), jr.nextString());
                            }
                        }
                        newEntity.addNestedEntity(name, nestedEntity);
                    } else {
                        newEntity.addAttribute(name, jr.nextString());
                    }
                }
                entities.add(newEntity);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}