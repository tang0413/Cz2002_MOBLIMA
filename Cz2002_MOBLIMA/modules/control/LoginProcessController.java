package modules.control;

import modules.boundary.Console;
import modules.entity.Admin;
import modules.data.DataBase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LoginProcessController extends BaseController {
	protected ArrayList<Admin> adminList;
	private static final String FILENAME = "AdminList.txt";

	public LoginProcessController(Console inheritedConsole) {
		super(inheritedConsole);
		logText = "Login Process";
	}

	@Override
	public void enter() {
		while (true) {
			this.console.logText(logText);
			String username = this.console.getStr("Your username");
			String password = this.console.getStr("Your password");
			try {
				adminList = DataBase.readAdminList(FILENAME);
			} catch (FileNotFoundException e) {
				System.out.println("exception");
			}
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
		}
	}

	private Boolean validate(ArrayList<Admin> adminList, String username, String password) {
		for (Admin a : adminList) {
			if (a.auth(username, password)) {
				return true;
			}
		}
		return false;
	}

	private Boolean tryAgain() {
		String tryAgain = this.console.getStr("Try again?[y/n]");
		if (tryAgain.equals("y")){
			return true;
		}
		return false;
	}
}
