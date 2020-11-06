package service;
import importexport.IImportExport;
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
    private static StorageYaml instance = null;

    public static StorageYaml getInstance(){           // TODO da li da bude instanca interfejsa
        if (instance == null)
            instance = new StorageYaml();
        return instance;
    }

    @Override
    public List<Entity> read(String path) {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportYaml.getInstance();
        entities = importExport.importEntities(path);
        return entities;
    }

    @Override
    public void add(String path, Entity entity){
        IImportExport importExport = ImportExportYaml.getInstance();
        List<Entity> entities = null;

        int fileNo = Database.getInstance().getNumberOfEntities() / Database.getInstance().getMaxEntities();
        path += Integer.toString(fileNo);
        entities = importExport.importEntities(path);

        for(Entity e : entities){
            if(e.equals(entity)) return;
        }
        entities.add(entity);
        Database.getInstance().addEntity(entity);



        importExport.exportEntities(path, entities);

    }

    @Override
    public void delete(String path, Entity entity) {
        List<Entity> entities;
        Entity toRemove = null;
        entities = ImportExportYaml.getInstance().importEntities(path);

        Database.getInstance().getEntities().remove(entity);

        for(Entity i : entities){
            if(i.equals(entity)) {
                toRemove = i;
                break;
            }
        }
        entities.remove(toRemove);
        ImportExportYaml.getInstance().exportEntities(path,entities);
    }

}
