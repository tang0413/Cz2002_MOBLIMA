package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Actor;
import modules.entity.movie.Movie;
import modules.data.DataBase;
import modules.utils.Sorting;

import java.util.ArrayList;

/**
 * Represents a series of actions to displaying movie information details differently admin or customer
 */
public class ListMovieInfoController extends BaseController {//TODO can be combined with listMovie in the future

    private int movieID;

    public ListMovieInfoController(Console inheritedConsole, int movieId) {
        super(inheritedConsole);
        this.movieID = movieId;
    }

    /**
     * This is to enter a serious process to display the detailed information of the chosen list and provide action options
     * @param isAdmin true if it's for admin use
     */
    public void enter(Boolean isAdmin) {
        while (true){
            try{
                Movie chosenMovie = DataBase.getMovieById(this.movieID);
                constructLogInfo(isAdmin, chosenMovie);
                int choice = this.console.getInt("Enter index to proceed", 1, logMenu.size());
                switch (choice) {
                    case 1:
                        if (!isAdmin){
                            ListMovieReviewController review = new ListMovieReviewController(console, this.movieID);
                            review.enter();
                            break;
                        }
                    case 2:
                        if (!isAdmin){
                            ListAvailableCineplexController available = new ListAvailableCineplexController(console, chosenMovie);
                            available.enter();
                            break;
                        }
                    case 3:
                        if (!isAdmin){
                            return;
                        }
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        UpdateMovieController update = new UpdateMovieController(this.console);
                        update.enter(choice, this.movieID);
                        break;
                    case 8:
                        return;
                }
            } catch (Exception e){
            }
        }
    }

    @Override
    @Deprecated
    public void enter() {

    }

    /**
     * This is used to print movie details and action options
     * @param isAdmin true if it's for admin use
     * @param movie the exact movie object that the user has chosen
     */
    private void constructLogInfo(Boolean isAdmin, Movie movie){
        logMenu = new ArrayList<>();
        logMenu.add("Name: " + movie.getName());
        logMenu.add("Rating: " + movie.getRating());
        logMenu.add("Type: " + movie.getType());
        logMenu.add("Category: " + movie.getCat());
        logMenu.add("Description: " + movie.getDescription());
        logMenu.add("Director: " + movie.getDirector());
        logMenu.add("Cast: " + movie.getCast());
        logMenu.add("Status: " + movie.getStatus());
        if (isAdmin) {
            this.console.logText("Choose from the following attributes of: " + movie.getName());
            logMenu.remove(1);
            logMenu.add("Back");
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
