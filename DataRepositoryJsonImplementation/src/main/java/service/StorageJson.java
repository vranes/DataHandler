package service;

import Exceptions.IdentifierException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Database;
import model.Entity;
import utils.FileUtils;

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
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        List<Entity> entities = Database.getInstance().getFiles().get(fileNo);
        entities.add(entity);
        path += Integer.toString(fileNo);
        IImportExport importExport = ImportExportJson.getInstance();
        importExport.exportEntities(path, entities);
        database.addEntity(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws IdentifierException {

        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        String filename =  Integer.toString(fileNo);
        String filePath = path.concat(filename);
        String absolutePath = filePath;
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
            FileUtils.stringToFile(filePath, formattedJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.getEntities().remove(entity);
        database.getFiles().get(fileNo).remove(entity);
    }

    @Override
    public void refresh(Entity ent, String path) throws IdentifierException {
        if (OrderProvider.getInstance().isAvailableID(ent.getId())){
            throw new IdentifierException("An entity with id: " + ent.getId() + " doesnt exists");}
        Entity toEdit = Crawler.getInstance().findById(ent.getId());

        List <Entity> entities = Database.getInstance().getFiles().get(
                OrderProvider.getInstance().locateInFile(toEdit)
        );
        Database.getInstance().getEntities().remove(toEdit);
        entities.remove(toEdit);
        if(!ent.getType().equals(""))
            toEdit.setType(ent.getType());
        if(!ent.getNestedEntities().isEmpty())
            toEdit.setNestedEntities(ent.getNestedEntities());
        if(!ent.getAttributes().isEmpty())
            toEdit.setAttributes(ent.getAttributes());
        entities.add(toEdit);
        Database.getInstance().getEntities().add(toEdit);

        ImportExportJson.getInstance().exportEntities(path, entities);
    }

    @Override
    public void addnested(String path, Entity toAdd, String parentId, String key) throws IdentifierException {
        if (OrderProvider.getInstance().isAvailableID(parentId)) {
            throw new IdentifierException("An entity with id: " + parentId + " doesnt exists");
        }

        List <Entity> entities = Database.getInstance().getFiles().get(
                OrderProvider.getInstance().locateInFile(Crawler.getInstance().findById(parentId))
        );
        Entity et = Crawler.getInstance().findById(parentId);
        Database.getInstance().getEntities().remove(et);
        entities.remove(et);
        et.getNestedEntities().put(key,toAdd);
        entities.add(et);
        Database.getInstance().getEntities().add(et);

        ImportExportJson.getInstance().exportEntities(path, entities);

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
