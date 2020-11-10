package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private int maxEntities;        // maksimalan broj entiteta po fajlu
    private List<Entity> entities;   // lista svih entiteta cele baze (ili trenutnog fajla ? zavisi od implementacije)
    private Map<Integer, List<Entity>> files;
    private String path;
    private static Database instance = null;

    private Database(){
        maxEntities = 10;
        entities = new ArrayList<>();
        files = new HashMap<>();
    }
    public static Database getInstance(){
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setMaxEntities(int maxEntities) { this.maxEntities = maxEntities; }

    public int getMaxEntities() { return maxEntities; }

    public int getFilesNum() { return files.size(); }

    public void addEntity(Entity e) { entities.add(e); }

    public List<Entity> getEntities() {

        return entities; }

    public void setEntities(List<Entity> entities) { this.entities = entities; }

    public int getNumberOfEntities() { return entities.size(); }

    public Map<Integer, List<Entity>> getFiles() { return files; }

    public void setFiles(Map<Integer, List<Entity>> files) { this.files = files; }

}


