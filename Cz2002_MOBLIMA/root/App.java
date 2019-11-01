package root;

import modules.boundary.Console;
import modules.control.UserSelectingController;
public class App {
	private static String version = "0.1";
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
