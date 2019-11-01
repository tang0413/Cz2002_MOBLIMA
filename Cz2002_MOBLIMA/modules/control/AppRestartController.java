package modules.control;

import modules.boundary.Console;
import root.RunApp;

import java.util.ArrayList;

public class AppRestartController extends BaseController {
    public AppRestartController(Console inheritedConsole) {
        super(inheritedConsole);
    }
    @Override
    public void enter() {
        this.console.log(" ");
        this.console.logReminder("Updated successfully! Restart the app to see the change?");
        this.console.log(" ");
        this.logMenu = new ArrayList<>();
        this.logMenu.add("\u001B[31m" + "RESTART" + "\u001B[0m");
        this.logMenu.add("Back");
        this.console.logMenu(this.logMenu);
        int choice = this.console.getInt("Enter index to proceed", 1,2);
        switch (choice){
            case 1:
                RunApp.restart();
                break;
            case 2:
                break;
        }
    }
}
