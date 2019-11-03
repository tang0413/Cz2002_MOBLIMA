package root;

import modules.data.DataBase;

/**
 * A wrapper to run or restart the MOBLIMA App
 * The starting point of the whole program.
 * @author zhangxinyi
 *
 * @version 1.0
 * @since 2019-10-29
 */
public class RunApp {
	/**
	 * An instance of App to run
	 */
	private static App app;
	/**
	 * Instantiate a new App and run it
	 */
	public static void main(String[] args) {
		app = new App();
		app.run();
	}
	/**
	 * To give an effect of restarting App
	 * It will clear the database buffer and destroy the current running app, then instantiate a new app and run it to achieve the effect of "restating app"
	 */
	public static void restart(){
		DataBase.clearBuffer();
		app = null;
		app = new App();
		app.run();
	}
}
