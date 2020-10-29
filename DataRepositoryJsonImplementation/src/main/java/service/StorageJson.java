package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import importexport.IImportExport;
import importexport.ImportExportJson;
import model.Entity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StorageJson implements IStorage {

    @Override
    public List<Entity> read(String path) {
        List<Entity> entities = new ArrayList<>();
        IImportExport importExport = ImportExportJson.getInstance();
        try {
            String fileString = importExport.importFile(path);
            JsonReader jr = new JsonReader(new StringReader(fileString));
            while (jr.peek() == JsonToken.BEGIN_OBJECT) {
                Entity newEntity = new Entity();
                while (jr.peek() != JsonToken.END_OBJECT) {
                    String name = jr.nextName();
                    if (name.equals("id"))
                        newEntity.setId(jr.nextString());
                    else if (name.equals("type"))
                        newEntity.setType(jr.nextString());
                    else if (jr.peek() == JsonToken.BEGIN_OBJECT) {
                        Entity nestedEntity = new Entity();
                        while (jr.peek() != JsonToken.END_OBJECT) {
                            String nestedName = jr.nextName();
                            if (nestedName.equals("id"))
                                nestedEntity.setId(jr.nextString());
                            else if (nestedName.equals("type"))
                                nestedEntity.setType(jr.nextString());
                            else {
                                nestedEntity.addAttribute(jr.nextName(), jr.nextString());
                            }
                        }
                        newEntity.addNestedEntity(name, nestedEntity);
                    } else {
                        newEntity.addAttribute(name, jr.nextString());
                    }
                }
                entities.add(newEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public List<Entity> readAll(String path) {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < Database.getInstance().getFilesNum(); i++){
            entities.addAll(read(path + Integer.toString(i)));
        }
        return entities;
    }

    @Override
    public void add (String path, Entity entity) {
        Database.getInstance().addEntity(entity);
        int fileNo = Database.getInstance().getNumberOfEntities() / Database.getInstance().getMaxEntities();

        path += Integer.toString(fileNo);
        //String fileString = ImportExportJson.getInstance().importFileString(path);
        //String entityString = ObjectConverterJson.getInstance().objectToFormat(entity);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> entities = null;
        try {
            entities = objectMapper.readValue(new File(path), new TypeReference<List<Entity>>(){});
            entities.add(entity);         //TODO ili string
            objectMapper.writeValue(new File(path), entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(fileString);

        JsonNode node = ObjectConverterJson.getInstance().objectToNode();
        ((ObjectNode) tree).put("Entity", node);    //TODO ???? sta je string
*/
    }

    @Override
    public void add (String path, String id, String name, Map<String, String> attributes, Map<String, Entity> nestedEntities){
        Entity entity = new Entity(id, name);
        entity.setAttributes(attributes);
        entity.setNestedEntities(nestedEntities);
        add(path, entity);
    }

    @Override
    public void delete(String path, Entity entity) {    //TODO dodati da obidje celu bazu fajl po fajl
        Database.getInstance().getEntities().remove(entity);
        File jsonFile = new File(path).getAbsoluteFile();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array = null;
        try {
            array = (ArrayNode) objectMapper.readTree(jsonFile);
            String id = entity.getId();
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("id").equals(id)) {
                    array.remove(i);
                    break;
                }
            }
            String formattedJson = objectMapper.writeValueAsString(array);
            ImportExportJson.getInstance().exportFile(path, formattedJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String path, String id) {
        for (Entity e: Database.getInstance().getEntities()){
            if (e.getId().equals(id)){
                delete(path, e);
                break;
            }
        }
    }


    @Override
    public Entity findById(String id) {
        for (Entity e: Database.getInstance().getEntities()){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    @Override
    public List<Entity> findByType(String type) {
        List <Entity> entityList = new ArrayList<>();
        for (Entity e: Database.getInstance().getEntities()) {
            if (e.getType().equals(type))
                entityList.add(e);
        }
        return entityList;
    }
    /*
    (na primer: vrati sve entitete sa nazivom student koji sadrže ključ
    studijskiProgram i ime im počinje na M),
    */
    public List<Entity> findByAttribute(String name) {
        List <Entity> result = new ArrayList<>();
        for (Entity e: Database.getInstance().getEntities()) {
            for (String attribute: e.getAttributes().keySet()){
                if (attribute.equals(name)) {
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }

    public List<Entity> findByValue(String operator, String name, String value) {
        List <Entity> result = new ArrayList<>();
        for (Entity e: Database.getInstance().getEntities()) {
            for (String attribute: e.getAttributes().keySet()){
                if (attribute.equals(name)) {
                    //TODO proveriti operator
                    result.add(e);
                    break;
                }
            }
        }
        return result;
    }

}
