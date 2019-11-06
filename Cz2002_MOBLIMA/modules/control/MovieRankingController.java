package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

/**
 * Represents a router page which allows a user to choose in which way the movie list shall be sorted and is able to proceed to the listing after choosing
 */
public class MovieRankingController extends BaseController implements generalEnter {
    /**
     * This is to instantiate a controller with all sorting options in menu
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public MovieRankingController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please indicate the sorting option";
        logMenu = new ArrayList<>();
        logMenu.add("By ticket sales");
        logMenu.add("By user rating");
        logMenu.add("Back");
    }

    /**
     * This is to enter a series of process to display the sorting options and get the user's choice
     * The user will be redirected to the movie list page after indicating the sort option
     */
    public void enter() {
        while (true){
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            switch (choice) {
                case 1:
                case 2:
                    ListMovieController ls = new ListMovieController(this.console, choice);
                    ls.enter(false);
                    break;
                case 3:
                    return;
            }
        }
    }
}
