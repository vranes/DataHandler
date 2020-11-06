package service;

import Exceptions.IdentifierException;
import model.Database;
import model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage {

    // Ideja je da ovde budu metode za CRUD operacije i filtriranje
    // Koje ce da barataju Database-om (u njemu je lista svih entiteta i samo najosnovnije metode)
    protected Database database = Database.getInstance();
    public abstract List<Entity> read (String path);
    public abstract void add (String path, Entity entity) throws IdentifierException;
    public abstract void delete(String path, Entity entity) throws IdentifierException;

    public void loadDatabase(String path) {
        List<Entity> entities = new ArrayList<>();
        entities = readAll(path);
        database.setEntities(entities);
    }

    public List<Entity> readAll(String path) {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < Database.getInstance().getFilesNum(); i++){
            List<Entity> fileEntities = (read(path + Integer.toString(i)));
            entities.addAll(fileEntities);
            database.getFiles().put(i, fileEntities);
        }

        return entities;
    }

    public void add(String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities) throws IdentifierException {
        Entity entity = new Entity(id, name);
        entity.setAttributes(attributes);
        entity.setNestedEntities(nestedEntities);
        add(path, entity);
    }

    public void deleteID(String path, String id) throws IdentifierException {
        Entity e = Crawler.getInstance().findById(id);
        delete(path, e);
    }

    public void deleteAll(String path, List<Entity> list) throws IdentifierException {
        for (Entity e: list){
            delete(path, e);
        }
    }


}
