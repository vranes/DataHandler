package service;

import model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage {

    // Ideja je da ovde budu metode za CRUD operacije i filtriranje
    // Koje ce da barataju Database-om (u njemu je lista svih entiteta i samo najosnovnije metode)

    public abstract List<Entity> read (String path);
    public abstract void add (String path, Entity entity);  // TODO generisanje vs zadavanje ID-ja?
    public abstract void delete(String path, Entity entity);


    public void add(String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities){
        Entity entity = new Entity(id, name);
        entity.setAttributes(attributes);
        entity.setNestedEntities(nestedEntities);
        add(path, entity);
    }

    public List<Entity> readAll(String path) {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < Database.getInstance().getFilesNum(); i++){
            entities.addAll(read(path + Integer.toString(i)));
        }
        //TODO da li ovde azurirati broj entiteta u bazi? ne - bolje negde drugo ali nez gde
        return entities;
    }

    public void delete(String path, String id) {                // TODO rijesiti find-om
        for (Entity e : Database.getInstance().getEntities()) {
            if (e.getId().equals(id)) {
                delete(path, e);
                break;
            }
        }
    }

    public Entity findById(String id) {
        for (Entity e: Database.getInstance().getEntities()){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    public List<Entity> findByType(String type) {
        List <Entity> entityList = new ArrayList<>();
        for (Entity e: Database.getInstance().getEntities()) {
            if (e.getType().equals(type))
                entityList.add(e);
        }
        return entityList;
    }

    /*
 (na primer: vrati sve entitete sa nazivom student koji sadrže ključ
 studijskiProgram i ime im počinje na M),
 */

    public List<Entity> findByAttribute(List<Entity> entities, String name) {
        List <Entity> result = new ArrayList<>();
        for (Entity e: entities) {
            for (String attribute: e.getAttributes().keySet()){
                if (attribute.equals(name)) {
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }
// TODO proslijedjujemo i listu entiteta koji se pretrazuju da bi mogli da se kombinuju ovi upiti

    public List<Entity> findByValue(List<Entity> entities, String operator, String name, String value) {
        List <Entity> result = new ArrayList<>();
        for (Entity e: entities) {
            for (String attribute: e.getAttributes().keySet()){
                if (attribute.equals(name)) {
                    //TODO proveriti operator
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }

}
