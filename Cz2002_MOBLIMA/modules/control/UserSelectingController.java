package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

/**
 * Represents the homepage which appears after the entry of the App.
 * It allows the user to choose from "Admin Entry" or "Common User Entry"
 */
public class UserSelectingController extends BaseController implements generalEnter{
    /**
     * This is to instantiate a controller with all entries in menu
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UserSelectingController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose your user category";
        logMenu = new ArrayList<String>();
        logMenu.add("Admin Entry");
        logMenu.add("Common User Entry");
        logMenu.add("Quit");
    }

    /**
     * This is to enter a series of process to display the entry menu and let user to choose.
     * The user will be redirected to the corresponding page of action options after indicating the choice
     */
    public void enter() {
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
