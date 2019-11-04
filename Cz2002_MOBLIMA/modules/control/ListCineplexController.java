package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;

/**
 * Represents a series of actions to list out all cineplexs
 */
public class ListCineplexController extends BaseController{
    /**
     * A specific movie that the user chose before
     */
    private Movie movie;
    /**
     * The whole list of all cineplexes; loaded inside enter()
     */
    private ArrayList<Cineplex> cineplexList = new ArrayList<>();

    /**
     * This is to instantiate a controller specially for displaying all cineplexes
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param mv a user specified movie
     */
    public ListCineplexController(Console inheritedConsole, Movie mv) {
        super(inheritedConsole);
        this.movie = mv;
        logText = "Here are all Cineplexes";
    }

    /**
     * This is to enter a series of actions to display the whole cineplex list and let user to choose from it.
     * After the user indicates the choice, he/she will be redirected to see the show plan of that cineplex on the movie
     */
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

    /**
     * This is to construct a logMenu from the list of cineplexes for display purpose
     * @param cineplexList The whole list of all cineplexes
     */
    private void contructLogMenu(ArrayList<Cineplex> cineplexList){
        logMenu = new ArrayList<>();
        for (Cineplex c : cineplexList){
            logMenu.add(c.getCineplexName());
        }
        logMenu.add("Back");
    }
}
