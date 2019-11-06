package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Ticket;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a type of controller that is able to list out movie names in console
 */
public class ListMovieController extends BaseController implements withAdminEnter {
    /**
     * This is the whole list of movies loaded from the database;
     */
    private ArrayList<Movie> movieList;
    /**
     * This is the sort option of the movie list
     * 1 for sort by ticket sales and 2 for sort by user rating; 0 for no sorting requirement
     */
    private int sortOption;

    /**
     * This is to instantiate a controller with no sorting requirement
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    @Deprecated
    public ListMovieController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are all movies";
    }

    /**
     * This is to instantiate a controller with sorting requirement
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param sortOption 1 for sort by ticket sales and 2 for sort by user rating; 0 for no sorting requirement
     */
    public ListMovieController(Console inheritedConsole, int sortOption) {
        super(inheritedConsole);
        this.sortOption = sortOption;
        switch (sortOption){
            case 1:
                logText = "Here are sorted movies by ticket sales";
                break;
            case 2:
                logText = "Here are sorted movies by user rating";
                break;
            default:
                logText = "Here are all movies";
                break;
        }
    }

    /**
     * This is to enter a series of process to display the movie list and let user to choose.
     * @param isAdmin true if it's for admin use
     */
    public void enter(Boolean isAdmin) {
        while (true) {
            try{
                movieList = DataBase.readList(Movie.class);
                movieList = this.constructLogMenu(movieList, sortOption);
                this.console.logText(logText);
                this.console.logMenu(logMenu);
                int choice = this.console.getInt("Enter index to proceed", 1, movieList.size()+1);
                if (choice == movieList.size()+1){
                    return;
                } else {
                    ListMovieInfoController movieInfo = new ListMovieInfoController(console, movieList.get(choice -1).getId());
                    movieInfo.enter(isAdmin);
                }
            } catch (Exception e){
                console.logWarning(e.getMessage());
                console.logWarning("Failed to load Movies!");
                return;
            }
        }
    }

    /**
     * This is to display all movies by order if required and return the re-sorted movie list
     * @param movieList The whole list of movies
     * @param sortOption 1 for sort by ticket sales and 2 for sort by user rating; 0 for no sorting requirement
     * @return the re-sorted movie list by sortOption
     */
    private ArrayList constructLogMenu(ArrayList<Movie> movieList, int sortOption){
        ArrayList<Movie> newMovieList = new ArrayList<>();
        logMenu = new ArrayList<>();
        switch (sortOption){
            case 0:
                for (Movie m: movieList){
                    if (!m.getStatus().equals("End Of Showing")){
                        logMenu.add(m.getName());
                        newMovieList.add(m);
                    } else {
                        continue;
                    }
                }
                break;
            case 1:
                try{
                    HashMap<Integer, Integer> salesCount = new HashMap<>();
                    for (Movie m: movieList){
                        salesCount.put(m.getId(), 0);
                    }
                    ArrayList<Ticket> ticketList = DataBase.readList(Ticket.class);
                    for (Ticket t: ticketList){
                        salesCount.put(t.getMovieId(), salesCount.get(t.getMovieId())+1);
                    }
                    HashMap<Integer,Integer> sortedMap = Sorting.sortByValue(salesCount);
                    int i = 0;
                    for (Map.Entry<Integer, Integer> en : sortedMap.entrySet()){
                        if (i >= 5){
                            break;
                        }
                        Movie m = (Movie)DataBase.getObjById(en.getKey(), Movie.class);
                        if (!m.getStatus().equals("End Of Showing")){
                            logMenu.add(m.getName());
                            newMovieList.add(m);
                            i ++;
                        } else {
                            continue;
                        }
                    }
                    break;
                } catch (Exception e){
                    console.logWarning("Failed to do the sorting!");
                }
            case 2:
                Movie[] array = movieList.toArray(new Movie[movieList.size()]);
                Sorting.selectionSortReverse(array);
                int i = 0;
                for (Movie m: array){
                    if (i >= 5){
                        break;
                    }
                    if (!m.getStatus().equals("End Of Showing")){
                        logMenu.add(m.getName());
                        newMovieList.add(m);
                        i ++;
                    } else {
                        continue;
                    }
                }
                break;
        }
        logMenu.add("Back");
        return newMovieList;
    }
}
