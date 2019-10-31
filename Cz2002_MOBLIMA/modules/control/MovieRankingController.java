package modules.control;

import modules.boundary.Console;
import modules.entity.BaseEntity;

import java.util.ArrayList;

public class MovieRankingController extends BaseController {
    private int option;
    public MovieRankingController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please indicate the sorting option";
        logMenu = new ArrayList<String>();
        logMenu.add("By ticket sales");
        logMenu.add("By user rating");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true){
            this.console.log(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            switch (choice) {
                case 2: //TODO: finish case 1
                    ListMovieController ls = new ListMovieController(this.console, choice);
                    ls.enter();
                    break;
                case 3:
                    return;
            }
        }
    }
}
