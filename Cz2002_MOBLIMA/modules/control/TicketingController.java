package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.*;
import modules.entity.movie.Movie;
import modules.entity.MovieGoner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents a type of controller that is able to make booking according to the user's request
 */
public class TicketingController extends BaseController implements GeneralEnter {
    /**
     * The specific movie that user chose before.
     */
    private Movie movie;
    /**
     * The specific cineplex that user chose before.
     */
    private Cineplex cineplex;
    /**
     * The specific show that user chose before.
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
     * To instantiate a controller specially for the whole booking process
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     * @param sh the show chosen by the user
     * @param mv the movie chosen by the user
     * @param ci the cineplex chosen by the user
     */
    public TicketingController(ConsoleUI inheritedConsoleUI, Movie mv, Cineplex ci, Show sh)
    {
        super(inheritedConsoleUI);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here is the seat availability condition of the show";
    }

    /**
     * This is to enter a series of actions to display the detailed show information and let user to fill in booking information
     */
    public void enter() {
        while(true){
            try{
                ticketList = DataBase.readList(Ticket.class);
                movieGonersList = DataBase.readList(MovieGoner.class);
                ticketTypesList = DataBase.readList(TicketType.class);
                holidayList = DataBase.readList(Holiday.class);
            }catch (Exception e){
                this.consoleUI.logWarning(e.getMessage());
            }
            this.consoleUI.logText(logText);
            this.consoleUI.logSeatPlan(ticketList, this.show);
            contructLogMenu();
            int choice = this.consoleUI.getInt("Enter index to proceed", 1, 3);
            if(choice == 1)
            {
                ArrayList<String> chosenSeats = this.consoleUI.getSeat(show.getCinemaname());
                bookMovie(chosenSeats);
            }
            else
            {
                return;
            }
        }
    }

    /**
     * This function is to check the existing booking data for the specific show that the user wants to book
     * @param ticketList list of seats that been booked
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
     * The function to let the user enter, confirm details and eventually settle the booking
     * @param indicatedSeats seats that the user selected before
     */
    private void bookMovie(ArrayList<String> indicatedSeats) {
        //id=1|movieId=1|cineplexId=1|showId=1|tickettype=1|seats=F07
        int i = 0, scc = 0, check = 0, s = 0, checkAge = 0, confirmBuy = 0,count = indicatedSeats.size(), h=0, noTicketPurchase=0, ticketNumber=1;
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
                    consoleUI.logWarning("You are not allowed to choose taken seats!");
                    consoleUI.logWarning("Going back to seat availability page...");
                    TimeUnit.SECONDS.sleep(2);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            consoleUI.logWarning("Failed to validate seats!");
            return;
        }

        try {
            code = checkCode(show.getDate());
        } catch (Exception e) {
            e.printStackTrace();
            consoleUI.logWarning("Failed to validate date!");
            return;
        }
        email = this.consoleUI.getEmail();
        checkUser = checkExistsUser(email);
        if (checkUser == 0) {
            name = this.consoleUI.getStr("Enter Your Name");
            phoneNumber = this.consoleUI.getStr("Enter Your Phone Number");
            this.consoleUI.logReminder("Welcome! " + name);
            this.consoleUI.log("");
        }

