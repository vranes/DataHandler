package service;

import model.Entity;

import java.util.List;
import java.util.Map;

public interface IStorage {

    // nisam sigurna da li cemo cuvati direktno u fajlove ili ucitati u aplikaciju sve pa u objekte
    public void save (Entity entity);
    public void save (List<Entity> entities);
    public void save (String id, String name, Map<String, String> map);

    public void save (String path, Entity entity);
    public void save (String path, List<Entity> entities);
    public void save (String path, String id, String name, Map<String, String> map);

    public <T> T findById(String path, String id, String type);
    public <T> T findById(String path, String id);
    public <T> List<T> findByType(String path, String type);

    // nisam sigurna da li cemo pretrazivati fajlove ili ucitati u aplikaciju sve pa pretrazivati objekte
    public <T> T findByIdLocal(String id, String type);
    public <T> T findByIdLocal(String id);
    public <T> List<T> findByTypeLocal(String type);

}
