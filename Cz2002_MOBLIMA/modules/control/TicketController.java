package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.*;
import modules.entity.movie.Movie;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class TicketController extends BaseController {
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

    public TicketController(Console inheritedConsole, Movie mv, Cineplex ci, Show sh)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        this.show = sh;
        logText = "Please choose your seats";
    }

    @Override
    public void enter() {
        while(true){
            try{
                ticketList = DataBase.readList(Ticket.class);
            }catch (Exception e){
                this.console.logWarning(e.getMessage());
            }

            this.seatPlan(ticketList);
            contructMenu();
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            if(choice == 1)
            {
                //ask user to choose seats
                //check if seats is taken
                String noTicket = this.console.getStr("How many ticket do you want to book?");
                bookMovie(noTicket);
                ListMovieController movie =  new ListMovieController(console,option);
                movie.enter();
            }
            else
            {
                return;
            }
        }
    }

    public void seatPlan(ArrayList<Ticket> ticketList)
    {
        int i,j, sc=0,c=0, increment;
        char ch;

        String[] data = checkUserBooked(ticketList);

        int size = checkCinema();
        ROWS = size/2;
        SEATS = size/2;
        increment =0;
        while(increment!=(SEATS*3))
        {
            System.out.print("=");
            increment++;
        }
        System.out.print("\n");
        System.out.print("||");

        increment =0;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("SCREEN");
        increment=1;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("||");
        System.out.print("\n||    ");
        increment =0;
        while(increment!=SEATS)
        {
            System.out.print((increment+1) + " ");
            increment++;
        }
        System.out.print(" ||");
        System.out.println("");
        int showseats[][] = new int [ROWS][SEATS];
        for(i=1; i <= ROWS; i++) {
                System.out.print("|| " + (char) (i + 64) + " |");
            for (j = 1; j <= SEATS; j++) {
                while (data[sc] != null) {
                    int pos = data[sc].charAt(0) - 64;
                    if (i == pos && j == (Integer.parseInt(data[sc].substring(1)))) {
                        System.out.print("X|");
                        c = 1;
                    }
                    sc++;
                }
                if (c == 0)
                    System.out.print("_|");
                c = 0;
                sc = 0;
            }
            System.out.print(" || \n");
        }

        System.out.print("||");

        increment =0;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("ENTRANCE");
        increment=3;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("||");
        System.out.print("\n");
        increment =0;
        while(increment!=(SEATS*3))
        {
            System.out.print("=");
            increment++;
        }
        System.out.print("\n");
    }


    public int checkCinema()
    {
        String temp = String.valueOf(this.show.getCinemaname().charAt(0));
        if(temp.equals("a"))
            return 16;
        else if(temp.equals("b"))
            return 18;
        else
            return 20;
    }

    public String[] checkUserBooked(ArrayList<Ticket> ticketList)
    {
        int sc = 0;
        String[] data = new String[100];
        for(Ticket t : ticketList) {
            if (t.getShowId() == this.show.getId() && t.getCineplexId() == this.cineplex.getId() && t.getMovieId() == this.movie.getId()) {
                data[sc] = t.getSeats();
                sc++;
            }
        }
        return data;
    }

    private void bookMovie(String ticket)
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
        String nric="";
        Double ticketPrice;
        String tId="";
        int ticketType;
        int checkAge = 0;
        int noTicket = Integer.parseInt(ticket);
        String[] data = checkUserBooked(ticketList);
        int checkUser;
        try {
            while (noTicket != 0) {
                tempchosenSeats = this.console.getStr("Enter Your Seats Code");
                while (data[i] != null) {
                    if (tempchosenSeats.equals(data[i]))
                        check = 1;
                    i++;
                }
                if (check != 1) {
                    sc[scc] = tempchosenSeats;
                    scc++;
                }
                noTicket--;
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
        email = this.console.getStr("Enter Your Email:");
        checkUser = checkExistsUser(movieGonersList,email);
        if(checkUser == 0)
        {
            name = this.console.getStr("Enter Your Name:");
            phoneNumber = this.console.getStr("Enter Your Phone Number:");
        }


        contructAgeMenu();
        checkAge = this.console.getInt("Choose your age category:", 1,3);

        ticketPrice = checkPrice(days,ticketTypesList,checkAge);
        ticketType = checkTicketType(days,ticketTypesList,checkAge);

        tId = generateTID();

        //add new user
        ArrayList<String> newUserParm = new ArrayList<>();
        int newUserId = DataBase.getNewId(MovieGoner.class);
        newUserParm.add(name);
        newUserParm.add(phoneNumber);
        newUserParm.add(email);
        try
        {
            MovieGoner newUser = new MovieGoner(newUserParm);
            DataBase.setData(newUser);
            console.logSuccess();;
        }catch (Exception e){
            this.console.log(e.getMessage());
        }

        ArrayList<String> newTicketParm = new ArrayList<>();
        int newTicketId = DataBase.getNewId(Ticket.class);
        newTicketParm.add(Integer.toString(this.movie.getId()));
        newTicketParm.add(Integer.toString(this.cineplex.getId()));
        newTicketParm.add(Integer.toString(this.show.getId()));
        newTicketParm.add(Integer.toString(ticketType));
        newTicketParm.add(sc[s]);
        if(checkUser != 0) {
            newTicketParm.add(Integer.toString(newUserId));
        }
        else
        {
            newTicketParm.add(Integer.toString(checkUser));
        }
        newTicketParm.add(tId);

        try
        {
            Ticket newTicket = new Ticket(newTicketParm);
            DataBase.setData(newTicket);
            console.logSuccess();;
        }catch (Exception e){
            this.console.log(e.getMessage());
        }
    }

    public int checkExistsUser(ArrayList<MovieGoner> movieGonersList, String Email)
    {
        int userId = 0;
        for(MovieGoner mg : movieGonersList)
        {
            if(mg.getEmail().equals(Email)) {
                System.out.print("Welcome " + mg.getName());
                userId = mg.getId();
            }
        }
        return userId;
    }

    private Double checkPrice(String date, ArrayList<TicketType> ticketTypesList, int cat)
    {
        int tempCat = cat;
        String tempDate = date;
        Double confirmPrice = 0.0;
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

    private int checkTicketType(String date, ArrayList<TicketType> ticketTypesList, int cat)
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

    private void contructMenu()
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
