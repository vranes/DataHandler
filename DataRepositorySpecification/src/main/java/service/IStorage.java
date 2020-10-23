package service;

import model.Entity;

import java.util.List;
import java.util.Map;

public interface IStorage {

    public void save (Entity entity);
    public void save (List<Entity> entities);
    public void save (String id, String name, Map<String, String> map);

}