        while(count!=0) //determine which ticket types (can check even if have multiple type of ticket)
        {
            contructAgeMenu();
            checkAge = this.consoleUI.getInt("Choose your age category for ticket:" + ticketNumber , 1, 3);
            try {
                thisTicketType = checkTicketType(code, checkAge);
                thisTicketTypes.add(thisTicketType);
                ticketPrice = checkPrice(thisTicketType);
                confirmPrice[h]=ticketPrice;
                count--;
                h++;
                ticketNumber++;
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
                    this.consoleUI.log("Name:" + mg.getName());
                    this.consoleUI.log("Email:" + mg.getEmail());
                }
            }
        }
        else
        {
            this.consoleUI.log("Name:" + name);
            this.consoleUI.log("Email:" + email);
        }
        this.consoleUI.log("Movie Name: " + movie.getName());
        this.consoleUI.log("Movie Type: " + movie.getType());
        this.consoleUI.log("Movie Category: " + movie.getCat());
        this.consoleUI.log("Movie Description: " + movie.getDescription());
        this.consoleUI.log("Cineplex Name: " + cineplex.getCineplexName());
        this.consoleUI.log("Cinema Name: " + show.getCinemaname());
        String cinemaType = (show.getCinemaname().charAt(0)=='c')? "Platinum Movie Suites": "Regular";
        this.consoleUI.log("Cinema Type: " + cinemaType);
        this.consoleUI.log("Show Time: " + show.getTime() + " " + show.getDate());

        while(noTicketPurchase!=indicatedSeats.size())
        {
            this.consoleUI.log("");
            this.consoleUI.log("Seats: " + sc[noTicketPurchase]);
            this.consoleUI.log("Ticket Type: " + thisTicketTypes.get(noTicketPurchase).getName());
            this.consoleUI.log("Prices: S$" + confirmPrice[noTicketPurchase]);
            totalPrice+=confirmPrice[noTicketPurchase];
            noTicketPurchase++;
        }
        this.consoleUI.log("");
        this.consoleUI.logReminder("Total Prices: S$" + totalPrice);

        tId = generateTID();

        contructConfirmBuy();
        confirmBuy = this.consoleUI.getInt("Confirm Purchase?:", 1, 2);


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
                    this.consoleUI.log(e.getMessage());
                    this.consoleUI.logWarning("Failed to store user information!");
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
                    this.consoleUI.log(e.getMessage());
                    this.consoleUI.logWarning("Failed to store ticket information!");
                    return;
                }
                count--;
                s++;
            }

            count=indicatedSeats.size();

            try {
                consoleUI.logSuccess();
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
     * check the price of the ticket according to the types of ticket, movie and cinema
     * @param thisTicketType detail been pass into to check ticket type and allocated prices
     * @return the finalized price of a ticket
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
     * function to check if the user has made any booking before
     * @param Email the user-entered email
     * @return id of the user if exists. 0 if not.
     */
    public int checkExistsUser(String Email)
    {
        int userId = 0;
        try {
            movieGonersList = DataBase.readList(MovieGoner.class);
            for(MovieGoner mg : movieGonersList)
            {
                if(mg.getEmail().equals(Email)) {
                    this.consoleUI.logReminder("Welcome Back! " + mg.getName());
                    this.consoleUI.log("");
                    userId = mg.getId();
                }
            }
        }
        catch (Exception e)
        {
                consoleUI.logWarning(e.getMessage());
                consoleUI.logWarning("Failed to check User!");

        }
        return userId;
    }

    /**
     * function to check the type of a ticket
     * @param code the three-character code which is based on the day of the show e.g. Tue; Pub for public holiday
     * @param cat the user-entered aga category. 1 for senior citizen, 2 for student, and 3 for none
     * @return the type of a ticket
     * @throws Exception Occurs when not suitable ticket type was found
     */
    private TicketType checkTicketType(String code, int cat) throws Exception//check logic
    {
        TicketType thisType;
        String tempDate = code;
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
     * function to check which day the show will be on
     * @param date the date of the show to be checked e.g. 11/12/2019
     * @return a three-character code. e.g. Tue; Pub for public holiday
     * @throws Exception Occurs when the passed in date cannot be parsed properly
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
     * function to create menu for users to choose book or return to select time again
     */
    private void contructLogMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Proceed to choose seats");
        logMenu.add("Back");
        this.consoleUI.logMenu(logMenu);
    }

    /**
     * function to create menu for users to choose the special promotion for certain age category
     */
    private void contructAgeMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Senior Citizen");
        logMenu.add("Student");
        logMenu.add("None");
        this.consoleUI.logMenu(logMenu);
    }

    /**
     * function to create menu for users to confirm entered booking information
     */
    private void contructConfirmBuy()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Confirm Purchase");
        logMenu.add("Cancel Purchase");
        this.consoleUI.logMenu(logMenu);
    }

    /**
     * function to generate a transaction ID based on current time and cinema name
     * @return new transaction ID is pass back
     */
    private String generateTID()
    {
        String TID = "";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.getInstance().getTime());
        TID = cineplex.getId() + show.getCinemaname() + timeStamp;
        return TID;
    }
}
