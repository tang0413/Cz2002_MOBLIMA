package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Actor;
import modules.entity.movie.Movie;
import modules.data.DataBase;

import java.util.ArrayList;

public class ListMovieInfoController extends BaseController {
    private int moviePosition;
    public ListMovieInfoController(Console inheritedConsole, int movieId) {
        super(inheritedConsole);
        this.moviePosition = movieId;
    }

    public void enter(Boolean isAdmin) {
        while (true){
            try{
                ArrayList<Movie> movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
                Movie chosenMovie = movieList.get(moviePosition);
                constructLogInfo(isAdmin, chosenMovie);
                int choice = this.console.getInt("Enter index to proceed", 1, 3);
                switch (choice) {
                    case 1:
                        if (isAdmin){
                            //TODO
                            break;
                        } else {
                            ListMovieReviewController review = new ListMovieReviewController(console, moviePosition);
                            review.enter();
                            break;
                        }
                    case 2:
                        if (isAdmin){
                            //TODO
                            break;
                        } else {
                            ListAvailableCineplexController available = new ListAvailableCineplexController(console, chosenMovie);
                            available.enter();
                            break;
                        }
                    case 3:
                        if (isAdmin){
                            //TODO
                            break;
                        } else {
                            return;
                        }

                }
            } catch (Exception e){
            }
        }
    }

    @Override
    public void enter() {

    }

    private void constructLogInfo(Boolean isAdmin, Movie movie){
        logMenu = new ArrayList<>();
        logMenu.add("Name: " + movie.getName());
        logMenu.add("Rating: " + movie.getRating());
        logMenu.add("Type: " + movie.getType());
        logMenu.add("Description: " + movie.getDescription());
        logMenu.add("Director: " + movie.getDirector());
        logMenu.add("Cast: " + movie.getCast());
        logMenu.add("Status: " + movie.getStatus());
        if (isAdmin) {
            this.console.logText("Choose from the following attributes of: " + movie.getName());
            logMenu.remove(1);
            this.console.logMenu(logMenu);
        } else {
            this.console.logText("Below is the detailed information of: " + movie.getName());
            this.console.logMenu(logMenu, true);
            logMenu = new ArrayList<>();
            logMenu.add("Check Reviews");
            logMenu.add("Proceed to booking");
            logMenu.add("Back");
            this.console.logMenu(logMenu);
        }
    }
}
