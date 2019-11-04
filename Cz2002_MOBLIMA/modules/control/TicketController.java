package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.Ticket;
import modules.entity.movie.Movie;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TicketController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show show;
    private int moviePosition;
    private int cineplexPosition;
    private int showtimePosition;
    private int ROWS;
    private int SEATS;
    private ArrayList<Ticket> ticketList = new ArrayList<>();

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
        int i=0,scc=0,check=0;
        String[] sc = new String[3];
        int noTicket = Integer.parseInt(ticket);
        String[] data = checkUserBooked(ticketList);

        while(noTicket!=0)
        {
            String chosenSeats = this.console.getStr("Enter Your Seats Code");
            String tempchosenSeats = chosenSeats.substring(0,1).toUpperCase() + chosenSeats.substring(1);
            while(data[i]!=null)
            {
                if(data[i].equals(tempchosenSeats))
                    check = 1;
                i++;
            }
            while(check!=1)
            {
                sc[scc] = tempchosenSeats;
                scc++;
            }
            noTicket--;
        }
    }

    private String checkTicketType(String date)
    {
        String checkDate = date;
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = format1.parse(checkDate);
        DateFormat format2 = new SimpleDateFormat("EEE");
        String finalDay = format2.format(dt1);
    }

    private void contructMenu()
    {
        logMenu = new ArrayList<>();
        logMenu.add("Proceed to choose seats");
        logMenu.add("Back");
        this.console.logMenu(logMenu);
    }
}
