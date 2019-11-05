package modules.control;
//package control;

import modules.boundary.Console;
import modules.entity.Admin;
import modules.data.DataBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents a series of actions to log in
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
				if (validate(adminList, username, password)) {
					StaffMenuController staffMenu = new StaffMenuController(this.console);
					staffMenu.enter();
					return; //for now
				} else {
					this.console.logWarning("Invalid Credential!");
					if (!tryAgain()){
						return;
					};
				}
			} catch (Exception e) {
				console.logWarning(e.getMessage());
				console.logWarning("Failed to load the admin list!");
				return;
			}
		}
	}

	/**
	 * This is to check if the user input username and password fit any of the admins
	 * @param adminList
	 * @param username
	 * @param password
	 * @return
	 */
	private Boolean validate(ArrayList<Admin> adminList, String username, String password) {
		for (Admin a : adminList) {
			if (a.auth(username, password)) {
				return true;
			}
		}
		return false;
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
