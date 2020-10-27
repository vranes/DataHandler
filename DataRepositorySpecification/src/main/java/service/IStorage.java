package service;

import model.Entity;
import java.util.List;
import java.util.Map;

public interface IStorage {

    // Ideja je da ovde budu metode za CRUD operacije i filtriranje
    // Koje ce da barataju Database-om (u njemu je lista svih entiteta i samo najosnovnije metode)

    public List<Entity> read (String path);
    public List<Entity> readAll (String path);
    // Nalupala sam metode za pocetak, izmeni/dodaj sta god ali da bude univerzalno korisceno u aplikaciji
    public void add (String path, Entity entity);  // TODO generisanje vs zadavanje ID-ja?
    public void add (String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities);

    public void delete(String path, Entity entity);
    public void delete(String path, String id);

    // probaj da koristis ove metode a ne ove ispod, pretrazujemo listu entiteta iz Database-a
    public Entity findById(String id);
    public List<Entity> findByType(String type);
    public List<Entity> findByAttribute(String name);
    public List<Entity> findByValue(String operator, String name, String value);

}
