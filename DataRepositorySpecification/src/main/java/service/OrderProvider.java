package service;

import Exceptions.IdentifierException;
import model.Database;
import model.Entity;

import javax.xml.crypto.Data;
import java.util.List;

public class OrderProvider {

    private static OrderProvider instance = null;
    private Database database = Database.getInstance();

    public static OrderProvider getInstance(){
        if(instance == null)
            instance = new OrderProvider();
        return instance;
    }

    public int availableFile(){                                     // nalazi prvi slobodan fajl
        for(int i = 0; i < database.getFilesNum(); i++){
            List<Entity> fileEntities = database.getFiles().get(i);
            if (fileEntities.size() < database.getMaxEntities())
                return i;
        }
        return database.getFilesNum();
    }

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

    public boolean isAvailableID(String id) {                     // proverava da li je ID slobodan
        for (Entity e: database.getEntities()) {
            if (e.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    public String autoID(){                                            // nalazi prvi slobodan ID
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
