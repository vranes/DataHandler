package service;

import importexport.ImportExportJson;
import model.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageJson implements IStorage {

    @Override
    public void add (String path, Entity entity) throws Exception {
        Database.getInstance().addEntity(entity);
        int fileNo = Database.getInstance().getNumberOfEntities() / Database.getInstance().getMaxEntities();

        path += Integer.toString(fileNo);

        String fileString = ImportExportJson.getInstance().importFileString(path);
        String entityString = ObjectConverterJson.getInstance().objectToFormat(entity);
        fileString = fileString + entityString;
    }


    @Override
    public void add (String path, String id, String name, Map<String, String> map) throws Exception {

    }

    @Override
    public <T> T findById(String path, String id, String type) {
        return null;
    }

    @Override
    public <T> T findById(String path, String id) {
        return null;
    }

    @Override
    public <T> List<T> findByType(String path, String type) {
        return null;
    }

    @Override
    public <T> T findByIdLocal(String id, String type) {
        return null;
    }

    @Override
    public <T> T findByIdLocal(String id) {
        return null;
    }

    @Override
    public <T> List<T> findByTypeLocal(String type) {
        return null;
    }
}
