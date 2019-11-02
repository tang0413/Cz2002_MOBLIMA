package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public class StaffMenuController extends BaseController {
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

    @Override
    public void enter() {
        while(true){
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 6);
            switch (choice) {
                case 1:
                    ListMovieController list = new ListMovieController(this.console);
                    list.enter(true, 0);
                    break;
                case 2:
                    UpdateMovieController update = new UpdateMovieController(this.console);
                    update.enter(0, 0);
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
