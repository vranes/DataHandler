package service;

import Exceptions.IdentifierException;
import importexport.IImportExport;
import importexport.ImportExportCustom;
import model.Database;
import model.Entity;
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
    public List<Entity> read(String path) throws Exception {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportCustom.getInstance();
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
        ImportExportCustom.getInstance().exportEntities(filePath, entities);
        database.addEntity(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws Exception {
        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        database.getFiles().get(fileNo).remove(entity);
        String filePath = path + fileNo;
        IImportExport importExport = ImportExportCustom.getInstance();
        importExport.exportEntities(filePath, database.getFiles().get(fileNo));
        database.getEntities().remove(entity);
    }

    @Override
    public void refreshFile(List <Entity> entities, String path) throws Exception {
        ImportExportCustom.getInstance().exportEntities(path, entities);
    }


}
