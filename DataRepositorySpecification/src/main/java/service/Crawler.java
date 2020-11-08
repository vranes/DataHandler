package service;

import model.Database;
import model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Entity> findByAttribute(List<Entity> entities, List<String> names) {
        List <Entity> result = new ArrayList<>();
        System.out.println(result +" find BY attribute");
        System.out.println(names +" names find BY attribute");
        System.out.println(entities +" find BY attribute");


        for (Entity e: entities) {
            int flag = 0;
            for(String s : names){
                if(e.getAttributes().containsKey(s)) flag = 1;
                else {
                    flag = 0 ;
                    break;
                }
            }
            if(flag == 1) result.add(e);
        }
        System.out.println(result +" find BY attribute end");
        System.out.println(names +" names find BY attribute end");
        System.out.println(entities +" find BY attribute end");
        return result;
    }

    public List<Entity> findByValue(List<Entity> entities, Map<String, String> valMap) {
        List <Entity> result = new ArrayList<>();
        System.out.println(result +" find BY value ");
        System.out.println(valMap +" names find BY value ");
        System.out.println(entities +" find BY value ");
        for (Entity e: entities) {
            int flag = 0;
            for(Map.Entry<String,String> pair : valMap.entrySet()){
                if(e.getAttributes().get(pair.getKey()).equals(pair.getValue())){
                    flag = 1;
                }else {
                    flag = 0;
                    break;
                }
            }
            if(flag == 1) result.add(e);
        }
        System.out.println(result +" find BY value end");
        System.out.println(valMap +" names find BY value end");
        System.out.println(entities +" find BY value end");
        return result;
    }
}
