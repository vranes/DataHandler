package service;
import importexport.ImportExportYaml;
import model.Entity;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageYaml implements IStorage{
Yaml yaml = new Yaml();

    @Override
    public List<Entity> read(String path) {

        Constructor constructor = new Constructor();
        constructor.addTypeDescription(new TypeDescription(Entity.class));
        List<Entity> entities = new ArrayList<Entity>();

        Yaml yaml = new Yaml(constructor);

        try (InputStream in = new FileInputStream(new File(path))) {

            Iterable<Object> allYamlObjects = yaml.loadAll(in);

            for (Iterator<?> yamlObjectIterator = allYamlObjects.iterator(); yamlObjectIterator.hasNext(); ) {
                List<?> yamlObjects = (List<?>) yamlObjectIterator.next();
                for (Object yamlObject : yamlObjects) {
                    if (yamlObject instanceof Entity) {
                        entities.add((Entity) yamlObject);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }
    @Override
    public List<Entity> readAll(String path) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        int fileNo = Database.getInstance().getNumberOfEntities() / Database.getInstance().getMaxEntities();
        String pathcopy = path;
        pathcopy += Integer.toString(fileNo);
        List<Entity> ent = null;

        while((ent = read(pathcopy)) != null){
            for(Entity e : ent)
                entities.add(e);
            pathcopy =  path;
            pathcopy += Integer.toString(fileNo+1);
        }
            return entities;
    }

    @Override
    public void add(String path, Entity entity){
        List<Entity> entities  = read(path);
        entities.add(entity);
        String yamlString = "";

        for(Entity ent : entities){
           yamlString = yamlString.concat(yaml.dump(ent));
        }
       try{ ImportExportYaml.getInstance().exportFile(path,yamlString);}
       catch (IOException e) { e.printStackTrace();}

    }

    @Override
    public void add(String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities) {

    }

    @Override
    public void delete(String path, Entity entity) {
        List<Entity> entities = read(path);
        entities.remove(entity);

        try {
            ImportExportYaml.getInstance().exportFile(path,yaml.dump(entities));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(String path, String id) {
        List <Entity> entities = read(path);
        entities.remove(entity);



        ImportExportYaml.getInstance().exportFile(path, yaml.dump(entities));


    }

    @Override
    public Entity findById(String id) {
        List<Entity> entities = read(path);
        Entity toDelete = null;

        for(Entity ent : entities){
            if(ent.getId().equals(id))
            {
                return ent;
                break;
            }
        }
        System.out.println("no byid");
        return null;
//
//        String yamlString = "";
//
//        for(Entity ent : entities){
//           yamlString = yamlString.concat(yaml.dump(ent));
//        }
//        try{ ImportExportYaml.getInstance().exportFile(path,yamlString);}  // ovo u odvojenu funkciju
//        catch (IOException e) { e.printStackTrace();}
    }

    @Override
    public List<Entity> findByType(String type) {
        List<Entity> entities = read(path);
        List <Entity> findTypes = null;
        Entity toDelete = null;

        for(Entity ent : entities){
            if(ent.getType().equals(type))
            {
                findTypes.add(ent);
            }
        }
        return entities;
//
//        String yamlString = "";
//
//        for(Entity ent : entities){
//           yamlString = yamlString.concat(yaml.dump(ent));
//        }
//        try{ ImportExportYaml.getInstance().exportFile(path,yamlString);}  // ovo u odvojenu funkciju
//        catch (IOException e) { e.printStackTrace();}
    }

    @Override
    public List<Entity> findByAttribute(String name) {
        return null;
    }

    @Override
    public List<Entity> findByValue(String operator, String name, String value) {
        return null;
    }
}
