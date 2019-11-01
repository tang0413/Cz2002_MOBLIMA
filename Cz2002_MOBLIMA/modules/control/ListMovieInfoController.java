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
        logMenu.add("Check Reviews");
        logMenu.add("Proceed to booking");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        while (true){
            this.console.logText("This is the basic information of " + movie.getName());
            this.console.log("Name: " + movie.getName());
            this.console.log("Rating: " + movie.getRating());
            this.console.log("Type: " + movie.getType());
            this.console.log("Description: " + movie.getDescription());
            this.console.log("Director: " + movie.getDirector());
            this.console.log("Cast: " + movie.getCast());
            this.console.log("Type: " + movie.getType());
            this.console.log("Status: " + movie.getStatus());
            this.console.log(""); //TODO: can reuse logwithseperator and need director
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 3);
            switch (choice) {
                case 1:
                    ListMovieReviewController review = new ListMovieReviewController(console, this.movie);
                    review.enter();
                    break;
                case 2:
                    ListAvailableCineplexController available = new ListAvailableCineplexController(console, this.movie);
                    available.enter();
                    break;
                case 3:
                    return;
            }
        }
    }

//    public String getMovieCast(){
//        //moved to movie entity as part of the attributes
//        ArrayList<String> castList = new ArrayList<String>();
//        try{
//            ArrayList<Actor> wholeActorList = DataBase.readList(FILENAME, Actor.class);
//            for (Actor a: wholeActorList){
//                if (a.getInMovie().contains(Integer.toString(this.movieId))){
//                    castList.add(a.getName());
//                }
//            }
//        } catch(Exception e){
//        }
//        String res = String.join(",", castList);
//        return res;
//    }
}
