package modules.control;
//package control;

import modules.boundary.Console;
import modules.entity.Admin;
import modules.data.DataBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a type of controller that is able to do log in for an admin
 */
public class LoginProcessController extends BaseController {
	/**
	 * This is the whole list of all admins
	 */
	protected ArrayList<Admin> adminList;

	/**
	 * This is to instantiate a log in controller
	 * @param inheritedConsole
	 */
	public LoginProcessController(Console inheritedConsole) {
		super(inheritedConsole);
		logText = "Please enter your username and email";
	}

	/**
	 * This is to enter a series of process to take and validate username and password input
	 * If the credentials are valid, the user will be redirect to staff menu
	 */
	@Override
	public void enter() {
		while (true) {
			this.console.logText(logText);
			String username = this.console.getStr("Your username");
			String password = this.console.getStr("Your password");
			try {
				adminList = DataBase.readList(Admin.class);
				Admin thisAdmin = validate(username, password);
				if (thisAdmin != null){
					StaffMenuController staffMenu = new StaffMenuController(this.console, thisAdmin);
					staffMenu.enter();
					return;
				} else {
					this.console.logWarning("Invalid Credential!");
					if (!tryAgain()){
						return;
					};
				}
			} catch (Exception e) {
				e.printStackTrace();
				console.logWarning(e.getMessage());
				console.logWarning("Failed to load the admin list!");
				return;
			}
		}
	}

	/**
	 * This is to check if the user input username and password fit any of the admins
	 * @param username the user entered username
	 * @param password the user entered password
	 * @return the Admin whose username and password matches the user input. null if no such admin.
	 */
	private Admin validate(String username, String password) {
		for (Admin a : adminList) {
			if (a.auth(username, password)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * This is to provide an option to quit or try again when failed to log i n
	 * @return true if the user enters "y". false if not.
	 */
	private Boolean tryAgain() {
		String tryAgain = this.console.getStr("Try again?[y/n]");
		if (tryAgain.equals("y")){
			return true;
		}
		return false;
	}
}
