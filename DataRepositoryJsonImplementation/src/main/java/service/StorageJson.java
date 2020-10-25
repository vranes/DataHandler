package service;

import model.Entity;

import java.util.List;
import java.util.Map;

public class StorageJson implements IStorage {
    @Override
    public void save(Entity entity) {

    }

    @Override
    public void save(List<Entity> entities) {

    }

    @Override
    public void save(String id, String name, Map<String, String> map) {

    }

    @Override
    public void save(String path, Entity entity) {

    }

    @Override
    public void save(String path, List<Entity> entities) {

    }

    @Override
    public void save(String path, String id, String name, Map<String, String> map) {

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
