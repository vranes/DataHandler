package start;
import model.Entity;
import service.StorageJson;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Entity ent = new Entity();
		StorageJson sj = new StorageJson();
		ent.setId("1");
		ent.setType("hello");
		Map <String,String> map = new HashMap <String,String>();
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

		sj.add("Users/mac/Desktop",ent);
//		 AppCore appCore = new AppCore();
//	     MainFrame mainFrame = MainFrame.getInstance();
//	     mainFrame.setAppCore(appCore);

		//mainFrame.getAppCore().readDataFromTable("NASTAVNI_PREDMETI"); //ovo treba pozvati pri odaberu tabele za prikaz}
	}
}
