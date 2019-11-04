package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.Ticket;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class TicketController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show showtime;
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
        this.showtime = sh;

    }

    @Override
    public void enter() {
        while(true){
            try{
                ticketList = DataBase.readList(TICKETFILENAME, Ticket.class);
            }catch (Exception e){
                this.console.logWarning(e.getMessage());
            }
            this.seatPlan(ticketList);
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            if(choice == 1)
            {

            }
        }
    }

    public void seatPlan(ArrayList<Ticket> ticketList)
    {
        int i,j, sc=0;
        String[] data = new String[100];
        Ticket[] array = ticketList.toArray(new Ticket[ticketList.size()]);
        for(Ticket t : array) {
            if (t.getMovieId() == this.movie.getId() && t.getCineplexId() == this.cineplex.getId() && t.getShowId() == this.showtime.getId()) {
                data[sc] = t.getSeats();
                sc++;
            }
        }
        sc=0;
        if(this.showtime.getCinemaname().equals("a"))
        {
            System.out.print("|");
            ROWS=9;
            SEATS=9;
            int showseats[][] = new int [ROWS][SEATS];
            for(i=0; i < ROWS; i++) {
                for(j=0; j < SEATS; j++) {
                    while(sc != 100)
                    {
                        int pos = data[sc].charAt(0) - 'A' - 129;
                        if(i==pos && j==(Integer.parseInt(data[sc].substring(1))))
                        {
                            System.out.print("X");
                        }
                        else
                            System.out.print("_");
                        sc++;
                    }
                    sc=0;
                }
                System.out.print("|");
                System. out. print("\n");
            }
        }
        else if(this.showtime.getCinemaname().equals("b"))
        {
            System.out.print("|");
            ROWS=10;
            SEATS=10;
            int showseats[][] = new int [ROWS][SEATS];
            for(i=1; i != ROWS; i++) {
                for(j=1; j != SEATS; j++) {
                    while(sc != 100)
                    {
                        int pos = data[sc].charAt(0) - 'A' - 129;
                        if(i==pos && j==(Integer.parseInt(data[sc].substring(1))))
                        {
                            System.out.print("X");
                        }
                        else
                            System.out.print("_");
                        sc++;
                    }
                    sc=0;
                }
                System.out.print("|");
                System. out. print("\n");
            }
        }
        else
        {
            System.out.print("|");
            ROWS=11;
            SEATS=11;
            int showseats[][] = new int [ROWS][SEATS];
            for(i=1; i != ROWS; i++) {
                for(j=1; j != SEATS; j++) {
                    while(sc != 100)
                    {
                        int pos = data[sc].charAt(0) - 'A' - 129;
                        if(i==pos && j==(Integer.parseInt(data[sc].substring(1))))
                        {
                            System.out.print("X");
                        }
                        else
                            System.out.print("_");
                        sc++;
                    }
                    sc=0;
                }
                System.out.print("|");
                System. out. print("\n");
            }
        }
    }
}
