package root;

import modules.boundary.Console;
import modules.control.UserSelecting;
public class App {
	public static void run(){
		Console console = new Console();
		UserSelecting user = new UserSelecting();
//		user.run(); //not implemented now.
		console.logText("Thank you for using our APP!");
		console.destoryScanner();
	}
}
