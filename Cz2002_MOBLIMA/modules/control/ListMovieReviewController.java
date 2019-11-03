package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * Represents a series of actions to display all reviews of a movie and provide action options
 * Not in use
 */
@Deprecated
public class ListMovieReviewController extends BaseController {

    /**
     * This is the id of the user chosen movie
     */
    private int movieId;

    /**
     * This is to instantiate a controller with a specific movieID
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param movieId the id of the user chosen movie
     */
    public ListMovieReviewController(Console inheritedConsole, int movieId) {
        super(inheritedConsole);
        this.movieId = movieId;
        logMenu = new ArrayList<>();
        logMenu.add("Make Review");
        logMenu.add("Back");
    }

    @Override
    public void enter() {

    }
}
