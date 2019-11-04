package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;

public class ListAvailableCineplexController extends BaseController{
    private Movie movie;
    private ArrayList<Cineplex> cineplexList = new ArrayList<>();

    public ListAvailableCineplexController(Console inheritedConsole, Movie mv) {
        super(inheritedConsole);
        this.movie = mv;
        logText = "Here are all Cineplexes";
    }

    @Override
    public void enter()  {
        while (true){
            try{
                cineplexList = DataBase.readList(Cineplex.class);
            }catch (Exception e){
            }
            this.contructLogMenu(cineplexList);
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, cineplexList.size()+1);
            if(choice == cineplexList.size()+1)
                return;
            else
            {
                ListShowController showTime = new ListShowController(console,this.movie, cineplexList.get(choice-1));
                showTime.enter(false);
            }
        }
    }

    private void contructLogMenu(ArrayList<Cineplex> cineplexList){
        logMenu = new ArrayList<>();
        for (Cineplex c : cineplexList){
            logMenu.add(c.getCineplexName());
        }
        logMenu.add("Back");
    }



//    private ArrayList<String> checkAvailableCineplex(){
//
//    }
}
