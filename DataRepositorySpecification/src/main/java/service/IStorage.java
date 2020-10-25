package service;

import model.Entity;

import java.util.List;
import java.util.Map;

public interface IStorage {

    // Ideja je da ovde budu metode za CRUD operacije i filtriranje
    // Koje ce da pozivaju metode Database-a (u njemu je lista svih entiteta i samo najosnovnije metode)

    // Nalupala sam metode za pocetak, izmeni/dodaj sta god ali da bude univerzalno korisceno u aplikaciji
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
