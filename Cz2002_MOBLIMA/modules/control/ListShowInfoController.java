package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * Represents a type of controller that is able to list out detailed show information for common users to confirm before proceeding to booking, or for staff to choose from to edit.
 */
public class ListShowInfoController extends BaseController {
    /**
     * The specific movie that user chose before. not applicable for staff
     */
    private Movie movie;
    /**
     * The specific cineplex that user chose before. not applicable for staff
     */
    private Cineplex cineplex;
    /**
     * The specific show whose information is to be displayed on screen
     */
    private Show show;

    /**
     * This is for common user use. specific movie, cineplex and show record required
     * To instantiate a controller specially for displaying detailed show information for users to check before booking
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param sh the show chosen by the user
     * @param mv the movie chosen by the user
     * @param ci the cineplex chosen by the user
     */
    public ListShowInfoController(Console inheritedConsole, Show sh, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here are the detailed information of the show";
    }

    /**
     * This is for admin use. Only show Id is required for dynamic loading purpose
     * To instantiate a controller specifically for displaying show information for the staff to edit
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param showId the id of the show chosen by the staff
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

    /**
     * This is to enter a series of actions to display the detailed show information for common user, or to list out attributes of a show record for admin to choose from to edit
     * After the common user confirm their choice, they will be either redirect to choose seat.
     * After the staff chooses a specific attribute, he will be redirected to change that field.
     * @param isAdmin true if it is for admin use
     */
    public void enter(Boolean isAdmin){
        while(true){
            try{
                this.console.logText(logText);
                constuctLogInfo(isAdmin, movie, cineplex, show);
                int choice = this.console.getInt("Enter index to proceed", 1, isAdmin?7:2);
                switch (choice){
                    case 1:
                        if(!isAdmin){
                            TicketingController ticket = new TicketingController(console, this.movie, this.cineplex, this.show);
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
                        console.logWarning("This will completely deleted this show from the Database. You are not recommended to do so unless the show was just mis-entered. Continue?");
                        console.logReminder("To disable users from booking, simply set a movie's status to 'End Of Showing' or 'Coming Soon' will do");
                        if(console.getStr("Type 'YES' to continue").equals("YES")){
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
                console.logWarning("Failed to load the show information!");
                return;
            }
        }
    }

    /**
     * This is to construct a logMenus containing information of a specific show for common user, or detailed attributes of a show record for staff
     * @param isAdmin true if it is for admin use
     * @param movie the movie chosen by the user. Not used when isAdmin is true
     * @param cineplex the cineplex chosen by the user. Not used when isAdmin is true
     * @param showtime the show chosen by the user
     */
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
            logMenu.add("Check Available Seats");
            logMenu.add("Back");
            this.console.logMenu(logMenu);
        }
    }

    @Override
    @Deprecated
    public void enter() {

    }
}
