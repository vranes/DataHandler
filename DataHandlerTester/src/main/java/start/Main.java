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
		Map <String,String> map = new HashMap <>();
		map.put("Atr1","22");
		map.put("Atr2","33");
		Entity ent1 = new Entity();
		ent1.setId("2");
		ent1.setType("hiiiiii");
		ent.setAttributes(map);
		Map<String,Entity> map2 = new HashMap<>();
		Entity ent2 = new Entity();
		ent2.setId("2");
		ent2.setType("nested");
		map2.put("1",ent2);
		ent.setNestedEntities(map2);

		sj.add("/Files/file",ent);
//		 AppCore appCore = new AppCore();
//	     MainFrame mainFrame = MainFrame.getInstance();
//	     mainFrame.setAppCore(appCore);
		//mainFrame.getAppCore().readDataFromTable("NASTAVNI_PREDMETI"); //ovo treba pozvati pri odaberu tabele za prikaz}
	}
}
