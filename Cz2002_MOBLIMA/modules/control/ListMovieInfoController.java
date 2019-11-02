package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Actor;
import modules.entity.movie.Movie;
import modules.data.DataBase;
import modules.utils.Sorting;

import java.util.ArrayList;

public class ListMovieInfoController extends BaseController {//TODO can be combined with listMovie in the future
    private int sortOption;
    private int moviePosition;
    public ListMovieInfoController(Console inheritedConsole, int movieId, int sortOption) {
        super(inheritedConsole);
        this.moviePosition = movieId;
        this.sortOption = sortOption;
    }

    public void enter(Boolean isAdmin) {
        while (true){
            try{
                ArrayList<Movie> movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
                Movie chosenMovie = movieList.get(moviePosition); //TODO repeated code
                if (this.sortOption == 2){
                    Movie[] array = movieList.toArray(new Movie[movieList.size()]);
                    Sorting.selectionSortReverse(array);
                    chosenMovie = array[moviePosition];
                } else if (this.sortOption == 1){
                    //TODO finish by sale ranking
                }
                constructLogInfo(isAdmin, chosenMovie);
                int choice = this.console.getInt("Enter index to proceed", 1, logMenu.size());
                switch (choice) {
                    case 1:
                        if (!isAdmin){
                            ListMovieReviewController review = new ListMovieReviewController(console, moviePosition);
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
                        update.enter(choice, moviePosition);
                        break;
                    case 8:
                        return;
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
