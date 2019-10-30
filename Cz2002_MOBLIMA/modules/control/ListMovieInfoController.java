package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListMovieInfoController extends BaseController {
    private int movieId;
    private Movie movie;
    public ListMovieInfoController(Console inheritedConsole, int movieId, Movie movie) {
        super(inheritedConsole);
        this.movieId = movieId;
        this.movie= movie;
        logMenu = new ArrayList<String>();
        logMenu.add("Check Reviews"); //TODO: if inside bookings, can make review and rating
        logMenu.add("Proceed to booking");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        this.console.logText("This is the basic information of " + movie.getName());
        this.console.log("Name: " + movie.getName());
        this.console.log("Rating: " + movie.getRating());
        this.console.log("Description: " + movie.getDescription());
        this.console.log("");
        this.console.logMenu(logMenu);
        int choice = this.console.getInt("Enter index to proceed", 1, 3);
        if (choice == 3){
            return;
        } //TODO: wait the rest to be finished.

    }
}
