package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Actor;
import modules.entity.movie.Movie;
import modules.data.DataBase;

import java.util.ArrayList;

public class ListMovieInfoController extends BaseController {
    private int movieId;
    private Movie movie;
    private static final String FILENAME = "ActorList.txt";
    public ListMovieInfoController(Console inheritedConsole, int movieId, Movie movie) {
        super(inheritedConsole);
        this.movieId = movieId;
        this.movie= movie;
        logMenu = new ArrayList<String>();
        logMenu.add("Check Reviews"); //TODO: if inside bookings, can make review and rating
        logMenu.add("Proceed to booking");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        this.console.logText("This is the basic information of " + movie.getName());
        this.console.log("Name: " + movie.getName());
        this.console.log("Rating: " + movie.getRating());
        this.console.log("Description: " + movie.getDescription());
        this.console.log("Cast: " + getMovieCast());
        this.console.logMenu(logMenu);
        int choice = this.console.getInt("Enter index to proceed", 1, 3);
        if (choice == 3){
            return;
        } //TODO: wait the rest to be finished.

    }

    public String getMovieCast(){
        ArrayList<String> castList = new ArrayList<String>();
        try{
            ArrayList<Actor> wholeActorList = DataBase.readActorList(FILENAME);
            for (Actor a: wholeActorList){
                if (a.getInMovie().contains(Integer.toString(this.movieId))){
                    castList.add(a.getName());
                }
            }
        } catch(Exception e){
        }
        String res = String.join(",", castList);
        return res;
    }
}
