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
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true) {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 5);
            switch (choice) {
                //TODO finish the whole menu
                case 1:
                    ListMovieController ls = new ListMovieController(this.console, 0);
                    ls.enter(false);
                    break;
                case 4:
                    MovieRankingController rank = new MovieRankingController(this.console);
                    rank.enter();
                    break;
                case 5:
                    return;
            }

        }
    }
}
