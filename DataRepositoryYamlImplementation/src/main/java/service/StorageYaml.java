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
        for(Entity e : entities)
            Database.getInstance().addEntity(e);// ove dve linije dodate

        return entities;
    }

    @Override
    public void add(String path, Entity entity) throws IdentifierException {
        if (!OrderProvider.getInstance().isAvailableID(entity.getId())){
            throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }

        int fileNo = OrderProvider.getInstance().availableFile();
        path += Integer.toString(fileNo);

        IImportExport importExport = ImportExportYaml.getInstance();
        List<Entity> entities = null;

        entities = importExport.importEntities(path);
        entities.add(entity);
        importExport.exportEntities(path, entities);

        database.addEntity(entity);
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        database.getFiles().get(fileNo).add(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws IdentifierException {
        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        String filename =  Integer.toString(fileNo);
        String filePath = path.concat(filename);
        String absolutePath = filePath;
        File file = new File(absolutePath);
        List<Entity> entities;
        Entity toRemove = null;
        entities = ImportExportYaml.getInstance().importEntities(filePath);
        Database.getInstance().getEntities().remove(entity);

        for(Entity i : entities){
            if(i.getId().equals(entity.getId())) {
                toRemove = i;
                break;
            }
        }
        entities.remove(toRemove);
        ImportExportYaml.getInstance().exportEntities(filePath,entities);
    }

    @Override
    public void refresh(String path, List <Entity> entities) throws IdentifierException {
        ImportExportYaml.getInstance().exportEntities(path, entities);
    }

}
