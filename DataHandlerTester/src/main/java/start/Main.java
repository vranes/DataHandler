package start;

import view.frame.*;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("core.JsonRegistrator");
		MainFrame mainFrame = MainFrame.getInstance();
	}
}
