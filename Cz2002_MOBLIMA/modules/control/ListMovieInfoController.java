package modules.control;

import modules.boundary.ConsoleUI;
import modules.entity.movie.Movie;
import modules.data.DataBase;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents a type of controller that is able to display movie information details differently for admin or customer
 */
public class ListMovieInfoController extends BaseController implements WithAdminEnter {

    /**
     * This is the id of the user chosen movie
     */
    private int movieID;

    /**
     * This is to instantiate a controller with a specific movieID
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     * @param movieId the id of the user chosen movie
     */
    public ListMovieInfoController(ConsoleUI inheritedConsoleUI, int movieId) {
        super(inheritedConsoleUI);
        this.movieID = movieId;
    }

    /**
     * This is to enter a series of process to display the detailed information of the chosen list and provide action options
     * @param isAdmin true if it's for admin use
     */
    public void enter(Boolean isAdmin) {
        while (true){
            try{
                Movie chosenMovie = (Movie)DataBase.getObjById(this.movieID, Movie.class);
                constructLogInfo(isAdmin, chosenMovie);
                int choice = this.consoleUI.getInt("Enter index to proceed", 1, logMenu.size());
                switch (choice) {
                    case 1:
                        if (!isAdmin){
                            checkMovieReviews(chosenMovie);
                            break;
                        }
                    case 2:
                        if (!isAdmin){
                            if (chosenMovie.getStatus().equals("Preview") || chosenMovie.getStatus().equals("Now Showing")){
                                ListCineplexController available = new ListCineplexController(consoleUI, chosenMovie);
                                available.enter(false);
                            } else {
                                consoleUI.logReminder("Oops~ Booking not available for now!");
                                TimeUnit.SECONDS.sleep(2);
                            }
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
                        UpdateMovieController update = new UpdateMovieController(this.consoleUI);
                        update.enter(choice, this.movieID);
                        break;
                    case 8:
                        return;
                }
            } catch (Exception e){
                consoleUI.logWarning(e.getMessage());
                consoleUI.logWarning("Failed to load the movie information!");
                return;
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
            this.consoleUI.logText(logText);
            ArrayList<String> reviews = chosenMovie.getReview();
            if (reviews.size() >= 1){
                this.consoleUI.logWithSeparator(reviews, "|");
            } else {
                this.consoleUI.log("");
                this.consoleUI.log("Oops, no review for now!");
                this.consoleUI.log("");
            }
            ArrayList<String> subLogMenu = new ArrayList<>();
            subLogMenu.add("Make Review");
            subLogMenu.add("Back");
            this.consoleUI.logMenu(subLogMenu);
            int choice = this.consoleUI.getInt("Enter index to proceed", 1, 2);
            switch (choice){
                case 1:
                    MovieReviewingController mvReview = new MovieReviewingController(this.consoleUI, chosenMovie);
                    mvReview.enter();
                    break;
                case 2:
                    return;
            }
        } catch (Exception e){
            this.consoleUI.log(e.getMessage());
        }
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
            this.consoleUI.logText("Choose from the following attributes of: " + movie.getName());
            this.consoleUI.logReminder("Movie ID: " + movie.getId());
            logMenu.remove(1);
            logMenu.add("Back");
            this.consoleUI.logMenu(logMenu);
        } else {
            this.consoleUI.logText("Below is the detailed information of: " + movie.getName());
            this.consoleUI.logMenu(logMenu, true);
            logMenu = new ArrayList<>();
            logMenu.add("Check Reviews");
            logMenu.add("Proceed to booking");
            logMenu.add("Back");
            this.consoleUI.logMenu(logMenu);
        }
    }
}
