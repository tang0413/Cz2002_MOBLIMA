package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * Represents a type of controller that is able to list out all cineplexs
 */
public class ListCineplexController extends BaseController implements WithAdminEnter {
    /**
     * A specific movie that the user chose before
     */
    private Movie movie;
    /**
     * The whole list of all cineplexes; loaded inside enter()
     */
    private ArrayList<Cineplex> cineplexList;

    public Cineplex cineplex;
    /**
     * This is to instantiate a controller specially for displaying all cineplexes
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     * @param mv a user specified movie
     */
    public ListCineplexController(ConsoleUI inheritedConsoleUI, Movie mv) {
        super(inheritedConsoleUI);
        this.movie = mv;
        logText = "Here are all Cineplexes";
    }

    /**
     * For admin use, not specific movie required.
     * This is to instantiate a controller specially for displaying all cineplexes
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public ListCineplexController(ConsoleUI inheritedConsoleUI) {
        super(inheritedConsoleUI);
        logText = "Here are all Cineplexes";
    }

    /**
     * This is to enter a series of actions to display the whole cineplex list and let user to choose from it.
     * After the user indicates the choice, he/she will be redirected to see the show plan of that cineplex on the movie
     * If it's an admin request, he/she will be redirected to edit the chosen cineplex
     * @param isAdmin true if it's an admin request
     */
    public void enter(Boolean isAdmin)  {
        while (true){
            try{
                cineplexList = DataBase.readList(Cineplex.class);
                this.contructLogMenu(cineplexList);
                this.consoleUI.logText(logText);
                this.consoleUI.logMenu(logMenu);
                int choice = this.consoleUI.getInt("Enter index to proceed", 1, cineplexList.size()+1);
                if(choice == cineplexList.size()+1)
                    return;
                else
                {
                    if (!isAdmin){
                        ListShowController showTime = new ListShowController(consoleUI,this.movie, cineplexList.get(choice-1));
                        showTime.enter(false);
                    } else {
                        cineplex = cineplexList.get(choice-1);
                        return;
                    }
                }
            }catch (Exception e){
                consoleUI.logWarning(e.getMessage());
                consoleUI.logWarning("Failed to load Cineplexes!");
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

    public Cineplex getCineplex() {
        return cineplex;
    }
}
