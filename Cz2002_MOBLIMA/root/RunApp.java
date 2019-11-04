package root;

import modules.data.DataBase;

/**
 * Represents a wrapper to run or restart the App.
 */
public class RunApp {
	/**
	 * This is the App to be run
	 */
	private static App app;

	/**
	 * The entrance of the program
	 * @param args
	 */
	public static void main(String[] args) {
		app = new App();
		app.run();
	}

	/**
	 * This is to create an effect of restarting a App by destroy the previous APP, then instantiate and enter a new App.
	 */
	@Deprecated
	public static void restart(){
		DataBase.clearBuffer();
		app = null;
		app = new App();
		app.run();
	}
}
