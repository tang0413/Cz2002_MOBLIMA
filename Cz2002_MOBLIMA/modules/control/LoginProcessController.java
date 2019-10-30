package modules.control;

import modules.boundary.Console;
import modules.entity.AdminList;
import modules.control.StaffMenuController;

public class LoginProcessController extends BaseController{
	public LoginProcessController(Console inheritedConsole) {
		super(inheritedConsole);
		logText = "Login Process";
	}

	@Override
	public void enter() {
		while (true){
			this.console.logText(logText);
			String username = this.console.getStr("Your username");
			String password = this.console.getStr("Your password");
			if (AdminList.auth(username, password)){
				StaffMenuController staffMenu = new StaffMenuController(this.console);
				staffMenu.enter();
				return; //for now
			} else {
				this.console.logWarning("Invalid Credential!");
				//TODO: provide an option to exit
			}
		}
	}
}
