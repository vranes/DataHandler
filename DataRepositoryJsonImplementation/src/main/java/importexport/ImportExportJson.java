package importexport;

import Exceptions.FormatException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import model.Entity;
import utils.FileUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ImportExportJson implements IImportExport {
    
    private static ImportExportJson instance = null;
    
    public static IImportExport getInstance(){
        if (instance == null)
            instance = new ImportExportJson();
        return instance;
    }

    private String importFile(String sourcePath) throws Exception {
        String jsonString = FileUtils.fileToString(sourcePath);
        if (!jsonString.isEmpty())
            jsonString = beautifyJson(jsonString);
        return jsonString;
    }

    @Override
    public List<Entity> importEntities(String sourcePath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Entity> entities = new ArrayList<>();
        String fileString = importFile(sourcePath);
        if (!fileString.isEmpty()) {
            try {
                entities = objectMapper.readValue(fileString, new TypeReference<List<Entity>>(){});
                if (entities == null) throw new FormatException("Expected JSON format not found");
            } catch (Exception e) {
                throw new FormatException("Expected JSON format not found");
            }
        }
        return entities;
    }

    private void exportFile(String destinationPath, String json) throws Exception {
        FileUtils.stringToFile(destinationPath, beautifyJson(json));
    }

    @Override
    public void exportEntities(String destinationPath, List<Entity> entities) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileString = new String();
        try{
            fileString = objectMapper.writeValueAsString(entities);
            if (fileString == null) throw new FormatException("Unexpected object format");
        } catch (Exception e){
            throw new FormatException("Unexpected object format");
        }
        exportFile(destinationPath, fileString);
    }

    private String beautifyJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try{
            JsonNode tree = objectMapper.readTree(json);
            String formattedJson = objectMapper.writeValueAsString(tree);
            return formattedJson;
        } catch (Exception e) {
            throw new FormatException("Expected JSON format not found");
        }
    }

}
