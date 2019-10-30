package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public class StaffMenuController extends BaseController {
    public StaffMenuController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose from the following options";
        logMenu = new ArrayList<String>();
        logMenu.add("Quit");
    }

    @Override
    public void enter() {
        this.console.logText(logText);
        this.console.logMenu(logMenu);
        int choice = this.console.getInt("Your Choice", 1, 1);
        switch (choice) {
            case 1:
                return;
        }
    }
}
