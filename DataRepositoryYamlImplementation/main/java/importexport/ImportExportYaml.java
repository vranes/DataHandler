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

    public static IImportExport getInstance(){           // TODO da li da bude instanca interfejsa
        if (instance == null)
            instance = new ImportExportYaml();
        return instance;
    }

    @Override
    public String importFile(String sourcePath) throws IOException {
        return null;
    }

    @Override
    public void exportFile(String destinationPath, String data) throws IOException {

    }

//    @Override
//    public List<Entity> importFileEntities(String sourcePath) throws IOException {
//
//
//        Constructor constructor = new Constructor();
//        constructor.addTypeDescription(new TypeDescription(Entity.class));
//
//        Yaml yaml = new Yaml(constructor);
//
//// Load categories and products
//        try (InputStream in = new FileInputStream(new File(sourcePath))) {
//            List<Entity> entities = new ArrayList<Entity>();
//
//            Iterable<Object> allYamlObjects = yaml.loadAll(input);
//
//            for (Iterator<?> yamlObjectIterator = allYamlObjects.iterator(); yamlObjectIterator.hasNext(); ) {
//                List<?> yamlObjects = (List<?>) yamlObjectIterator.next();
//                for (Object yamlObject : yamlObjects) {
//                    if (yamlObject instanceof Entity) {
//                        entities.add((Entity) yamlObject);
//                    }
//                }
//            }
//
//
//            return entities;
//        }
//
//    }


}
