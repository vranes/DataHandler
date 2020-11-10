package service;

import Exceptions.IdentifierException;
import importexport.IImportExport;
import importexport.ImportExportCustom;
import model.Database;
import model.Entity;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StorageCustom extends AbstractStorage{


    private static StorageCustom instance = null;
    private Database database = Database.getInstance();

    public static AbstractStorage getInstance(){
        if (instance == null)
            instance = new StorageCustom();
        return instance;
    }

    @Override
    public List<Entity> read(String path) {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportCustom.getInstance();
        entities = importExport.importEntities(path);
        for(Entity e : entities)
            Database.getInstance().addEntity(e);
        return entities;
    }

    @Override
    public void add (String path, Entity entity) throws IdentifierException {
        if (!OrderProvider.getInstance().isAvailableID(entity.getId())){
            throw new IdentifierException("An entity with id: " + entity.getId() + " already exists");
        }
        int fileNo = OrderProvider.getInstance().availableFile();
        path += Integer.toString(fileNo);
        IImportExport importExport = ImportExportCustom.getInstance();
        List<Entity> entities = null;
        entities = Database.getInstance().getFiles().get(fileNo);
        entities.add(entity);
        importExport.exportEntities(path, entities);

        database.addEntity(entity);
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
    }

    @Override
    public void delete(String path, Entity entity) throws IdentifierException {

        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        String filename =  Integer.toString(fileNo);
        String filePath = path.concat(filename);
        String absolutePath =  filePath;
        File file = new File(absolutePath);
        CustomMapper objectMapper = new CustomMapper();
        database.getFiles().get(fileNo).remove(entity);

        IImportExport importExport = ImportExportCustom.getInstance();
        importExport.exportEntities(path, database.getFiles().get(fileNo));

        database.getEntities().remove(entity);
    }
    @Override
    public void refresh(String path, List <Entity> entities) throws IdentifierException {
        ImportExportCustom.getInstance().exportEntities(path, entities);
    }
}
