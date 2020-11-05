package service;

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

    public String availableFile(){                                     // nalazi prvi slobodan fajl
        for(int i = 0; i < database.getFilesNum(); i++){
            List<Entity> list = database.getFiles().get(i);
            if (list.size() < database.getMaxEntities())
                return Integer.toString(i);
        }
        return Integer.toString(database.getFilesNum());
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
