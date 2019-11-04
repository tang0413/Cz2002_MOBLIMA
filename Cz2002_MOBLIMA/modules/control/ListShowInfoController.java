package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListShowInfoController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show show;
    private int moviePosition;
    private int cineplexPosition;
    private int showtimePosition;
    private ArrayList<Show> showList = new ArrayList<>();

    public ListShowInfoController(Console inheritedConsole, Show sh, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here are the detailed information of the show";
    }

    @Override
    public void enter(){
        while(true){
            try{
                constuctLogIno(movie, cineplex, show);
                int choice = this.console.getInt("Enter index to proceed", 1, 3);
                if (choice == 1){
                    TicketController ticket = new TicketController(console, this.movie, this.cineplex, this.show);
                    ticket.enter();
                }
                else
                    return;
            } catch (Exception e){
                console.logWarning(e.getMessage());
            }
        }
    }

    private void constuctLogIno(Movie movie, Cineplex cineplex, Show showtime)
    {
        logMenu = new ArrayList<>();
        logMenu.add("Movie Name: " + movie.getName());
        logMenu.add("Cineplex Name: " + cineplex.getCineplexName());
        logMenu.add("Cinema Name: " + showtime.getCinemaname());
        logMenu.add("Show Time: " + showtime.getTime() + " " + showtime.getDate());
        this.console.logText(logText);
        this.console.logMenu(logMenu, true);
        logMenu = new ArrayList<>();
        logMenu.add("Proceed to choose seats");
        logMenu.add("Back");
        this.console.logMenu(logMenu);

    }
    /*public ListShowInfoController(Console inheritedConsole, int showtimeId, Movie mv, Cineplex ci)
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
