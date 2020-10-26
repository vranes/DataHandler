package importexport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import model.Entity;
import utils.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImportExportJson implements IImportExport {
    
    private static ImportExportJson instance = null;
    
    public static IImportExport getInstance(){           // TODO da li da bude instanca interfejsa
        if (instance == null)
            instance = new ImportExportJson();
        return instance;
    }

    public List<Entity> importFileEntities (String sourcePath) throws IOException {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportJson.getInstance();

/*
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(file);
        String color = jsonNode.

        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){}); // za json array

        Map<String, Object> map
                = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
*/
        try {
            JsonReader jr = new JsonReader(new FileReader(sourcePath));
            while (jr.peek() == JsonToken.BEGIN_OBJECT) {
                Entity newEntity = new Entity();
                while (jr.peek() != JsonToken.END_OBJECT) {
                    String name = jr.nextName();
                    if (name.equals("id"))
                        newEntity.setId(jr.nextString());
                    else if (name.equals("type"))
                        newEntity.setType(jr.nextString());
                    else if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                        Entity nestedEntity = new Entity();
                        while (jr.peek() != JsonToken.END_OBJECT) {
                            String nestedName = jr.nextName();
                            if (nestedName.equals("id"))
                                nestedEntity.setId(jr.nextString());
                            else if (nestedName.equals("type"))
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public String importFileString (String sourcePath) throws IOException {
        String jsonString = FileUtils.fileToString(sourcePath);

        return beautifyJson(jsonString);
    }

    public void exportFile(String destinationPath, String json) throws IOException {

        File file = new File("");
        file = new File(file.getAbsolutePath() + destinationPath);
        file.setWritable(true);
        FileUtils.stringToFile(file, beautifyJson(json));
    }

    private String beautifyJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(json);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }

}
