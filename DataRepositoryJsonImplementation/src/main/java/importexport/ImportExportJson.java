package importexport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import model.Entity;
import service.Database;
import utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ImportExportJson implements IImportExport {
    
    private static ImportExportJson instance = null;
    
    public static IImportExport getInstance(){           // TODO da li da bude instanca interfejsa
        if (instance == null)
            instance = new ImportExportJson();
        return instance;
    }

    public void importFile (String filePath) throws IOException {
        IImportExport importExport = ImportExportJson.getInstance();
        try {
            importExport.importFile(filePath);
        } catch (IOException e) {
            System.out.println("greska pri ucitavanju");
            // TODO gui ERROR HANDLER a ne ovde
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
                Database.getInstance().addEntity(newEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void exportFile(String destinationPath, String json) throws IOException {
      /*
         File file = new File("");
        file = new File(file.getAbsolutePath() + destinationPath);
        file.setWritable(true);
        FileUtils.stringToFile(file, beautifyJson(json));
       */

    }
/*
    public String importFile(String sourcePath) throws IOException {
        String jsonString = FileUtils.fileToString(sourcePath);

        return beautifyJson(jsonString);
    }

    private String beautifyJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(json);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }
*/
}
