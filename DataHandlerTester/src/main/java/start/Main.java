package start;

import view.frame.*;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("core.CustomRegistrator");

		MainFrame mainFrame = MainFrame.getInstance();
		//mainFrame.getAppCore().readDataFromTable("NASTAVNI_PREDMETI"); //ovo treba pozvati pri odaberu tabele za prikaz}
	}
}
