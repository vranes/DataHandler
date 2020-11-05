package service;

import Exceptions.IdentifierException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Database;
import model.Entity;

import javax.xml.crypto.Data;
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
        return entities;
    }

    @Override
    public void add (String path, Entity entity) throws IdentifierException {
        path = path + OrderProvider.getInstance().availableFile();
        for (Entity e: database.getEntities()){
            if(e.getId().equals(entity.getId()))
                throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }
        database.addEntity(entity);

        int fileNo = database.getNumberOfEntities() / database.getMaxEntities();
        path += Integer.toString(fileNo);
        IImportExport importExport = ImportExportJson.getInstance();
        List<Entity> entities = null;
        entities = importExport.importEntities(path);
        entities.add(entity);
        importExport.exportEntities(path, entities);
    }

    @Override
    public void delete(String path, Entity entity) {    //TODO dodati da obidje celu bazu fajl po fajl
        database.getEntities().remove(entity);
        File jsonFile = new File(path).getAbsoluteFile();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array = null;
        try {
            array = (ArrayNode) objectMapper.readTree(jsonFile);
            String id = entity.getId();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("id").equals(id)) {
                    array.remove(i);
                    break;
                }
            }
            String formattedJson = objectMapper.writeValueAsString(array);
            ImportExportJson.getInstance().exportFile(path, formattedJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
