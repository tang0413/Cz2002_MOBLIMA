package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;

/**
 * Represents a series actions to list out movie names in console
 */
public class ListMovieController extends BaseController{ //TODO: experiment with refreshing list;
    /**
     * This is the whole list of movies loaded from the database;
     */
    private ArrayList<Movie> movieList = new ArrayList<>();
    private int sortOption;
    /**
     * This is to instantiate a controller with no sorting requirement
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    @Deprecated
    public ListMovieController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are all movies";
    }

    @Override
    @Deprecated
    public void enter() {
    }

    /**
     * This is to instantiate a controller with sorting requirement
     * @param inheritedConsole
     * @param sortOption 1 for sort by ticket sales and 2 for sort by user rating; 0 for no sorting requirement
     */
    public ListMovieController(Console inheritedConsole, int sortOption) {
        super(inheritedConsole);
        this.sortOption = sortOption;
        switch (sortOption){
            case 1:
                logText = "Here are sorted movies by ticket sales";
                break;
            case 2:
                logText = "Here are sorted movies by user rating";
                break;
            default:
                logText = "Here are all movies";
                break;
        }
    }

    /**
     * This is to enter a serious process to display the movie list and let user to choose.
     * @param isAdmin true if it's for admin use
     */
    public void enter(Boolean isAdmin) {
        while (true) {
            try{
                movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
            } catch (Exception e){
            }
            movieList = this.constructLogMenu(movieList, sortOption);
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, movieList.size()+1);
            if (choice == movieList.size()+1){
                return;
            } else {
                ListMovieInfoController movieInfo = new ListMovieInfoController(console, movieList.get(choice -1).getId());
                movieInfo.enter(isAdmin);
            }
                //display info controller (choice);
        }
    }

    /**
     * This is to display all movies by order if required
     * @param movieList The whole list of movies
     * @param sortOption 1 for sort by ticket sales and 2 for sort by user rating; 0 for no sorting requirement
     */
    //TODO change description
    private ArrayList constructLogMenu(ArrayList<Movie> movieList, int sortOption){
        ArrayList<Movie> newMovieList = new ArrayList<>();
        logMenu = new ArrayList<>();
        switch (sortOption){
            case 0:
            case 1://TODO by sales
                for (Movie m: movieList){
                    logMenu.add(m.getName());
                }
                break;
            case 2:
                Movie[] array = movieList.toArray(new Movie[movieList.size()]);
                Sorting.selectionSortReverse(array);
                for (Movie m: array){
                    logMenu.add(m.getName());
                    newMovieList.add(m);
                }
                break;
        }
        logMenu.add("Back");
        if (newMovieList.size()>0){
            return newMovieList;
        }
        return movieList;
    }
}
