package importexport;

import Exceptions.FormatException;
import model.Entity;
import service.CustomMapper;
import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportExportCustom implements IImportExport {


    private static ImportExportCustom instance = null;

    public static IImportExport getInstance(){
        if (instance == null)
            instance = new ImportExportCustom();
        return instance;
    }

    public String importFile(String sourcePath){
        String fileString = FileUtils.fileToString(sourcePath);

        return fileString;
    }

    public List<Entity> importEntities(String sourcePath){
        CustomMapper objectMapper = new CustomMapper();
        List<Entity> entities = new ArrayList<>();
        String fileString = importFile(sourcePath);
        if (!fileString.isEmpty()) {
            try {
                entities = objectMapper.readValueAsList(fileString);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e){
                e.printStackTrace();
            }
        }
        return entities;
    }

    public void exportFile(String destinationPath, String fileString){
        FileUtils.stringToFile(destinationPath, fileString);
    }

    public void exportEntities(String destinationPath, List<Entity> entities) {

        CustomMapper objectMapper = new CustomMapper();
        String fileString = new String();
        try {
            fileString = objectMapper.writeValueAsString(entities);
            exportFile(destinationPath, fileString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
