package root;

import modules.boundary.Console;
import modules.control.UserSelecting;
public class App {
	private static String version = "0.1";
	public static void run(){
		Console console = new Console();
		console.logText("Welcome to our movie system! Version: " + version);
		UserSelecting user = new UserSelecting(console);
		user.enter(); //not implemented now.
		console.logText("Thank you for using our APP!");
		console.destoryScanner();
	}
}
