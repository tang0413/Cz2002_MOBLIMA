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

    /**
     * This is the id of the user chosen movie
     */
    private int movieID;

    /**
     * This is to instantiate a controller with a specific movieID
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param movieId the id of the user chosen movie
     */
    public ListMovieInfoController(Console inheritedConsole, int movieId) {
        super(inheritedConsole);
        this.movieID = movieId;
    }

    /**
     * This is to enter a series of process to display the detailed information of the chosen list and provide action options
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
                            checkMovieReviews(chosenMovie);
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

    /**
     * This is a sub-controller method which displays the detail review information
     * @param chosenMovie the movie object that the user has chosen
     */
    private void checkMovieReviews(Movie chosenMovie){
        try{
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
            ArrayList<String> subLogMenu = new ArrayList<>();
            subLogMenu.add("Make Review");
            subLogMenu.add("Back");
            this.console.logMenu(subLogMenu);
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

    @Override
    @Deprecated
    public void enter() {

    }

    /**
     * This is used to print movie details and action options. The menu and options will be different depending on the user category
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
