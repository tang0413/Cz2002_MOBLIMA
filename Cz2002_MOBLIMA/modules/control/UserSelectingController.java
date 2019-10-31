package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public class UserSelectingController extends BaseController{
    public UserSelectingController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = new String("Please choose your user category");
        logMenu = new ArrayList<String>();
        logMenu.add("Staff");
        logMenu.add("Customer");
        logMenu.add("Quit");
    }
    @Override
    public void enter() {
        //TODO: this is for demo only. not actual process.
        while (true) {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            switch (choice) {
                case 1:
                    LoginProcessController login = new LoginProcessController(console);
                    login.enter();
                    break;
                case 2:
                    UserMenuController user = new UserMenuController(console);
                    user.enter();
                    break;
                case 3:
                    return;
            }
        }
    }
}
