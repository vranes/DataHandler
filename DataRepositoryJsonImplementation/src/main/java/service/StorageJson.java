package service;

import Exceptions.IdentifierException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Database;
import model.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageJson extends AbstractStorage {

    private static StorageJson instance = null;
    private Database database = Database.getInstance();

    public static AbstractStorage getInstance(){
        if (instance == null)
            instance = new StorageJson();
        return instance;
    }

    @Override
    public List<Entity> read(String path) {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportJson.getInstance();
        entities = importExport.importEntities(path);
        for(Entity e : entities)
            Database.getInstance().addEntity(e);
        return entities;
    }

    @Override
    public void add (String path, Entity entity) throws IdentifierException {
        if (!OrderProvider.getInstance().isAvailableID(entity.getId())){
            throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }
        int fileNo = OrderProvider.getInstance().availableFile();
        path += Integer.toString(fileNo);
        IImportExport importExport = ImportExportJson.getInstance();
        List<Entity> entities = null;
        entities = importExport.importEntities(path);
        entities.add(entity);
        importExport.exportEntities(path, entities);

        database.addEntity(entity);
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        database.getFiles().get(fileNo).add(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws IdentifierException {

        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        String filename =  "/file"+Integer.toString(fileNo);
        String filePath = path.concat(filename);
        String absolutePath = new File("").getAbsolutePath() + filePath;
        File file = new File(absolutePath);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array = null;
        try {
            array = (ArrayNode) objectMapper.readTree(file);
            String id = entity.getId();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("id").asText().equals(String.valueOf(id))) {
                    array.remove(i);
                    break;
                }
            }
            String formattedJson = objectMapper.writeValueAsString(array);
            ImportExportJson.getInstance().exportFile(filePath, formattedJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.getEntities().remove(entity);
        database.getFiles().get(fileNo).remove(entity);
    }

          /*  try {
            String fileString = importExport.importFile(path);
            JsonReader jr = new JsonReader(new StringReader(fileString));
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
        } */

}
