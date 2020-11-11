package service;

import Exceptions.IdentifierException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Database;
import model.Entity;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageJson extends AbstractStorage {

    private static StorageJson instance = null;
    private Database database = Database.getInstance();

    public static AbstractStorage getInstance(){
        if (instance == null)
            instance = new StorageJson();
        return instance;
    }

    @Override
    public List<Entity> read(String path) throws Exception {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportJson.getInstance();
        entities = importExport.importEntities(path);
        for(Entity e : entities)
            Database.getInstance().addEntity(e);
        return entities;
    }

    @Override
    public void add (String path, Entity entity) throws Exception {
        if (!OrderProvider.getInstance().isAvailableID(entity.getId())){
            throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }
        int fileNo = OrderProvider.getInstance().availableFile();
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        List<Entity> entities = Database.getInstance().getFiles().get(fileNo);
        entities.add(entity);
        String filePath = path + fileNo;
        ImportExportJson.getInstance().exportEntities(filePath, entities);
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
        ImportExportJson.getInstance().exportEntities(filePath, entities);
        database.getEntities().remove(entity);
    }

    @Override
    public void refreshFile(List <Entity> entities, String path) throws Exception {
        ImportExportJson.getInstance().exportEntities(path, entities);
    }


}
