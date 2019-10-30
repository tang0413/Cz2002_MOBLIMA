package modules.control;
//package control;

import modules.boundary.Console;

public class UserSelectingController extends BaseController{
    public UserSelectingController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = new String("Please choose your user category");
        logMenu = new String[]{"Staff", "Customer", "Quit"};

    }
    @Override
    public void enter() {
        //TODO: this is for demo only. not actual process.
        while (true) {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Your Choice", 1, 3);
            switch (choice) {
                case 1:
                    LoginProcessController login = new LoginProcessController(console);
                    login.enter();
                    break;
                case 2:
                    // do sth here
                    break;
                case 3:
                    return;
            }
        }
    }
}
