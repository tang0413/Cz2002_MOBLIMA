package root;

import modules.data.DataBase;

public class RunApp {
	private static App app;
	public static void main(String[] args) {
		app = new App();
		app.run();
	}

	public static void restart(){
		DataBase.clearBuffer();
		app = null;
		app = new App();
		app.run();
	}
}
