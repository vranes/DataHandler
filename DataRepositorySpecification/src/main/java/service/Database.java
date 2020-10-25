package service;

import model.Entity;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private int maxEntities;        // maksimalan broj entiteta po fajlu
    private int filesNum;           // broj fajlova trenutne baze
    private List<Entity> entities;   // lista svih entiteta cele baze (ili trenutnog fajla ? zavisi od implementacije)

    private static Database instance = null;
    private Database(){
        filesNum = 0;
        entities = new ArrayList<>();
    }
    public static Database getInstance(){
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public void setMaxEntities(int maxEntities) { this.maxEntities = maxEntities; }

    public void setFilesNum(int filesNum) { this.filesNum = filesNum; }

    public int getMaxEntities() { return maxEntities; }

    public int getFilesNum() { return filesNum; }

    public void addEntity(Entity e) { entities.add(e); }

    public List<Entity> getEntities() { return entities; }

    public void setEntities(List<Entity> entities) { this.entities = entities; }

}


