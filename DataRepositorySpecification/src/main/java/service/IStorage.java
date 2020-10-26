package service;

import model.Entity;

import java.util.List;
import java.util.Map;

public interface IStorage {

    // Ideja je da ovde budu metode za CRUD operacije i filtriranje
    // Koje ce da barataju Database-om (u njemu je lista svih entiteta i samo najosnovnije metode)

    // Nalupala sam metode za pocetak, izmeni/dodaj sta god ali da bude univerzalno korisceno u aplikaciji
    public void add (String path, Entity entity) throws Exception;  // TODO generisanje vs zadavanje ID-ja?
    public void add (String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities) throws Exception;

    // probaj da koristis ove metode a ne ove ispod, pretrazujemo listu entiteta iz Database-a
    public Entity findByIdLocal(String id);
    public List<Entity> findByTypeLocal(String type);

    /*
    public <T> T findById(String path, String id);
    public <T> List<T> findByType(String path, String type);
    */
}
