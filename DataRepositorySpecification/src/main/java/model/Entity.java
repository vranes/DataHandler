package model;

import java.util.HashMap;
import java.util.Map;

public class Entity {

    private String name;
    private String id;
    private Map<String, String> attributes;

    public Entity() {
        this.attributes = new HashMap<>();
        //TODO autoincrement id
        this.id = "";
        this.name = "";
    }

    public Entity(String id , String name) {
        this.attributes = new HashMap<>();
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void addAttribute(String key, String value){ attributes.put(key, value); }

    public void replaceAttribute(String key, String value){ attributes.replace(key, value); }

    public Map<String, String> getAttributes() { return attributes; }

    public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
