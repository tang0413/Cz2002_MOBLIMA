package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.MovieGoner;
import modules.entity.Show;
import modules.entity.Ticket;
import modules.entity.movie.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a series of actions to list out booking histories of a user specified by email
 */
public class BookingHistoryController extends BaseController {
    /**
     * The whole list of all movies; loaded inside enter()
     */
    private ArrayList<MovieGoner> movieGonerList;
    /**
     * The whole list of all tickets; loaded inside enter()
     */
    private ArrayList<Ticket> ticketList;
    /**
     * The user specified by email
     */
    private MovieGoner user;
    /**
     * This is to instantiate a Controller specially for displaying booking histories
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public BookingHistoryController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Your booking history";
        logMenu = new ArrayList<>();
    }

    /**
     * This is to enter a serious of process to get the user's email and display his/her booking records accordingly
     */
    @Override
    public void enter() {
        try {
            movieGonerList = DataBase.readList(MovieGoner.class);
            ticketList = DataBase.readList(Ticket.class);
            console.logText(logText);
            String email;
            Boolean foundUser;
            while (true){
                email = console.getEmail();
                foundUser = false;
                for (MovieGoner mg: movieGonerList){
                    if (mg.getEmail().equals(email)){
                        foundUser = true;
                        user = mg;
                        break;
                    }
                }
                if (!foundUser){
                    console.logWarning("No user was found with this email! Do you want to try again?");
                    if(console.getStr("[y/n]").equals("y")){
                        continue;
                    } else {
                        return;
                    }
                }
                HashMap<String, String> bookingRecord = new HashMap<>();
                for (Ticket t: ticketList){
                    if (t.getCustId() == user.getId()){
                        if (bookingRecord.get(t.gettId()) == null){
                            bookingRecord.put(t.gettId(), constructTickInfo(t));
                        } else {
                            bookingRecord.put(t.gettId(), bookingRecord.get(t.gettId())+ ", " +t.getSeats());
                        }
                    }
                }
                Iterator hmIterator = bookingRecord.entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)hmIterator.next();
                    logMenu.add(mapElement.getValue().toString());
                }
                if (logMenu.size() == 0){
                    logMenu.add("");
                    logMenu.add("Oops, you don't have any booking records for now. Go make your first booking!");
                }
                console.logMenu(logMenu, true);
                logMenu = new ArrayList<>();
                logMenu.add("Back");
                console.logMenu(logMenu);
                int choice = console.getInt("Enter 1 to return to previous page", 1, 1);
                return;
            }
        } catch (Exception e){
            console.logWarning(e.getMessage());
            console.logWarning("Failed to get booking history!");
        }
    }

    /**
     * This is to construct a string of detailed information on a ticket.
     * @param t a ticket that booked by the user
     * @return a string of detailed information on the ticket 't'
     * @throws Exception Some information contained inside the ticket is incorrect
     */
    private String constructTickInfo(Ticket t) throws Exception {
        Movie mv = (Movie)DataBase.getObjById(t.getMovieId(), Movie.class);
        Cineplex cineplex = (Cineplex)DataBase.getObjById(t.getCineplexId(), Cineplex.class);
        Show sh = (Show)DataBase.getObjById(t.getShowId(), Show.class);
        return  "\nTID: " + t.gettId() + //TODO can add more info
                "\nMovie: " + mv.getName() +
                "\nCineplex: " + cineplex.getCineplexName() +
                "\nCinema:  " + sh.getCinemaname() +
                "\nTime: " + sh.getTime() + " " + sh.getDate() +
                "\nSeat(s): " + t.getSeats();
    }
}
