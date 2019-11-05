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

public class TicketingController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show show;
    private ArrayList<MovieGoner> movieGonersList;
    private ArrayList<TicketType> ticketTypesList;
    private ArrayList<Holiday> holidayList;
    private int ROWS;
    private int SEATS;
    private int option;
    private ArrayList<Ticket> ticketList = new ArrayList<>();

    public TicketingController(Console inheritedConsole, Movie mv, Cineplex ci, Show sh)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Here is the seat availability condition of the show";
    }

    @Override
    public void enter() {
        while(true){
            try{
                ticketList = DataBase.readList(Ticket.class);
            }catch (Exception e){
                this.console.logWarning(e.getMessage());
            }
            this.console.logText(logText);
            this.console.logSeatPlan(ticketList, this.show);
            contructLogMenu();
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            if(choice == 1)
            {
                ArrayList<String> chosenSeats = this.console.getSeat();
                bookMovie(chosenSeats);
            }
            else
            {
                return;
            }
        }
    }

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

    private void bookMovie(ArrayList<String> indicatedSeats) {
        //id=1|movieId=1|cineplexId=1|showId=1|tickettype=1|seats=F07
        int i = 0, scc = 0, check = 0, s = 0, checkAge = 0, confirmBuy = 0,count = indicatedSeats.size(), ticketcounter = 1, h=0, count2 = indicatedSeats.size(), count3 = 0,count4=0;
        String[] sc = new String[100];
        int[] type = new int[100];
        Double[] confirmPrice = new Double[100];
        String email = "";
        String days = "";
        String chosenSeats = "";
        String tempchosenSeats = "";
        String name = "";
        String phoneNumber = "";
        Double ticketPrice=0.00,totalPrice=0.00,ticketType=0.00;
        String tId = "";
        String[] data = checkUserBooked(ticketList);
        int checkUser;
        try {
            for (String seat : indicatedSeats) {
                tempchosenSeats = seat;
                while (data[i] != null) {
                    if (tempchosenSeats.equals(data[i]))
                        check = 1;
                    i++;
                }
                if (check != 1) {
                    sc[scc] = tempchosenSeats;
                    scc++;
                }
            }
        } catch (Exception e) {
            console.logWarning(e.getMessage());
        }

        try {
            days = checkDays(show.getDate());
        } catch (Exception e) {
            console.logWarning(e.getMessage());
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
            checkAge = this.console.getInt("Choose your age category for ticket:" + ticketcounter , 1, 3);
            ticketType = checkPrice(days, checkAge, 1);
            type[h]=ticketType.intValue();
            ticketPrice = checkPrice(days, checkAge , 2);
            confirmPrice[h]=ticketPrice;
            ticketcounter++;
            count--;
            h++;
        }

        while(count3!=indicatedSeats.size())
        {
            try{
                movieGonersList = DataBase.readList(MovieGoner.class);
                ticketTypesList = DataBase.readList(TicketType.class);
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
                this.console.log("Movie Rating: " + movie.getRating());
                this.console.log("Movie Type: " + movie.getType());
                this.console.log("Movie Category: " + movie.getCat());
                this.console.log("Movie Description: " + movie.getDescription());
                this.console.log("Movie Director: " + movie.getDirector());
                this.console.log("Movie Cast: " + movie.getCast());
                this.console.log("Movie Status: " + movie.getStatus());
                this.console.log("Movie Cineplex Name: " + cineplex.getCineplexName());
                this.console.log("Movie Cinema Name: " + show.getCinemaname());
                this.console.log("Movie Show Time: " + show.getTime() + " " + show.getDate());
                this.console.log("Seats: " + sc[count4]);
                this.console.log("Ticket Type: " + type[count4]);
                this.console.log("Prices: " + confirmPrice[count4]);
                totalPrice+=confirmPrice[count4];
                count4++;

            }
            catch (Exception e) {
                this.console.log(e.getMessage());
                this.console.logWarning("Failed to store ticket information!");
            }
            this.console.log("Total Prices: " + totalPrice);
            count3++;
        }

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
            while (count2 != 0) {
                //TODO put a while loop here
                ArrayList<String> newTicketParam = new ArrayList<>();
                int newTicketId = DataBase.getNewId(Ticket.class);
                newTicketParam.add(Integer.toString(newTicketId));
                newTicketParam.add(Integer.toString(this.show.getMovieId()));
                newTicketParam.add(Integer.toString(this.show.getCineplexId()));
                newTicketParam.add(Integer.toString(this.show.getId()));
                newTicketParam.add(Integer.toString(type[s]));
                newTicketParam.add(sc[s]);
                newTicketParam.add(Integer.toString(checkUser));
                newTicketParam.add(tId);

                try {
                    Ticket newTicket = new Ticket(newTicketParam);
                    DataBase.setData(newTicket);
                    console.logSuccess();
                    ;
                } catch (Exception e) {
                    this.console.log(e.getMessage());
                    this.console.logWarning("Failed to store ticket information!");
                }
                count2--;
                s++;
            }
        }
        else
        {
            return;
        }
    }

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

    private Double checkPrice(String date, int cat, int c)//check logic
    {
        int tempCat = cat;
        int getId = 0;
        String tempDate = date;
        Double confirmPrice = 0.0;
        DecimalFormat df = new DecimalFormat("#.##"); //TODO repeat code!!
        try {
            ticketTypesList = DataBase.readList(TicketType.class);
            int time = Integer.parseInt(show.getTime().substring(0, 2));
            if(!tempDate.equals("Sat") || (!tempDate.equals("Sun")))
            {
                if (time < 18 && tempCat == 1) { tempDate = "Sen"; }

                if (time < 18 && tempCat == 2 ) { tempDate = "Stu"; }
            }
            if(tempDate.equals("Fri"))
            {
                if (time < 18 && tempCat == 2 ) { tempDate = "Fri6"; }
            }
            for (TicketType tt : ticketTypesList) {
                if (tempDate.equals(tt.getDays())) {
                    if (movie.getType().equals("3D")) {
                        confirmPrice = tt.getdPrice();
                        getId = tt.getId();
                    } else if (movie.getType().equals("Blockburster")) {
                        confirmPrice = tt.getrPrice() + 1;
                        getId = tt.getId();
                    }
                    else
                    {
                        confirmPrice = tt.getrPrice();
                        getId = tt.getId();
                    }
                }
            }
        }catch (Exception e)
            {
                console.logWarning(e.getMessage());
                console.logWarning("Failed to check price!");

            }
        if(c==1)
            return Double.valueOf(getId);
        else
            return Double.valueOf(df.format(confirmPrice * 1.07));
    }

    private String checkDays(String date) throws Exception
    {
        String checkDate = date;
        String stringDate = "";
        int check = 0;
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkDate);
        for(Holiday h : holidayList)
        {
            if(h.getDate().equals(checkDate))
            {
                stringDate = "Pub";
                check = 1;
            }
        }
        if(check == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            stringDate = sdf.format(date1);
        }
        return stringDate;
    }

    private void contructLogMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Proceed to choose seats");
        logMenu.add("Back");
        this.console.logMenu(logMenu);
    }

    private void contructAgeMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Senior Citizen");
        logMenu.add("Student");
        logMenu.add("None");
        this.console.logMenu(logMenu);
    }

    private void contructConfirmBuy()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Confirm Purchase");
        logMenu.add("Cancel Purchase");
        this.console.logMenu(logMenu);
    }

    private String generateTID()
    {
        String TID = "";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.getInstance().getTime());
        TID = show.getCinemaname() + timeStamp;
        return TID;
    }
}
