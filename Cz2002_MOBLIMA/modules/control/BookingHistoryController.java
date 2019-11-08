package modules.control;

import modules.boundary.ConsoleUI;
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
 * Represents a type of controller that is able to list out booking histories of a user specified by email
 */
public class BookingHistoryController extends BaseController implements GeneralEnter {
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
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public BookingHistoryController(ConsoleUI inheritedConsoleUI) {
        super(inheritedConsoleUI);
        logText = "Your booking history";
        logMenu = new ArrayList<>();
    }

    /**
     * This is to enter a serious of process to get the user's email and display his/her booking records accordingly
     */
    public void enter() {
        try {
            movieGonerList = DataBase.readList(MovieGoner.class);
            ticketList = DataBase.readList(Ticket.class);
            consoleUI.logText(logText);
            String email;
            Boolean foundUser;
            while (true){
                email = consoleUI.getEmail();
                foundUser = false;
                for (MovieGoner mg: movieGonerList){
                    if (mg.getEmail().equals(email)){
                        foundUser = true;
                        user = mg;
                        break;
                    }
                }
                if (!foundUser){
                    consoleUI.logWarning("No user was found with this email! Do you want to try again?");
                    if(consoleUI.getStr("[y/n]").equals("y")){
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
                consoleUI.logMenu(logMenu, true);
                logMenu = new ArrayList<>();
                logMenu.add("Back");
                consoleUI.logMenu(logMenu);
                int choice = consoleUI.getInt("Enter 1 to return to previous page", 1, 1);
                return;
            }
        } catch (Exception e){
            consoleUI.logWarning(e.getMessage());
            consoleUI.logWarning("Failed to get booking history!");
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
        String cinemaType = (sh.getCinemaname().charAt(0)=='c')? "Platinum Movie Suites": "Regular";
        return  "\nTID: " + t.gettId() + //TODO can add more info
                "\nMovie: " + mv.getName() +
                "\nCineplex: " + cineplex.getCineplexName() +
                "\nCinema:  " + sh.getCinemaname() +
                "\nCinema Type: " + cinemaType +
                "\nTime: " + sh.getTime() + " " + sh.getDate() +
                "\nSeat(s): " + t.getSeats();
    }
}
