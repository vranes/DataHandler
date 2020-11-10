package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Class that stores database entities, keeps track record of files and max number of entities per file
 */
public class Database {

    /**
     *  maximum number of entities per file
     */
    private int maxEntities;
    /**
     *  list of all database entities
     */
    private List<Entity> entities;
    /**
     * map of all files with their belonging entities
     */
    private Map<Integer, List<Entity>> files;
    /**
     *  path to the database root directory
     */
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

    /**
     * adds a new entity to the database
     */
    public void addEntity(Entity e) { entities.add(e); }

    public List<Entity> getEntities() { return entities; }

    public void setEntities(List<Entity> entities) { this.entities = entities; }

    public int getNumberOfEntities() { return entities.size(); }

    public Map<Integer, List<Entity>> getFiles() { return files; }

    public void setFiles(Map<Integer, List<Entity>> files) { this.files = files; }

}


