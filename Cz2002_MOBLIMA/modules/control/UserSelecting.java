package modules.control;

import modules.boundary.Console;
import modules.entity.AdminList;

public class UserSelecting extends BaseController{
    public UserSelecting(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    public void enter() {
        String username = this.console.getStr("Your username");
        String password = this.console.getStr("Your password");
        if (AdminList.auth(username, password)){
            this.console.logText("logged in!");
        } else {
            this.console.logText("Invalid Credential!");
        }
    }
}
