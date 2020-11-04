package service;

import model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import importexport.ImportExportYaml;
public class tester {

  public  static void tester(String[] args){

    Entity ent = new Entity();
    ent.setId("1");
    ent.setType("hello");
    Map <String,String> map = new HashMap <>();
    map.put("Atr1","22");
    map.put("Atr2","33");

    Entity ent1 = new Entity();
    ent1.setId("2");
    ent1.setType("hiiiiii");
    ent.setAttributes(map);
    Map<String,Entity> map2 = new HashMap<>();
    Entity ent2 = new Entity();
    ent2.setId("3");
    ent2.setType("nested");
    map2.put("1",ent2);

    ent.setNestedEntities(map2);
    List<Entity> enti = new ArrayList<>();
    enti.add(ent);
    enti.add(ent1);
    enti.add(ent2);
//      ImportExportYaml.getInstance().exportFile("/Files/file",enti);
//     List<Entity> e = ImportExportYaml.getInstance().importEntities("/Files/file");
//        for(Entity i : e){
//          System.out.println(i);
//        }
//      StorageYaml.getInstance().add("/Files/file",ent2);
//      List<Entity> e = StorageYaml.getInstance().read("/Files/file0");
//    for(Entity i : e){
//      System.out.println(i);
//    }
    }
}
