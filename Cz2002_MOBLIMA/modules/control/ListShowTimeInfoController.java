package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.ShowTime;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListShowTimeInfoController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private ShowTime showtime;
    private int moviePosition;
    private int cineplexPosition;
    private int showtimePosition;
    private static final String MOVIEFILENAME = "MovieList.txt";
    private static final String CINEFILENAME = "CineplexList.txt";
    private static final String SHOWTIMEFILENAME = "ShowList.txt";
    private ArrayList<ShowTime> showTimeList = new ArrayList<>();

    public ListShowTimeInfoController(Console inheritedConsole, int showtimePosition, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        logText = "Here are the Show Time";
    }

    @Override
    public void enter(){
        while(true){
            try{
                ArrayList<Movie> movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
                ArrayList<Cineplex> cineplexesList = DataBase.readList(CINEFILENAME, Cineplex.class);
                ArrayList<ShowTime> showTimeList = DataBase.readList(SHOWTIMEFILENAME, ShowTime.class);
                Movie chosenMovie = movieList.get(moviePosition);
                Cineplex chosenCineplex = cineplexesList.get(cineplexPosition);
                ShowTime chosenShowTime = showTimeList.get(showtimePosition);
                constuctLogIno(chosenMovie, chosenCineplex, chosenShowTime);
                int choice = this.console.getInt("Enter index to proceed", 1, 3);
                if (choice == 1){
                    /*ListSeatsController seat = new ListSeatsController(console, chosenMovie, chosenCineplex, chosenShowTime);
                    seat.enter();*/
                }
                else
                    return;
            } catch (Exception e){
            }
        }
    }

    private void constuctLogIno(Movie movie, Cineplex cineplex, ShowTime showtime)
    {
        logMenu = new ArrayList<>();
        logMenu.add("Movie Name:" + movie.getName());
        logMenu.add("Cineplex Name:" + cineplex.getCineplexName());
        logMenu.add("Show Time:" + showtime.getTime());
        this.console.logText("This is the basic information of ");
        this.console.logMenu(logMenu);
        logMenu = new ArrayList<>();
        logMenu.add("Choose Seats");
        logMenu.add("Back");
        this.console.logMenu(logMenu);

    }
    /*public ListShowTimeInfoController(Console inheritedConsole, int showtimeId, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.showtimeId = showtimeId;
        this.cineplex=ci;
        this.movie=mv;
        this.showtime = showtime;
        logMenu = new ArrayList<String>();
        logMenu.add("Choose Seats");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while(true) {
            this.console.logText("This is the basic information of " + cineplex.getCineplexName());
            this.console.log("Movie Name:" + movie.getName());
            this.console.log("Cineplex Name:" + cineplex.getCineplexName());
            this.console.log("Show Time:" + showtime.getTime());
            this.console.log("");
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 2);
            if (choice == 2)
                return;
            else {

            }
        }
    }*/
}
