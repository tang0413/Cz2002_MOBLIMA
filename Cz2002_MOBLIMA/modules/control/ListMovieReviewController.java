package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListMovieReviewController extends BaseController {
    private Movie movie;
    public ListMovieReviewController(Console inheritedConsole, Movie mv) {
        super(inheritedConsole);
        this.movie = mv;
        logText = "Here is the reviews of: " + this.movie.getName();
        logMenu = new ArrayList<String>();
        logMenu.add("Make Review");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true){
            this.console.logText(logText);
            ArrayList<String> reviews = this.movie.getReview();
            this.console.logWithSeparator(reviews, "|");
            //TODO: need to handle "no review" case
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 2);
            switch (choice){
                case 2:
                    return;
                    //TODO: finish whole function
            }
        }


    }
}
