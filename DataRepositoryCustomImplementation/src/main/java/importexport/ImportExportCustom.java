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

    @Override
    public List<Entity> importEntities(String sourcePath) throws Exception {
        CustomMapper objectMapper = new CustomMapper();
        List<Entity> entities = new ArrayList<>();
        String fileString = FileUtils.fileToString(sourcePath);
        if (!fileString.isEmpty()) {
            entities = objectMapper.readValueAsList(fileString);
        }
        return entities;
    }

    @Override
    public void exportEntities(String destinationPath, List<Entity> entities) throws Exception {
        CustomMapper objectMapper = new CustomMapper();
        String fileString = new String();
        fileString = objectMapper.writeValueAsString(entities);
        FileUtils.stringToFile(destinationPath, fileString);
    }

}
