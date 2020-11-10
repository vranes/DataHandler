package service;
import model.Database;
import model.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Class with methods for filtering database entities by various criteria
 */
public class Crawler {

    protected Database database = Database.getInstance();
    private static Crawler instance = null;

    public static Crawler getInstance(){
        if(instance == null)
            instance = new Crawler();
        return instance;
    }

    /**
     *  Finds and returns entity with the passed id
     */
    public Entity findById(String id) {
        for (Entity e: database.getEntities()){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    /**
     *  Filters database entities by type
     */
    public List<Entity> findByType(String type) {
        List <Entity> entityList = new ArrayList<>();
        for (Entity e: database.getEntities()) {
            if (e.getType().equals(type))
                entityList.add(e);
        }
        return entityList;
    }

    /**
     *  Filters passed entities by posession of the attribute name. Serves for more complex filter queries.
     */
    public List<Entity> findByAttribute(List<Entity> entities, List<String> names) {
        List <Entity> result = new ArrayList<>();
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
        return result;
    }

    /**
     *  Filters passed entities by a map of attribute name-value pairs. Serves for more complex filter queries.
     */
    public List<Entity> findByValue(List<Entity> entities, Map<String, String> valMap) {
        List <Entity> result = new ArrayList<>();
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
        return result;
    }
}
