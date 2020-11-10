package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  Class that represents entitites in the database
 */
public class Entity {

    /**
     *  entity type
     */
    private String type;
    /**
     *  entity identificator
     */
    private String id;
    /**
     *  map of attributes
     */
    private Map<String, String> attributes;
    /**
     *  map of nested entities
     */
    private Map<String, Entity> nestedEntities;

    /**
     *  empty constructor - necessery for object-mapping libraries
     */
    public Entity() {
        this.attributes = new HashMap<>();
        this.nestedEntities = new HashMap<>();
        //TODO autoincrement id
        this.id = "";
        this.type = "";
    }

    public Entity(String id , String name) {
        this.attributes = new HashMap<>();
        this.nestedEntities = new HashMap<>();
        this.id = id;
        this.type = name;
    }

    public String getId() { return id; }
    /**
     *  returns the intiger value of entity id
     */
    public Integer abcd() { return Integer.valueOf(getId()); }

    public void setId(String id) { this.id = id; }

    public void addAttribute(String key, String value) { attributes.put(key, value); }
    /**
     *  replaces attribute with a new value
     */
    public void replaceAttribute(String key, String value) { attributes.replace(key, value); }

    public Map<String, String> getAttributes() { return attributes; }

    public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }

    public String getType() { return type; }

    public void setType(String name) { this.type = name; }

    public Map<String, Entity> getNestedEntities() { return nestedEntities; }

    public void setNestedEntities(Map<String, Entity> nestedEntities) { this.nestedEntities = nestedEntities; }
    /**
     *  adds a new nested entity into the map
     */
    public void addNestedEntity (String key, Entity value) { nestedEntities.put(key, value); }

    @Override
    public String toString() {
        return "Entity{" +
                "type = " + type +
                ", id = " + id +
                ", attributes = " + attributes +
                ", nestedEntities = " + nestedEntities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(type, entity.type) &&
                Objects.equals(id, entity.id);
    }

}
