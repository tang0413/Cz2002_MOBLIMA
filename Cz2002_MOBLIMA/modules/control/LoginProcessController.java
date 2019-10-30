package modules.control;

import modules.boundary.Console;
import modules.entity.AdminList;

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
				this.console.logText("logged in!");
				//do sth here, enter the next controller
				return; //for now
			} else {
				this.console.logText("Invalid Credential!");
				return;
			}
		}
	}
}
