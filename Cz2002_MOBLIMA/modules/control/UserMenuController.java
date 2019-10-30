package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public class UserMenuController extends BaseController {
    public UserMenuController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose from the following options";
        logMenu = new ArrayList<String>();
        logMenu.add("List movie");
        logMenu.add("Search movie");
        logMenu.add("View booking history");
        logMenu.add("Movie ranking");
        logMenu.add("Quit");
    }

    @Override
    public void enter() {
        this.console.logText(logText);
        this.console.logMenu(logMenu);
        int choice = this.console.getInt("Your Choice", 1, 1);
        switch (choice) {
            case 1:
                ListMovieController ls = new ListMovieController(this.console);
                ls.enter();
                return;
        }
    }
}
