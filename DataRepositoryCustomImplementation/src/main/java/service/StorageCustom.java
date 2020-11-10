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
        if (!database.getFiles().containsKey(fileNo))
            database.getFiles().put(fileNo, new ArrayList<Entity>());
        List<Entity> entities = Database.getInstance().getFiles().get(fileNo);
        entities.add(entity);
        path += Integer.toString(fileNo);
        IImportExport importExport = ImportExportCustom.getInstance();
        importExport.exportEntities(path, entities);
        database.addEntity(entity);
    }

    @Override
    public void delete(String path, Entity entity) throws IdentifierException {
        Integer fileNo = OrderProvider.getInstance().locateInFile(entity);
        if(fileNo == null)
            throw new IdentifierException("The entity for deletion doesn't exist");
        database.getFiles().get(fileNo).remove(entity);
        IImportExport importExport = ImportExportCustom.getInstance();
        importExport.exportEntities(path, database.getFiles().get(fileNo));
        database.getEntities().remove(entity);
    }

    @Override
    public void refresh(Entity ent, String path) throws IdentifierException {
        if (OrderProvider.getInstance().isAvailableID(ent.getId())){
            throw new IdentifierException("An entity with id: " + ent.getId() + " doesnt exists");}
        Entity toEdit = Crawler.getInstance().findById(ent.getId());

        List <Entity> entities = Database.getInstance().getFiles().get(
                OrderProvider.getInstance().locateInFile(toEdit)
        );
        Database.getInstance().getEntities().remove(toEdit);
        entities.remove(toEdit);
        if(!ent.getType().equals(""))
            toEdit.setType(ent.getType());
        if(!ent.getNestedEntities().isEmpty())
            toEdit.setNestedEntities(ent.getNestedEntities());
        if(!ent.getAttributes().isEmpty())
            toEdit.setAttributes(ent.getAttributes());
        entities.add(toEdit);
        Database.getInstance().getEntities().add(toEdit);

        ImportExportCustom.getInstance().exportEntities(path, entities);
    }

    @Override
    public void addnested(String path, Entity toAdd, String parentId, String key) throws IdentifierException {
        if (OrderProvider.getInstance().isAvailableID(parentId)){
            throw new IdentifierException("An entity with id: " + parentId + " doesnt exists");
        }
        List <Entity> entities = Database.getInstance().getFiles().get(
                OrderProvider.getInstance().locateInFile(Crawler.getInstance().findById(parentId))
        );
        Entity et = Crawler.getInstance().findById(parentId);
        Database.getInstance().getEntities().remove(et);
        entities.remove(et);
        et.getNestedEntities().put(key,toAdd);
        entities.add(et);
        Database.getInstance().getEntities().add(et);

        ImportExportCustom.getInstance().exportEntities(path, entities);
    }

}
