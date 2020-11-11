package service;

import Exceptions.FormatException;
import Exceptions.IdentifierException;
import model.Database;
import model.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that will be extended by all implementations
 * in order to support CRUD operations in the desired format.
 * It will stimultaniously work with files and the Database class
 */
public abstract class AbstractStorage {
    protected Database database = Database.getInstance();

    /**
     * This method should be implemented by a implementation component. It reads object entities from a file
     * @param path
     */
    public abstract List<Entity> read (String path) throws Exception;

    /**
     * This method should be implemented by a implementation component. It adds the passed entity to a file.
     * @param path
     * @param entity
     * @throws IdentifierException
     */
    public abstract void add (String path, Entity entity) throws Exception;

    /**
     * This method should be implemented by a implementation component. It deletes the passed entity from a file
     * @param path
     * @param entity
     * @throws IdentifierException
     */
    public abstract void delete (String path, Entity entity) throws Exception;

    /**
     * This method should be implemented by a implementation component. It updates the already existing file
     * @param entities
     * @param path
     * @throws IdentifierException
     */
    public abstract void refreshFile(List<Entity> entities, String path) throws Exception;

    /**
     * Method for loading the entire database, file by file into the application.
     * It relies on the concrete implementations of read() method
     * @param path
     * @throws Exception
     */
    public void loadDatabase(String path) throws Exception {
        int fileNum = 0;
        String filename =  "/file" + Integer.toString(fileNum);
        String filePath = path.concat(filename);
        File file = new File(filePath);
        Map<Integer, List<Entity>> files = new HashMap <>();
        if(file.exists()){
            while(file.exists()){
                files.put(fileNum,read(filePath));
                fileNum++;
                filename = "/file"+Integer.toString(fileNum);
                filePath = path.concat(filename);
                file = new File(filePath);
            }
        }
        else{
            file.setReadable(true);
            try {
                file.createNewFile();
                files.put(fileNum, read(filePath));
                fileNum++;
            } catch (IOException e) {
                throw new FormatException("Error while creating files");
            }
        }
        Database.getInstance().setFiles(files);
    }

    /**
     * Method for reading all files in the database, to be used only if the exact number of files is known
     * @param path
     */
    public List<Entity> readAll(String path) throws Exception {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < Database.getInstance().getFilesNum(); i++){
            List<Entity> fileEntities = (read(path + Integer.toString(i)));
            entities.addAll(fileEntities);
            database.getFiles().put(i, fileEntities);
        }

        return entities;
    }

    /**
     * Method for adding a new entity into the database. Recieves the parameters and creates a new Entity.
     * Calls the concrete implementation of method add
     * @param path
     * @param id
     * @param name
     * @param attributes
     * @param nestedEntities
     * @throws IdentifierException
     */
    public void add(String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities) throws Exception {
        Entity entity = new Entity(id, name);
        entity.setAttributes(attributes);
        entity.setNestedEntities(nestedEntities);
        add(path, entity);
    }

    /**
     * Method used for deletion by ID. Finds the entity by ID and then calls the concrete implementation of method delete
     * @param path
     * @param id
     * @throws IdentifierException
     */
    public void deleteID(String path, String id) throws Exception {
        Entity e = Crawler.getInstance().findById(id);
        delete(path, e);
    }

    /**
     * Method used for deleting a list of entities. Calls the concrete implementation of method delete foe each entity
     * @param path
     * @param list
     * @throws IdentifierException
     */
    public void deleteAll(String path, List<Entity> list) throws Exception {
        for (Entity e: list){
            delete(path, e);
        }
    }

    /**
     * Method for updating already existing Entities in a file
     * @param ent
     * @param path
     * @throws Exception
     */
    public void refresh(Entity ent, String path) throws Exception {
        if (OrderProvider.getInstance().isAvailableID(ent.getId())){
            throw new IdentifierException("Entity with id: " + ent.getId() + " doesn't exist");}
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

        refreshFile(entities, path);
    }

    /**
     * Method for adding a new nested entity to an already existing entity in a file.
     * @param path
     * @param toAdd
     * @param parentId
     * @param key
     * @throws Exception
     */
    public void addNested(String path, Entity toAdd, String parentId, String key) throws Exception {
        if (OrderProvider.getInstance().isAvailableID(parentId)){
            throw new IdentifierException("Entity with id: " + parentId + " doesn't exist");}

        List <Entity> entities = Database.getInstance().getFiles().get(
                OrderProvider.getInstance().locateInFile(Crawler.getInstance().findById(parentId))
        );
        Entity et = Crawler.getInstance().findById(parentId);
        Database.getInstance().getEntities().remove(et);
        entities.remove(et);
        et.getNestedEntities().put(key,toAdd);
        entities.add(et);
        Database.getInstance().getEntities().add(et);

        refreshFile(entities, path);
    }

}
