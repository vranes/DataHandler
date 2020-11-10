package service;

import Exceptions.FormatException;
import model.Entity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CustomMapper {

    private static CustomMapper instance = null;
    // menjanjem ovih atributa lako menjamo oblik custom formata po zelji
    private char beginObject = '(';
    private char endObject = ')';
    private char beginList = '{';
    private char endList = '}';
    private char beginMap = '[';
    private char endMap = ']';
    private char beginWord = '*';
    private char endWord = '*';
    private char delimiter = ';';
    private char assign = '-';

    public static CustomMapper getInstance(){
        if (instance == null)
            instance = new CustomMapper();
        return instance;
    }

    public List<Entity> readValueAsList(String string) throws IOException, FormatException {
        ArrayList<Entity> entities = new ArrayList<>();
        CustomReader reader = new CustomReader(string, beginWord, endWord, assign);
        while (reader.read() == beginObject) {
            Entity newEntity = new Entity();
            while (reader.peek() != endObject) {
                String name = reader.nextName();
                if (name.equals("id"))
                    newEntity.setId(reader.nextString());
                else if (name.equals("type"))
                    newEntity.setType(reader.nextString());
                else if (name.equals("attributes")){
                    reader.read(assign);
                    reader.read(beginMap);
                    while (reader.peek() != endMap){
                        String attribute = reader.nextName();
                        String value = reader.nextString();
                        newEntity.addAttribute(attribute, value);
                    }
                    reader.read(endMap);
                }
                else if (name.equals("nestedEntities")) {
                    reader.read(assign);
                    reader.read(beginMap);
                    while (reader.peek() != endMap){
                        String key = reader.nextName();
                        reader.read(assign);
                        reader.read(beginObject);
                        Entity nestedEntity = new Entity();
                        while (reader.peek() != endObject) {
                            String nestedName = reader.nextName();
                            if (nestedName.equals("id"))
                                nestedEntity.setId(reader.nextString());
                            else if (nestedName.equals("type"))
                                nestedEntity.setType(reader.nextString());
                            else if (nestedName.equals("attributes")){
                                reader.read(assign);
                                reader.read(beginMap);
                                while (reader.peek() != endMap){
                                    String attribute = reader.nextName();
                                    String value = reader.nextString();
                                    newEntity.addAttribute(attribute, value);
                                }
                                reader.read(endMap);
                            }
                            else if (nestedName.equals("nestedEntities")){
                                reader.read(assign);
                                reader.read(beginMap);
                                while (reader.peek() != endMap){
                                    reader.read();
                                }
                                reader.read(endMap);
                            }
                        }
                        reader.read(endObject);
                        newEntity.addNestedEntity(key, nestedEntity);
                    }
                    reader.read(endMap);
                }
            }
            reader.read(endObject);
            entities.add(newEntity);
        }
        return entities;
    }

    public String writeValueAsString(Entity e) throws IOException{
        String string = new String();
        StringBuilder builder = new StringBuilder();

        builder.append(beginObject);

        builder.append("\n");
        builder.append(beginWord + "type" + endWord);
        builder.append(" " + assign + " ");
        builder.append(beginWord + e.getType() + endWord);

        builder.append("\n");
        builder.append(beginWord + "id" + endWord);
        builder.append(" " + assign + " ");
        builder.append(beginWord + e.getId() + endWord);

        builder.append("\n");
        builder.append(beginWord + "attributes" + endWord);
        builder.append(" " + assign + " ");
        builder.append(beginMap);
        for (String key: e.getAttributes().keySet()){
            builder.append("\n");
            builder.append(beginWord + key + endWord);
            builder.append(" " + assign + " ");
            builder.append(beginWord + e.getAttributes().get(key) + endWord);
        }
        //builder.append("\n");
        builder.append(endMap);

        builder.append("\n");
        builder.append(beginWord + "nestedEntities" + endWord);
        builder.append(" " + assign + " ");
        builder.append(beginMap);
        System.out.println(e.getNestedEntities().keySet());
        for (String key: e.getNestedEntities().keySet()){
            builder.append("\n");
            builder.append(beginWord + key + endWord);
            builder.append(" " + assign + " ");
            builder.append(writeValueAsString(e.getNestedEntities().get(key)));
        }
        //builder.append("\n");
        builder.append(endMap);

        builder.append("\n");
        builder.append(endObject);

        return builder.toString();
    }

    public String writeValueAsString(List <Entity> entities) throws IOException{
        String string = new String();
        StringBuilder builder = new StringBuilder();
        for(Entity e: entities){
            builder.append(beginObject);

            builder.append("\n");
            builder.append(beginWord + "type" + endWord);
            builder.append(" " + assign + " ");
            builder.append(beginWord + e.getType() + endWord);

            builder.append("\n");
            builder.append(beginWord + "id" + endWord);
            builder.append(" " + assign + " ");
            builder.append(beginWord + e.getId() + endWord);

            builder.append("\n");
            builder.append(beginWord + "attributes" + endWord);
            builder.append(" " + assign + " ");
            builder.append(beginMap);
            for (String key: e.getAttributes().keySet()){
                builder.append("\n");
                builder.append(beginWord + key + endWord);
                builder.append(" " + assign + " ");
                builder.append(beginWord + e.getAttributes().get(key) + endWord);
            }
            //builder.append("\n");
            builder.append(endMap);

            builder.append("\n");
            builder.append(beginWord + "nestedEntities" + endWord);
            builder.append(" " + assign + " ");
            builder.append(beginMap);
            for (String key: e.getNestedEntities().keySet()){
                builder.append("\n");
                builder.append(beginWord + key + endWord);
                builder.append(" " + assign + " ");
                builder.append(writeValueAsString(e.getNestedEntities().get(key)));
            }
            //builder.append("\n");
            builder.append(endMap);

            builder.append("\n");
            builder.append(endObject);
        }
        return builder.toString();
    }
}
