package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

/**
 * Represents a router page which provides all available admin action options
 */
public class StaffMenuController extends BaseController {
    /**
     * This is to instantiate a controller with all admin action options in menu
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public StaffMenuController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose from the following options";
        logMenu = new ArrayList<String>();
        logMenu.add("Update Movie Info");
        logMenu.add("Create New Movie");
        logMenu.add("Update Show Info");
        logMenu.add("Create New Show");
        logMenu.add("System Configuration");
        logMenu.add("Movie ranking");
        logMenu.add("Log off");
    }

    /**
     * This is to enter a series of actions to allow the user to choose an action option from the menu
     * The user will be redirected to the corresponding functional page after indicating the action option
     */
    @Override
    public void enter() {
        while(true){
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, logMenu.size());
            switch (choice) {
                case 1:
                    ListMovieController list = new ListMovieController(this.console, 0);
                    list.enter(true);
                    break;
                case 2:
                    UpdateMovieController update = new UpdateMovieController(this.console);
                    update.enter(0, 0);
                    break;
                case 4:
//                    UpdateShowController updateShow = new UpdateShowController(this.console);
//                    updateShow.enter(0, 0);
                    break;
                case 6:
                    MovieRankingController rank = new MovieRankingController(this.console);
                    rank.enter(); //TODO but to do so, staff will also be able to book movie
                    break;
                case 7:
                    return;
            }
        }
    }
}
