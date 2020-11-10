package service;

import model.Database;
import model.Entity;
import java.util.List;

/**
 *  Class that protects data integrity in the database.
 *  It holds methods for finding the first available file for adding new entities,
 *  locating entities in the database, checking if an ID is already in use
 *  and provides support for auto ID
 */
public class OrderProvider {

    private static OrderProvider instance = null;
    private Database database = Database.getInstance();

    public static OrderProvider getInstance(){
        if(instance == null)
            instance = new OrderProvider();
        return instance;
    }

    /**
     *  Returns the ID of the first file that isn't filled to the maximum capacity
     */
    public int availableFile(){
        for(int i = 0; i < database.getFilesNum(); i++){
            List<Entity> fileEntities = database.getFiles().get(i);
            if (fileEntities.size() < database.getMaxEntities())
                return i;
        }
        return database.getFilesNum();
    }

    /**
     *  Returns the file ID of a file containing the passed entity
     */
    public Integer locateInFile(Entity entity){
        for(int i = 0; i < database.getFilesNum(); i++){
            List<Entity> fileEntities = database.getFiles().get(i);
            for (Entity e:fileEntities){
                if(e.getId().equals(entity.getId()))
                    return i;
            }
        }
        return null;
    }

    /**
     *  Checks if the passed entity ID is already in use
     */
    public boolean isAvailableID(String id) {
        for (Entity e: database.getEntities()) {
            if (e.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    /**
     *  Provides auto-increment ID by finding the smallest unused int number
     */
    public String autoID(){
        for(int i = 0; i <= database.getNumberOfEntities(); i++){
            int flag = 1;
            for (Entity e: database.getEntities())
                if(e.getId().equals(Integer.toString(i)))
                    flag = 0;
            if (flag == 1) return Integer.toString(i);
        }
        return Integer.toString(database.getNumberOfEntities()+1);
    }

}
