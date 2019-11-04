package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListShowController extends BaseController {
    private Movie movie;
    private Cineplex cineplex;
    private Show showtime;
    private int showPosition;
    private ArrayList<Show> showList = new ArrayList<>();

    public ListShowController(Console inheritedConsole, Movie mv, Cineplex ci)
    {
        super(inheritedConsole);
        this.movie = mv;
        this.cineplex = ci;
        logText = "Here are the Show Time";
    }

    public void enter(Boolean isAdmin) { //TODO admin can see all the shows
        while(true){
            try{
                showList = DataBase.readList(Show.class);
            }catch (Exception e){
            }
            ArrayList<Show> selectedShowList = this.contructLogMenu(showList, isAdmin);
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to see show details", 1, selectedShowList.size()+1);
            if(choice == selectedShowList.size()+1)
                return;
            else
            {
                ListShowInfoController showInfo = new ListShowInfoController(console, selectedShowList.get(choice-1), this.movie, this.cineplex);
                showInfo.enter();
            }
        }
    }




    public ArrayList<Show> contructLogMenu(ArrayList<Show> showList, Boolean isAdmin)//TODO for admin list all
    {
        ArrayList selectedShowList = new ArrayList();
        logMenu = new ArrayList<>();
        for(Show st : showList) {
            if(st.getMovieId() == this.movie.getId() && st.getCineplexId() == (this.cineplex.getId())) { //TODO sytanx error getId
                selectedShowList.add(st);
                logMenu.add("Cinema: " + st.getCinemaname() + " Time:" + st.getTime() + " " + st.getDate());
            }
        }
        logMenu.add("Back");
        return selectedShowList;
    }

    @Override
    @Deprecated
    public void enter() {

    }
}
