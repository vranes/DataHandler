package service;

import model.Entity;

import java.util.List;

public abstract class Database {

    protected int maxEntities;        // maksimalan broj entiteta po fajlu
    protected int filesNum;           // broj fajlova trenutne baze
    protected List<Entity> entities;   // lista svih entiteta cele baze (ili trenutnog fajla ? zavisi od implementacije)

    // ova metoda treba da pri pokretanju ucita sve iz cele odabrane baze, fajl po fajl
    // svaki entitet da pretvori u <Entity> objekat i da ih sacuva u listi entiteta
    public abstract void load(String filePath);

    public void setMaxEntities(int maxEntities) { this.maxEntities = maxEntities; }

    public void setFilesNum(int filesNum) { this.filesNum = filesNum; }

    public int getMaxEntities() { return maxEntities; }

    public int getFilesNum() { return filesNum; }

    public void addEntity(Entity e) { entities.add(e); }

    public List<Entity> getEntities() { return entities; }

    public void setEntities(List<Entity> entities) { this.entities = entities; }

}


