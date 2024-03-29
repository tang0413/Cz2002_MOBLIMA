package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * Represents a type of controller that is able to list out show plans for a specific movie in a specific cineplex for common user, or all valid show plans for staff
 */
public class ListShowController extends BaseController implements WithAdminEnter {
    /**
     * The specific movie that user chose before. not applicable for staff
     */
    private Movie movie;
    /**
     * The specific cineplex that user chose before. not applicable for staff
     */
    private Cineplex cineplex;
    /**
     * The whole list of all cineplexes; loaded inside enter()
     */
    private ArrayList<Show> showList;

    /**
     * This is for admin use. No specific movie or cineplex required.
     * To instantiate a controller specially for displaying show list
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public ListShowController(ConsoleUI inheritedConsoleUI)
    {
        super(inheritedConsoleUI);
        logText = "Here are all the Shows";
    }

    /**
     * This is for common user use. specific movie or cineplex required
     * To instantiate a controller specially for displaying show list
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     * @param mv the movie chosen by the user
     * @param ci the cineplex chosen by the user
     */
    public ListShowController(ConsoleUI inheritedConsoleUI, Movie mv, Cineplex ci)
    {
        super(inheritedConsoleUI);
        this.movie = mv;
        this.cineplex = ci;
        logText = "Here are the available shows of "+ movie.getName() + " in " + cineplex.getCineplexName() + " Cineplex";
    }

    /**
     * This is to enter a series of actions to display all available shows for common user, or to list out all show records in details for admin to choose from
     * @param isAdmin true if it is for admin use
     */
    public void enter(Boolean isAdmin) {
        while(true){
            try{
                showList = DataBase.readList(Show.class);
                ArrayList<Show> selectedShowList = this.constructLogMenu(showList, isAdmin);
                this.consoleUI.logText(logText);
                if (selectedShowList.size()==0){
                    this.consoleUI.log("");
                    this.consoleUI.log("No shows are available!");
                    this.consoleUI.log("");
                }
                this.consoleUI.logMenu(logMenu);
                int choice = this.consoleUI.getInt("Enter index to see show details", 1, selectedShowList.size()+1);
                if(choice == selectedShowList.size()+1)
                    return;
                else
                {
                    if (!isAdmin) {
                        ListShowInfoController showInfo = new ListShowInfoController(consoleUI, selectedShowList.get(choice-1), this.movie, this.cineplex);
                        showInfo.enter(false);
                    } else {
                        ListShowInfoController showInfo = new ListShowInfoController(consoleUI, selectedShowList.get(choice-1).getId());
                        showInfo.enter(true);
                    }

                }
            }catch (Exception e){
                consoleUI.logWarning(e.getMessage());
                consoleUI.logWarning("Failed to load the shows!");
                return;
            }
        }
    }

    /**
     * This is to construct a logMenus containing brief information of all  available shows for common user, or detailed show information of all valid shows for admin
     * @param showList The whole list of all cineplexes
     * @param isAdmin true if it is for admin use
     * @return A selected list which contains all the show entries listed on screen in sequence. Used to find the user-chosen show
     */
    private ArrayList<Show> constructLogMenu(ArrayList<Show> showList, Boolean isAdmin)
    {
        ArrayList selectedShowList = new ArrayList();
        logMenu = new ArrayList<>();
        for(Show st : showList) {
            if (!isAdmin){//TODO should sort by time
                if(st.getMovieId() == this.movie.getId() && st.getCineplexId() == (this.cineplex.getId())) {
                    selectedShowList.add(st);
                    logMenu.add("Cinema: " + st.getCinemaname() + " Time: " + st.getTime() + " " + st.getDate());
                }
            } else {
                try{
                    Movie chosenMovie = (Movie)DataBase.getObjById(st.getMovieId(), Movie.class);
                    Cineplex chosenCineplex = (Cineplex)DataBase.getObjById(st.getCineplexId(), Cineplex.class);
                    if (!chosenMovie.getStatus().equals("End Of Showing")){
                        selectedShowList.add(st);
                        logMenu.add("\nMovie ID: "+ st.getMovieId() + "\nMovie Name: " + chosenMovie.getName()
                                + "\nCinplex ID: "+ st.getCineplexId() +"\nCinplex: " + chosenCineplex.getCineplexName()+ "\nCinema: " + st.getCinemaname()
                                + "\nTime: " + st.getTime() + "\nDate: " + st.getDate() + "\n");
                    }
                } catch (Exception e){
                    consoleUI.logWarning(e.getMessage());
                }
            }
        }
        logMenu.add("Back");
        return selectedShowList;
    }

}
