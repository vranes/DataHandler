package importexport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Entity;
import utils.FileUtils;
import java.io.*;
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
        if (!jsonString.isEmpty())
            jsonString = beautifyJson(jsonString);
        return jsonString;
    }

    public List<Entity> importEntities(String sourcePath){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Entity> entities = new ArrayList<>();
        String fileString = importFile(sourcePath);
        if (!fileString.isEmpty()) {
            try {
                entities = objectMapper.readValue(fileString, new TypeReference<List<Entity>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return entities;
    }

    public void exportFile(String destinationPath, String json){
        FileUtils.stringToFile(destinationPath, beautifyJson(json));
    }

    public void exportEntities(String destinationPath, List<Entity> entities) {

        ObjectMapper objectMapper = new ObjectMapper();
        String fileString = new String();
        try {
            fileString = objectMapper.writeValueAsString(entities);
            exportFile(destinationPath, fileString);
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
