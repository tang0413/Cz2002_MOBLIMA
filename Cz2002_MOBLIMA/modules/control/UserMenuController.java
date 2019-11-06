package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.BaseEntity;
import modules.entity.movie.Movie;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents a router page which provides all available Movie-goner action options and is able to proceed to the corresponding functions after the choosing
 */
public class UserMenuController extends BaseController {
    /**
     * This is to instantiate a controller with all Movie-goner action options in menu
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UserMenuController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose from the following options";
        logMenu = new ArrayList<String>();
        logMenu.add("List movie");
        logMenu.add("Search movie by name");
        logMenu.add("View booking history");
        logMenu.add("Movie ranking");
        logMenu.add("Back");
    }

    /**
     * This is to enter a series of actions to allow the user to choose an action option from the menu
     * The user will be redirected to the corresponding functional page after indicating the action option
     */
    @Override
    public void enter() {
        while (true) {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 5);
            switch (choice) {
                //TODO finish the whole menu
                case 1:
                    ListMovieController ls = new ListMovieController(this.console, 0);
                    ls.enter(false);
                    break;
                case 2:
                    searchMovieByName();
                    break;
                case 3:
                    BookingHistoryController book = new BookingHistoryController(this.console);
                    book.enter();
                    break;
                case 4:
                    MovieRankingController rank = new MovieRankingController(this.console);
                    rank.enter();
                    break;
                case 5:
                    return;
            }

        }
    }

    /**
     * This is to enter a series of actions to check if there is a movie has the same name as the one user entered
     * If yes, the user will be redirected to the movie information pages
     * If no, messages will be given before returning to the previous page
     */
    private void searchMovieByName(){
        try {
            ArrayList<Movie> movieList = DataBase.readList(Movie.class);
            String movieName = console.getStr("Please enter the name of the movie:");
            Boolean found = false;
            for (Movie m: movieList){
                if (m.getName().toLowerCase().equals(movieName.toLowerCase())){
                    found = true;
                    ListMovieInfoController movieInfo = new ListMovieInfoController(console, m.getId());
                    movieInfo.enter(false);
                    break;
                }
            }
            if(!found){
                console.logWarning("No movie with name: '" + movieName + "' was found! Returning to the previous page...");
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
