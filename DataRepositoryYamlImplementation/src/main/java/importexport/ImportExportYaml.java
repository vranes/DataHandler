package importexport;

import Exceptions.FormatException;
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

    @Override
    public List<Entity> importEntities(String sourcePath) throws Exception {
        List<Entity> entities = new ArrayList<>();
        String fileString = FileUtils.fileToString(sourcePath);
        if (!fileString.isEmpty()) {
            try {
                entities = yaml.load(fileString);
                if (entities == null) throw new FormatException("Expected YAML format not found");
                return entities;
            }
            catch (Exception e) {
                throw new FormatException("Expected YAML format not found");
            }
        }
        return entities;
    }

    @Override
    public void exportEntities(String destinationPath, List <Entity> entities) throws Exception {
        String fileString = new String();
        try{
            fileString = yaml.dump(entities);
            if (fileString == null) throw new FormatException("Unexpected object format");
        }
        catch (Exception e) {
            throw new FormatException("Unexpected object format");
        }

        FileUtils.stringToFile(destinationPath, fileString);
    }

}
