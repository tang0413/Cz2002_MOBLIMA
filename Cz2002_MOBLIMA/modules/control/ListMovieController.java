package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;

public class ListMovieController extends BaseController{ //TODO: experiment with refreshing list;
    private ArrayList<Movie> movieList = new ArrayList<>();
    public ListMovieController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are all movies";
    }

    @Override
    public void enter() {
    }

    public ListMovieController(Console inheritedConsole, int sortOption) {
        super(inheritedConsole);
        if (sortOption == 2){
            logText = "Here are sorted movies by user rating";
        } else {
            logText = "Here are sorted movies by ticket sales";
        }
    }

    public void enter(Boolean isAdmin, int sortOption) {
        while (true) {
            try{
                movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
            } catch (Exception e){
            }
            this.constructLogMenu(movieList, sortOption);
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, movieList.size()+1);
            if (choice == movieList.size()+1){
                return;
            } else {
                ListMovieInfoController movieInfo = new ListMovieInfoController(console, choice -1);
                movieInfo.enter(isAdmin);
            }
                //display info controller (choice);
        }
    }

    private void constructLogMenu(ArrayList<Movie> movieList, int sortOption){
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
                Sorting.insertionSort(array); //TODO: hard code to be sorting ratings. need to add by-sales methods
                for (Movie m: array){
                    logMenu.add(m.getName());
                }
                break;
        }
        logMenu.add("Back");
    }
}
