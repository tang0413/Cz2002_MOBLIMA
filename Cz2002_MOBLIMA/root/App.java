package root;

import modules.boundary.Console;
import modules.control.UserSelectingController;
import modules.entity.movie.Movie;

/**
 * Represents the whole MOBLIMA App
 * @author zhangxinyi
 * @version 1.0
 * @since 2019-10-29
 */
public class App {
	private static String version = "0.1";
	/**
	 * The starting process of the app.
	 * Garbage collector will be run at the entry in case of restarting
	 * An instance of Console class will be created for the input and output inside command line tools
	 * Then an instance of UserSelectingController is created and entered
	 * After returning form the UserSelectingController, the scanner will be closed and the process will quit
	 */
	public void run(){
		System.gc();
		Console console = new Console();
		console.logText("Welcome to our movie system! Version: " + version);
		UserSelectingController user = new UserSelectingController(console);
		user.enter(); //not implemented now.
		console.logText("Thank you for using our APP!");
		console.destoryScanner();
		System.exit(0);
	}
}
