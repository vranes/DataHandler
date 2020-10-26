package service;

import importexport.ImportExportJson;
import model.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageJson implements IStorage {

    @Override
    public void add (String path, Entity entity) throws Exception {
        Database.getInstance().addEntity(entity);
        int fileNo = Database.getInstance().getNumberOfEntities() / Database.getInstance().getMaxEntities();

        path += Integer.toString(fileNo);

        String fileString = ImportExportJson.getInstance().importFileString(path);
        String entityString = ObjectConverterJson.getInstance().objectToFormat(entity);
        fileString = fileString + entityString;
    }


    @Override
    public void add (String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities) throws Exception {
        Entity entity = new Entity(id, name);
        entity.setAttributes(attributes);
        entity.setNestedEntities(nestedEntities);
        add(path, entity);
    }

    @Override
    public Entity findByIdLocal(String id) {
        for (Entity e: Database.getInstance().getEntities()){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    @Override
    public List<Entity> findByTypeLocal(String type) {
        return null;
    }
}
