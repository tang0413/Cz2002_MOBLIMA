package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListMovieReviewController extends BaseController {
    private int moviePosition;
    public ListMovieReviewController(Console inheritedConsole, int moviePosition) {
        super(inheritedConsole);
        this.moviePosition = moviePosition;
        logMenu = new ArrayList<String>();
        logMenu.add("Make Review");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true){
            try{
                ArrayList<Movie> movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
                Movie chosenMovie = movieList.get(moviePosition);
                logText = "Here is the reviews of: " + chosenMovie.getName();
                this.console.logText(logText);
                ArrayList<String> reviews = chosenMovie.getReview();
                if (reviews.size() >= 1){
                    this.console.logWithSeparator(reviews, "|");
                } else {
                    this.console.log("");
                    this.console.log("Oops, no review for now!");
                    this.console.log("");
                }
                this.console.logMenu(logMenu);
                int choice = this.console.getInt("Enter index to proceed", 1, 2);
                switch (choice){
                    case 1:
                        MovieReviewingController mvReview = new MovieReviewingController(this.console, chosenMovie);
                        mvReview.enter();
                        break;
                    case 2:
                        return;
                }
            } catch (Exception e){
                this.console.log(e.getMessage());
            }
        }
    }
}
