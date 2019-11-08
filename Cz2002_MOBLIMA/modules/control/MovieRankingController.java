package modules.control;

import modules.boundary.ConsoleUI;

import java.util.ArrayList;

/**
 * Represents a router page which allows a user to choose in which way the movie list shall be sorted and is able to proceed to the listing after choosing
 */
public class MovieRankingController extends BaseController implements GeneralEnter {
    /**
     * This is to instantiate a controller with all sorting options in menu
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public MovieRankingController(ConsoleUI inheritedConsoleUI) {
        super(inheritedConsoleUI);
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
            this.consoleUI.logText(logText);
            this.consoleUI.logMenu(logMenu);
            int choice = this.consoleUI.getInt("Enter index to proceed", 1, 3);
            switch (choice) {
                case 1:
                case 2:
                    ListMovieController ls = new ListMovieController(this.consoleUI, choice);
                    ls.enter(false);
                    break;
                case 3:
                    return;
            }
        }
    }
}
