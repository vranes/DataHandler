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

public class StorageYaml extends AbstractStorage{
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
    public void add(String path, Entity entity){
        List<Entity> entities  = read(path);
        entities.add(entity);
        String yamlString = "";

        for(Entity ent : entities){
           yamlString = yamlString.concat(yaml.dump(ent));
        }
        ImportExportYaml.getInstance().exportFile(path,yamlString);

    }

    @Override
    public void delete(String path, Entity entity) {
        List<Entity> entities = read(path);
        entities.remove(entity);

        ImportExportYaml.getInstance().exportFile(path,yaml.dump(entities));

    }

//
//        String yamlString = "";
//
//        for(Entity ent : entities){
//           yamlString = yamlString.concat(yaml.dump(ent));
//        }
//        try{ ImportExportYaml.getInstance().exportFile(path,yamlString);}  // ovo u odvojenu funkciju
//        catch (IOException e) { e.printStackTrace();}




}
