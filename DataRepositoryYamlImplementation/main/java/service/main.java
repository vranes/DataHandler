package service;

import model.Entity;

import java.util.HashMap;
import java.util.Map;

public class main {

  public  static void main(String[] args) throws Exception {

    Entity ent = new Entity();
    ent.setId("1");
    ent.setType("hello");
      Map<String,String> map = new HashMap<String,String>();
      map.put("Atr1","22");
      map.put("Atr2","33");
      Entity ent1 = new Entity();
      ent1.setId("2");
      ent1.setType("hiiiiii");
      Map<String,String> map2 = new HashMap<String,String>();
      map2.put("Atr1","444");
      map2.put("Atr2","555");
    ent.setAttributes(map);
      Map<String,Entity> map4 = new HashMap<String,Entity>();
      map4.put("1",ent1);
    ent.setNestedEntities(map4);
    System.out.println(ObjectConverterYaml.getInstance().formatToObject(ObjectConverterYaml.getInstance().objectToFormat(ent),Entity.class));

    }
}
