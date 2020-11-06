package service;

import model.Database;
import model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Crawler {

    protected Database database = Database.getInstance();
    private static Crawler instance = null;

    public static Crawler getInstance(){
        if(instance == null)
            instance = new Crawler();
        return instance;
    }

    public Entity findById(String id) {
        for (Entity e: database.getEntities()){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    public List<Entity> findByType(String type) {
        List <Entity> entityList = new ArrayList<>();
        for (Entity e: database.getEntities()) {
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

    public List<Entity> findByValue(List<Entity> entities, String operator, String name, String value) {
        List <Entity> result = new ArrayList<>();
        for (Entity e: entities) {
            for (String key: e.getAttributes().keySet()){
                if (name.equals(key) && value.equals(e.getAttributes().get(key))) {
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }
}
