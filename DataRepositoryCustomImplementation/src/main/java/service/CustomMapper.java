package service;

import Exceptions.FormatException;
import model.Entity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CustomMapper {

    private static CustomMapper instance = null;
    private char beginObject = '(';
    private char endObject = ')';
    private char beginList = '[';
    private char endList = ']';
    private char beginMap = '{';
    private char endMap = '}';
    private char beginWord = '\'';
    private char endWord = '\'';
    private char delimiter = ';';
    private char assign = '-';

    public static CustomMapper getInstance(){
        if (instance == null)
            instance = new CustomMapper();
        return instance;
    }

    public List<Entity> readValueAsList(String string) throws IOException, FormatException {
        ArrayList<Entity> entities = new ArrayList<>();
        CustomReader reader = new CustomReader(-1, string);
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
                        String type = reader.nextName();
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

                        }
                        reader.read(endObject);
                        newEntity.addNestedEntity(type, nestedEntity);
                    }
                    reader.read(endMap);
                }
            }
            reader.read(endObject);
            entities.add(newEntity);
        }
        return null;
    }

    public String writeValueAsString(List <Entity> entities) throws IOException{
        return null;
    }


          /*
            while (jr.peek() == beginObject) {
                Entity newEntity = new Entity();
                while (jr.peek() != endObject) {
                    String name = jr.nextName();
                    if (name.equals("id"))
                        newEntity.setId(jr.nextString());
                    else if (name.equals("type"))
                        newEntity.setType(jr.nextString());
                    else if (name.equals("attributes")){
                    // TODO procitati listu
                    }
                    else if (name.equals("nestedEntities")) {
                        Entity nestedEntity = new Entity();
                        while (jr.peek() != endObject) {
                            String nestedName = jr.nextName();
                            if (nestedName.equals("id"))
                                nestedEntity.setId(jr.nextString());
                            else if (nestedName.equals("type"))
                                nestedEntity.setType(jr.nextString());
                            else if (nestedName.equals("attributes"){
                                // TODO procitati listu
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

      */
}
