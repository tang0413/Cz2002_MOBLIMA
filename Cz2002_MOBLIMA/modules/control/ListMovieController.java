package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;

public class ListMovieController extends BaseController{
    protected ArrayList<Movie> movieList;
    private static final String FILENAME = "MovieList.txt";
    public ListMovieController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are the ongoing movies";
        logMenu = new ArrayList<String>();
        try{
            movieList = DataBase.readList(FILENAME, Movie.class);
            for (Movie m: movieList){
                logMenu.add(m.getName());
            }
        } catch (Exception e){
        }
        logMenu.add("Back");
    }

    public ListMovieController(Console inheritedConsole, int sortOption) {
        super(inheritedConsole);
        if (sortOption == 2){
            logText = "Here are sorted movies by user rating";
        } else {
            logText = "Here are sorted movies by ticket sales";
        }
        logMenu = new ArrayList<String>();
        try{
            movieList = DataBase.readList(FILENAME, Movie.class);
            Movie[] array = movieList.toArray(new Movie[movieList.size()]);
            Sorting.insertionSort(array); //TODO: hard code to be sorting ratings. need to sadd sales methods
            for (Movie m: array){
                logMenu.add(m.getName());
            }
        } catch (Exception e){
            System.out.print(e);
        }
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true) {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to see movie details", 1, movieList.size()+1);
            if (choice == movieList.size()+1){
                return;
            } else {
                ListMovieInfoController movieInfo = new ListMovieInfoController(console, choice, movieList.get(choice - 1));
                movieInfo.enter();
                //display info controller (choice);
            }
        }
    }
}
