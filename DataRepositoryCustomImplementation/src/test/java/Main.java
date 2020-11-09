import Exceptions.FormatException;
import model.Entity;
import service.CustomMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        Entity ent = new Entity();
        ent.setId("1");
        ent.setType("student");
        Map<String, String> map = new HashMap<>();
        map.put("grupa", "101");
        map.put("godina", "prva");
        ent.setAttributes(map);

        Map<String, Entity> map2 = new HashMap<>();
        Entity ent2 = new Entity();
        ent2.setId("2");
        ent2.setType("predmet");
        map2.put("1", ent2);
        ent.setNestedEntities(map2);

        Entity ent3 = new Entity();
        ent3.setId("5");
        ent3.setType("student");

        List<Entity> list = new ArrayList<>();
        list.add(ent);
        list.add(ent3);

        try {
            String s = CustomMapper.getInstance().writeValueAsString(list);
            System.out.println(s);
            List entities = new ArrayList<>();
            entities.addAll(CustomMapper.getInstance().readValueAsList(s));
            System.out.println(entities);
            String s2 = CustomMapper.getInstance().writeValueAsString(entities);
            System.out.println(s2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }
}
/*
(
*type* - *student*
*id* - *1*
*attributes* - [
*grupa* - *101*
*godina* - *prva*]
*nestedEntities* - [
*1* - (
*type* - *predmet*
*id* - *2*
*attributes* - []
*nestedEntities* - []
)]
)(
*type* - *student*
*id* - *5*
*attributes* - []
*nestedEntities* - []
)

 */