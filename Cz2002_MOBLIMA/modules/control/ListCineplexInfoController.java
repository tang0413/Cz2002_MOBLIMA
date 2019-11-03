package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListCineplexInfoController extends BaseController {
    private int moviePosition;
    private int cineplexPosition;

    public ListCineplexInfoController(Console inheritedConsole, int cineplexId, Movie mv) {
        super(inheritedConsole);
        this.moviePosition = mv.getId();
        this.cineplexPosition = cineplexId;
    }

    @Override
    public void enter() {
        while (true) {
            try {
                ArrayList<Movie> movieList = DataBase.readList(Movie.class);
                ArrayList<Cineplex> cineplexesList = DataBase.readList(Cineplex.class);
                Movie chosenMovie = movieList.get(moviePosition-1);
                Cineplex chosenCineplex = cineplexesList.get(cineplexPosition);
                constructLogInfo(chosenMovie, chosenCineplex);
                int choice = this.console.getInt("Enter index to proceed", 1, 3);
                if (choice == 1){
                    ListShowTimeController showTime = new ListShowTimeController(console, chosenMovie, chosenCineplex);
                showTime.enter();
                }
                else
                        return;
            } catch (Exception e){
            }
        }
    }

    private void constructLogInfo(Movie movie, Cineplex cineplex)
    {
        logMenu = new ArrayList<>();
        logMenu.add("Movie Name:" + movie.getName());
        logMenu.add("Cineplex Name:" + cineplex.getCineplexName());
        this.console.logText("This is the basic information of " + cineplex.getCineplexName());
        this.console.logMenu(logMenu);
        logMenu = new ArrayList<>();
        logMenu.add("Check Movie Timing");
        logMenu.add("Back");
        this.console.logMenu(logMenu);


    }
}
