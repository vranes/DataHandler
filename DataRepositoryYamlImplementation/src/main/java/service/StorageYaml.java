package service;
import Exceptions.IdentifierException;
import importexport.IImportExport;
import importexport.ImportExportYaml;
import model.Database;
import model.Entity;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageYaml extends AbstractStorage{

    private static StorageYaml instance = null;

    public static AbstractStorage getInstance(){
        if (instance == null)
            instance = new StorageYaml();
        return instance;
    }

    @Override
    public List<Entity> read(String path) throws Exception {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportYaml.getInstance();
        entities = importExport.importEntities(path);
        for(Entity e : entities)
            Database.getInstance().addEntity(e);
        return entities;
    }

    @Override
    public void add(String path, Entity entity) throws Exception {
        if (!OrderProvider.getInstance().isAvailableID(entity.getId())){
            throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }
        int fileNo = OrderProvider.getInstance().availableFile();
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        List<Entity> entities = Database.getInstance().getFiles().get(fileNo);
        entities.add(entity);
        String filePath = path + fileNo;
        IImportExport importExport = ImportExportYaml.getInstance();
        importExport.exportEntities(filePath, entities);
        database.addEntity(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws Exception {
        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        List<Entity> entities = database.getFiles().get(fileNo);
        entities.remove(entity);
        String filePath = path + fileNo;
        ImportExportYaml.getInstance().exportEntities(filePath, entities);
        database.getEntities().remove(entity);
    }

    @Override
    public void refreshFile(List <Entity> entities, String path) throws Exception {
        ImportExportYaml.getInstance().exportEntities(path, entities);
    }

}
