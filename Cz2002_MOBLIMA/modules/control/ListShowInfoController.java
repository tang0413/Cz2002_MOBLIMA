package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * This is to confirm booking
 */
public class ListShowInfoController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show show;

    public ListShowInfoController(Console inheritedConsole, Show sh, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here are the detailed information of the show";
    }

    /**
     * for admin use
     */
    public ListShowInfoController(Console inheritedConsole, int showId)
    {
        super(inheritedConsole);
        try{
            this.show = (Show)DataBase.getObjById(showId, Show.class);
        } catch (Exception e){
            this.console.logWarning("No such show!");
        }
        logText = "Please indicate one attribute to change";
    }

    public void enter(Boolean isAdmin){
        while(true){
            try{
                this.console.logText(logText);
                constuctLogInfo(isAdmin, movie, cineplex, show);
                int choice = this.console.getInt("Enter index to proceed", 1, isAdmin?7:2);
                switch (choice){
                    case 1:
                        if(!isAdmin){
                            TicketController ticket = new TicketController(console, this.movie, this.cineplex, this.show);
                            ticket.enter();
                            break;
                        }
                    case 2:
                        if(!isAdmin){
                            return;
                        }
                    case 3:
                    case 4:
                    case 5:
                        UpdateShowController show = new UpdateShowController(console);
                        show.enter(choice, this.show.getId());
                        break;
                    case 6:
                        console.logWarning("This will deleted this show information. Continue?");
                        if(console.getStr("Type 'y' to continue").equals("y")){
                            UpdateShowController deleteShow = new UpdateShowController(console);
                            deleteShow.enter(choice, this.show.getId());
                            return;
                        } else {
                            break;
                        }
                    case 7:
                        return;
                }
            } catch (Exception e){
                console.logWarning(e.getMessage());
            }
        }
    }

    private void constuctLogInfo(Boolean isAdmin, Movie movie, Cineplex cineplex, Show showtime)
    {
        logMenu = new ArrayList<>();
        if (isAdmin) {
            logMenu.add("Movie ID: " + showtime.getMovieId());
            logMenu.add("Cineplex ID: " + showtime.getCineplexId());
            logMenu.add("Cinema Name: " + showtime.getCinemaname());
            logMenu.add("Time: " + showtime.getTime());
            logMenu.add("Date: " + showtime.getDate());
            logMenu.add("\u001B[31mDELETE\u001B[0m");
            logMenu.add("Back");
            this.console.logMenu(logMenu);
        } else {
            logMenu.add("Movie Name: " + movie.getName());
            logMenu.add("Cineplex Name: " + cineplex.getCineplexName());
            logMenu.add("Cinema Name: " + showtime.getCinemaname());
            logMenu.add("Show Time: " + showtime.getTime() + " " + showtime.getDate());
            this.console.logMenu(logMenu, true);
            logMenu = new ArrayList<>();
            logMenu.add("Proceed to choose seats");
            logMenu.add("Back");
            this.console.logMenu(logMenu);
        }
    }

    @Override
    @Deprecated
    public void enter() {

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
