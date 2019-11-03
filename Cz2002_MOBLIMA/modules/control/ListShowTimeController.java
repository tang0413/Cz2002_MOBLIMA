package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.ShowTime;
import modules.entity.movie.Movie;

import modules.data.DataBase;
import java.util.ArrayList;

public class ListShowTimeController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private ShowTime showtime;
    private int showPosition;
    private ArrayList<ShowTime> showTimeList = new ArrayList<>();
    private static final String SHOWTIMEFILENAME = "ShowList.txt";

    public ListShowTimeController(Console inheritedConsole, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        logText = "Here are the Show Time";
    }


    @Override
    public void enter() {
        while(true){
            try{
                showTimeList = DataBase.readList(SHOWTIMEFILENAME, ShowTime.class);
            }catch (Exception e){
            }
            this.contructLogMenu(showTimeList);
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to see movie details", 1, showTimeList.size()+1);
            if(choice == showTimeList.size()+1)
                return;
            else
            {
                ListShowTimeInfoController showInfo = new ListShowTimeInfoController(console,choice, this.movie, this.cineplex);
                showInfo.enter();
            }
        }
    }


    public void contructLogMenu(ArrayList<ShowTime> showTimeList)
    {
        logMenu = new ArrayList<>();
        for(ShowTime st : showTimeList) {
            if(st.getMovieId() == this.movie.getId() && st.getCineplexId() == (this.cineplex.getId()+1)) {
                if (this.movie.getStatus().equals("Preview") || this.movie.getStatus().equals("Now Showing")) {
                    logMenu.add(st.getTime());
                }
            }
        }
        logMenu.add("Back");
    }
    /*protected ArrayList<ShowTime> showtimeList;
    private Movie movie;
    private Cineplex cineplex;
    private static final String FILENAME = "ShowList.txt";
    public ListShowTimeController(Console inheritedConsole, Movie mv, Cineplex ci) {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        logText = "Here are the Show Time";
        logMenu = new ArrayList<String>();
        try {
            showtimeList = DataBase.readList(FILENAME, ShowTime.class);
            for(ShowTime st : showtimeList)
            {
                if(st.getMovieId() == this.movie.getId() && st.getCineplexId() == (this.cineplex.getId()+1)) {
                    if (this.movie.getStatus().equals("Preview") || this.movie.getStatus().equals("Now Showing")) {
                        logMenu.add(st.getTime());
                    }
                }
            }
        } catch (Exception e) {
        }
        logMenu.add("Back");
    }
    @Override
    public void enter() {
        while(true)
        {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to see cineplex details",1,showtimeList.size()+1);
            if(choice == showtimeList.size()+1){
                return;
            } else {
                ListShowTimeInfoController showInfo = new ListShowTimeInfoController(console, choice, showtimeList.get(choice - 1), this.movie, this.cineplex);
                showInfo.enter();
            }
        }
    }*/
}
