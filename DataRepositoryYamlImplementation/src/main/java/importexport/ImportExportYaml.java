package importexport;

import model.Entity;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import utils.FileUtils;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ImportExportYaml implements IImportExport {

    private static ImportExportYaml instance = null;
    private Yaml yaml = new Yaml();

    public static IImportExport getInstance(){
        if (instance == null)
            instance = new ImportExportYaml();
        return instance;
    }
/*
    @Override
    public String importFile(String sourcePath) {
        String yamlString = FileUtils.fileToString(sourcePath);
        return yamlString;
    }*/

    @Override
    public List<Entity> importEntities(String sourcePath) {
        List<Entity> entities = new ArrayList<>();
        String fileString = FileUtils.fileToString(sourcePath);

        if (!fileString.isEmpty()) {
            entities = yaml.load(fileString);
        }

        return entities;

    }
/*
    @Override
     public void exportFile(String destinationPath, String data) {
        FileUtils.stringToFile(destinationPath, data);
    } */

    @Override
    public void exportEntities(String destinationPath, List <Entity> entities) {
        String fileString = new String();
        fileString = yaml.dump(entities);

        FileUtils.stringToFile(destinationPath, fileString);

    }

}
