package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.*;
import modules.entity.movie.Movie;

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
    private MovieGoner movieGoner;
    private int ROWS;
    private int SEATS;
    private int option;
    private ArrayList<Ticket> ticketList = new ArrayList<>();
    private ArrayList<TicketType> ticketTypesList = new ArrayList<>();
    private ArrayList<MovieGoner> movieGonersList = new ArrayList<>();

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
                ticketTypesList = DataBase.readList(TicketType.class);
                ticketList = DataBase.readList(Ticket.class);
                movieGonersList = DataBase.readList(MovieGoner.class);
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

    private String[] checkUserBooked()
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

    private void bookMovie(ArrayList<String> indicatedSeats)
    {
        //id=1|movieId=1|cineplexId=1|showId=1|tickettype=1|seats=F07
        int i=0,scc=0,check=0,s=0;
        String[] sc = new String[2];
        String email = "";
        String days = "";
        String chosenSeats = "";
        String tempchosenSeats="";
        String name="";
        String phoneNumber="";
        Double ticketPrice;
        String tId="";
        int ticketType;
        int checkAge = 0;
        String[] data = checkUserBooked();
        int checkUser;
        try {
            for (String seat: indicatedSeats){
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
        }catch (Exception e)
        {
            console.logWarning(e.getMessage());
        }

        try {
            days = checkDays(show.getDate());
        }catch (Exception e)
        {
            console.logWarning(e.getMessage());
        }
        email = this.console.getEmail();
        checkUser = checkExistsUser(email);
        if(checkUser == 0)
        {
            name = this.console.getStr("Enter Your Name");
            phoneNumber = this.console.getStr("Enter Your Phone Number");
            this.console.logReminder("Welcome! "+ name);
            this.console.log("");
        }
        contructAgeMenu();
        checkAge = this.console.getInt("Choose your age category:", 1,3);

        ticketPrice = checkPrice(days, checkAge);
        ticketType = checkTicketType(days, checkAge);

        tId = generateTID();

        //add new user
        if (checkUser == 0){
            ArrayList<String> newUserParam = new ArrayList<>();
            int newUserId = DataBase.getNewId(MovieGoner.class);
            newUserParam.add(Integer.toString(newUserId));
            newUserParam.add(name);
            newUserParam.add(phoneNumber);
            newUserParam.add(email);
            try
            {
                MovieGoner newUser = new MovieGoner(newUserParam);
                DataBase.setData(newUser);
                checkUser = newUserId;
            }catch (Exception e){
                this.console.log(e.getMessage());
                this.console.logWarning("Failed to store user information!");
            }
        }

        //TODO put a while loop here
        ArrayList<String> newTicketParam = new ArrayList<>();
        int newTicketId = DataBase.getNewId(Ticket.class);
        newTicketParam.add(Integer.toString(newTicketId));
        newTicketParam.add(Integer.toString(this.show.getMovieId()));
        newTicketParam.add(Integer.toString(this.show.getCineplexId()));
        newTicketParam.add(Integer.toString(this.show.getId()));
        newTicketParam.add(Integer.toString(ticketType));
        newTicketParam.add(sc[s]);
        newTicketParam.add(Integer.toString(checkUser));
        newTicketParam.add(tId);

        try
        {
            Ticket newTicket = new Ticket(newTicketParam);
            DataBase.setData(newTicket);
            console.logSuccess();;
        }catch (Exception e){
            this.console.log(e.getMessage());
            this.console.logWarning("Failed to store ticket information!");
        }
    }

    private int checkExistsUser(String Email)
    {
        int userId = 0;
        for(MovieGoner mg : this.movieGonersList)
        {
            if(mg.getEmail().equals(Email)) {
                this.console.logReminder("Welcome Back! " + mg.getName());
                this.console.log("");
                userId = mg.getId();
                break;
            }
        }
        return userId;
    }

    private Double checkPrice(String date, int cat)
    {
        int tempCat = cat;
        String tempDate = date;
        Double confirmPrice = 0.0;
        DecimalFormat df = new DecimalFormat("#.##"); //TODO repeat code!!
        int time = Integer.parseInt(show.getTime().substring(0,2));
        if(time<12 && tempCat==1 && ((!tempDate.equals("Sat")||(!tempDate.equals("Sun")))))
                tempDate = "Sen";
        if(time<12 && tempCat==2 && ((!tempDate.equals("Sat")||(!tempDate.equals("Sun")))))
                tempDate = "Stu";
        for(TicketType tt : ticketTypesList)
        {
            if(tempDate.equals(tt.getDays()))
            {
                if(movie.getType().equals("3D"))
                {
                    confirmPrice =  tt.getdPrice();
                }
                else if(movie.getType().equals("Blockburster"))
                    confirmPrice = tt.getrPrice() + 1;
                else
                    confirmPrice = tt.getrPrice();
            }
        }
        return Double.valueOf(df.format(confirmPrice * 1.07));
    }

    private int checkTicketType(String date, int cat)
    {
        int tempCat = cat;
        int ticketTypeId = 0;
        String tempDate = date;
        DecimalFormat df = new DecimalFormat("#.##");
        int time = Integer.parseInt(show.getTime().substring(0,2));
        if(time<12 && tempCat==1 && ((!tempDate.equals("Sat")||(!tempDate.equals("Sun")))))
            tempDate = "Sen";
        if(time<12 && tempCat==2 && ((!tempDate.equals("Sat")||(!tempDate.equals("Sun")))))
            tempDate = "Stu";
        for(TicketType tt : ticketTypesList)
        {
            if(tempDate.equals(tt.getDays()))
            {
                ticketTypeId = tt.getId();
            }
        }
        return ticketTypeId;
    }

    private String checkDays(String date) throws Exception
    {
        String checkDate = date;
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkDate);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        String stringDate = sdf.format(date1);
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
    private String generateTID()
    {
        String TID = "";
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(Calendar.getInstance().getTime());
        TID = show.getCinemaname() + timeStamp;
        return TID;
    }
}
