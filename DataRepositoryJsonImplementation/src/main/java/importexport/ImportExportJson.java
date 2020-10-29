package importexport;

import com.fasterxml.jackson.core.type.TypeReference;
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

    public String importFile(String sourcePath){
        String jsonString = FileUtils.fileToString(sourcePath);

        return beautifyJson(jsonString);
    }

    public List<Entity> importEntities(String sourcePath){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Entity> entities = null;
        try {
            entities = objectMapper.readValue(sourcePath, new TypeReference<List<Entity>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public void exportFile(String destinationPath, String json){

        File file = new File("");
        file = new File(file.getAbsolutePath() + destinationPath);
        file.setWritable(true);
        FileUtils.stringToFile(file, beautifyJson(json));
    }

    public void exportFile(String destinationPath, List<Entity> entities) {
        File file = new File("");
        file = new File(file.getAbsolutePath() + destinationPath);
        file.setWritable(true);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String beautifyJson(String json) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = null;
        String formattedJson = null;
        try {
            tree = objectMapper.readTree(json);
            formattedJson = objectMapper.writeValueAsString(tree);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formattedJson;
    }

}
