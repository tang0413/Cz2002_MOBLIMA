package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.*;
import modules.entity.movie.Movie;
import modules.entity.MovieGoner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents a type of controller that is able to list out detailed show information for movie and allow user to book ticket.
 */


public class TicketingController extends BaseController {
    /**
     * The specific movie that user chose before. not applicable for staff
     */
    private Movie movie;
    /**
     * The specific cineplex that user chose before. not applicable for staff
     */
    private Cineplex cineplex;
    /**
     * The specific show that user chose before. not applicable for staff
     */
    private Show show;
    /**
     * The specific ArrayList for MovieGoner to store user data
     */
    private ArrayList<MovieGoner> movieGonersList;
    /**
     * The specific ArrayList for Ticket Type to store Type of Ticket data
     */
    private ArrayList<TicketType> ticketTypesList;
    /**
     * The specific ArrayList for Holiday Type to store Holiday date's data
     */
    private ArrayList<Holiday> holidayList;
    /**
     * The specific ArrayList for Ticket to store all transaction data
     */
    private ArrayList<Ticket> ticketList = new ArrayList<>();
    /**
     * The specific int for Rows and Seats to display seat layout
     */
    private int ROWS;
    private int SEATS;


    /**
     * This is for common user use. specific movie, cineplex and show record required
     * To instantiate a controller specially for displaying detailed show information for users to check before booking
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param sh the show chosen by the user
     * @param mv the movie chosen by the user
     * @param ci the cineplex chosen by the user
     */
    public TicketingController(Console inheritedConsole, Movie mv, Cineplex ci, Show sh)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here is the seat availability condition of the show";
    }

    /**
     * This is to enter a series of actions to display the detailed show information for common user, or to list out attributes of a show record for user to book movie ticket
     * After the common user confirm their choice, they will be allow them to eat seat number.
     */
    @Override
    public void enter() {
        while(true){
            try{
                ticketList = DataBase.readList(Ticket.class);
                movieGonersList = DataBase.readList(MovieGoner.class);
                ticketTypesList = DataBase.readList(TicketType.class);
                holidayList = DataBase.readList(Holiday.class);
            }catch (Exception e){
                this.console.logWarning(e.getMessage());
            }
            this.console.logText(logText);
            this.console.logSeatPlan(ticketList, this.show);
            contructLogMenu();
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            if(choice == 1)
            {
                ArrayList<String> chosenSeats = this.console.getSeat(show.getCinemaname());
                bookMovie(chosenSeats);
            }
            else
            {
                return;
            }
        }
    }

    /**
     * This function is to save all data the seats that have been booked
     * @param ticketList list of seats that been booked, pass into check
     * @return return all seats number that has booked
     */
    public String[] checkUserBooked(ArrayList<Ticket> ticketList)
    {
        int sc = 0;
        String[] data = new String[100];
        for(Ticket t : ticketList) {
            if (t.getShowId() == this.show.getId() && t.getCineplexId() == this.show.getCineplexId() && t.getMovieId() == this.show.getMovieId()) {
                data[sc] = t.getSeats();
                sc++;
            }
        }
        return data;
    }

    /**
     * The function to allow user enter the detail and choose detail before finalise and book ticket which been chosen
     * @param indicatedSeats seats that user selected is store and pass into
     */
    private void bookMovie(ArrayList<String> indicatedSeats) {
        //id=1|movieId=1|cineplexId=1|showId=1|tickettype=1|seats=F07
        int i = 0, scc = 0, check = 0, s = 0, checkAge = 0, confirmBuy = 0,count = indicatedSeats.size(), h=0, noTicketPurchase=0;
        String[] sc = new String[100];
        Double[] confirmPrice = new Double[100];
        String email = "",code = "",name = "",phoneNumber = "",tId="";
        Double ticketPrice=0.00,totalPrice=0.00;

        ArrayList<TicketType> thisTicketTypes = new ArrayList<>();
        TicketType thisTicketType;

        String[] data = checkUserBooked(ticketList);
        int checkUser;
        try {
            for (String seat : indicatedSeats) {
                i=0;
                while (data[i] != null) {
                    if (seat.equals(data[i]))
                        check = 1;
                    i++;
                }
                if (check != 1) {
                    sc[scc] = seat;
                    scc++;
                } else {
                    console.logWarning("You are not allowed to choose taken seats!");
                    console.logWarning("Going back to seat availability page...");
                    TimeUnit.SECONDS.sleep(2);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            console.logWarning("Failed to validate seats!");
            return;
        }

        try {
            code = checkCode(show.getDate());
        } catch (Exception e) {
            e.printStackTrace();
            console.logWarning("Failed to validate date!");
            return;
        }
        email = this.console.getEmail();
        checkUser = checkExistsUser(email);
        if (checkUser == 0) {
            name = this.console.getStr("Enter Your Name");
            phoneNumber = this.console.getStr("Enter Your Phone Number");
            this.console.logReminder("Welcome! " + name);
            this.console.log("");
        }

        while(count!=0) //determine which ticket types (can check even if have multiple type of ticket)
        {
            contructAgeMenu();
            checkAge = this.console.getInt("Choose your age category for ticket:" + h+1 , 1, 3);
            try {
                thisTicketType = checkTicketType(code, checkAge);
                thisTicketTypes.add(thisTicketType);
                ticketPrice = checkPrice(thisTicketType);
                confirmPrice[h]=ticketPrice;
                count--;
                h++;
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        count = indicatedSeats.size();

        if(checkUser!=0)
        {
            for(MovieGoner mg : movieGonersList)
            {
                if(mg.getId() == checkUser) {
                    this.console.log("Name:" + mg.getName());
                    this.console.log("Email:" + mg.getEmail());
                }
            }
        }
        else
        {
            this.console.log("Name:" + name);
            this.console.log("Email:" + email);
        }
        this.console.log("Movie Name: " + movie.getName());
        this.console.log("Movie Type: " + movie.getType());
        this.console.log("Movie Category: " + movie.getCat());
        this.console.log("Movie Description: " + movie.getDescription());
        this.console.log("Cineplex Name: " + cineplex.getCineplexName());
        this.console.log("Cinema Name: " + show.getCinemaname());
        String cinemaType = (show.getCinemaname().charAt(0)=='c')? "Platinum Movie Suites": "Regular";
        this.console.log("Cinema Type: " + cinemaType);
        this.console.log("Show Time: " + show.getTime() + " " + show.getDate());

        while(noTicketPurchase!=indicatedSeats.size())
        {
            this.console.log("");
            this.console.log("Seats: " + sc[noTicketPurchase]);
            this.console.log("Ticket Type: " + thisTicketTypes.get(noTicketPurchase).getName());
            this.console.log("Prices: S$" + confirmPrice[noTicketPurchase]);
            totalPrice+=confirmPrice[noTicketPurchase];
            noTicketPurchase++;
        }
        this.console.log("");
        this.console.logReminder("Total Prices: S$" + totalPrice);

        tId = generateTID();

        contructConfirmBuy();
        confirmBuy = this.console.getInt("Confirm Purchase?:", 1, 2);


        if (confirmBuy == 1) {
                //add new user
            if (checkUser == 0) {
                ArrayList<String> newUserParam = new ArrayList<>();
                int newUserId = DataBase.getNewId(MovieGoner.class);
                newUserParam.add(Integer.toString(newUserId));
                newUserParam.add(name);
                newUserParam.add(phoneNumber);
                newUserParam.add(email);
                try {
                    MovieGoner newUser = new MovieGoner(newUserParam);
                    DataBase.setData(newUser);
                    checkUser = newUserId;
                } catch (Exception e) {
                    this.console.log(e.getMessage());
                    this.console.logWarning("Failed to store user information!");
                }
            }
            while (count != 0) {
                ArrayList<String> newTicketParam = new ArrayList<>();
                int newTicketId = DataBase.getNewId(Ticket.class);
                newTicketParam.add(Integer.toString(newTicketId));
                newTicketParam.add(Integer.toString(this.show.getMovieId()));
                newTicketParam.add(Integer.toString(this.show.getCineplexId()));
                newTicketParam.add(Integer.toString(this.show.getId()));
                newTicketParam.add(Integer.toString(thisTicketTypes.get(s).getId()));
                newTicketParam.add(sc[s]);
                newTicketParam.add(Integer.toString(checkUser));
                newTicketParam.add(tId);

                try {
                    Ticket newTicket = new Ticket(newTicketParam);
                    DataBase.setData(newTicket);
                } catch (Exception e) {
                    this.console.log(e.getMessage());
                    this.console.logWarning("Failed to store ticket information!");
                    return;
                }
                count--;
                s++;
            }

            count=indicatedSeats.size();

            try {
                console.logSuccess();
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        else
        {
            return;
        }
    }

    /**
     * check the price of the ticket depend of movies
     * @param thisTicketType detail been pass into to check ticket type and allocated prices
     * @return price of ticket
     */
    private Double checkPrice(TicketType thisTicketType) {
        double priceWithoutAddon;
        if (movie.getType().equals("3D")) {
            priceWithoutAddon =  thisTicketType.getThreeDPrice();
        } else if (movie.getType().equals("Blockburster")) {
            priceWithoutAddon =  thisTicketType.getRegularPrice() + 1;
        } else {
            priceWithoutAddon =  thisTicketType.getRegularPrice();
        }
        if (show.getCinemaname().charAt(0)=='c'){
            priceWithoutAddon += 2;
        }
        return priceWithoutAddon;
    }

    /**
     * function to check if user is exist
     * @param Email uniqu key and pass into check if existed user
     * @return false will be pass back to allow create user if not will welcome user back
     */
    public int checkExistsUser(String Email)
    {
        int userId = 0;
        try {
            movieGonersList = DataBase.readList(MovieGoner.class);
            for(MovieGoner mg : movieGonersList)
            {
                if(mg.getEmail().equals(Email)) {
                    this.console.logReminder("Welcome Back! " + mg.getName());
                    this.console.log("");
                    userId = mg.getId();
                }
            }
        }
        catch (Exception e)
        {
                console.logWarning(e.getMessage());
                console.logWarning("Failed to check User!");

        }
        return userId;
    }

    /**
     * A function checkTicketType for future calculation purpose
     * @param date pass the days to determine the check with database
     * @param cat to know if there is chance is student or senior citizen
     * @return to pass back the type of ticket
     * @throws Exception make sure ticket type can be found, if not will show error
     */
    private TicketType checkTicketType(String date, int cat) throws Exception//check logic
    {
        TicketType thisType;
        String tempDate = date;
//        DecimalFormat df = new DecimalFormat("#.##"); //TODO repeat code!!
        try {
            ticketTypesList = DataBase.readList(TicketType.class);
            int time = Integer.parseInt(show.getTime().substring(0, 2));
            if(!tempDate.equals("Sat") && (!tempDate.equals("Sun")) && (!tempDate.equals("Pub")))
            {
                if (time < 18 && cat == 1) { tempDate = "Sen"; }

                if (time < 18 && cat == 2 ) { tempDate = "Stu"; }
            }
            else if(tempDate.equals("Fri"))
            {
                if (time < 18 && cat == 2 ) { tempDate = "Fri6"; }
            }
            for (TicketType tt : ticketTypesList) {
                if (tempDate.equals(tt.getCode())) {
                    thisType = tt;
                    return thisType;
                }
            }
        } catch (Exception e) {
            throw new Exception("Failed to check ticket type!");
        }
        throw new Exception("Failed to check ticket type!");
    }

    /**
     * check is which day of movie, example "Monday" will be "Mon'
     * @param date pass the days to determine the check with database
     * @return return "Mon' if is Monday
     * @throws Exception display error if cannot been found
     */
    private String checkCode(String date) throws Exception
    {
        String checkDate = date;
        String code = "";
        int check = 0;
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkDate);
        for(Holiday h : holidayList)
        {
            if(h.getDate().equals(checkDate))
            {
                code = "Pub";
                check = 1;
            }
        }
        if(check == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            code = sdf.format(date1);
        }
        return code;
    }

    /**
     * create menu for user to choose book or return to select time again
     */
    private void contructLogMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Proceed to choose seats");
        logMenu.add("Back");
        this.console.logMenu(logMenu);
    }

    /**
     * create menu for user to choose the special promotion for certain user
     */
    private void contructAgeMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Senior Citizen");
        logMenu.add("Student");
        logMenu.add("None");
        this.console.logMenu(logMenu);
    }

    /**
     * to confirm if user wanted to buy or not
     */
    private void contructConfirmBuy()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Confirm Purchase");
        logMenu.add("Cancel Purchase");
        this.console.logMenu(logMenu);
    }

    /**
     * this function is to generate a transaction ID
     * @return new transaction ID is pass back
     */
    private String generateTID()
    {
        String TID = "";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.getInstance().getTime());
        TID = show.getCinemaname() + timeStamp;
        return TID;
    }
}
