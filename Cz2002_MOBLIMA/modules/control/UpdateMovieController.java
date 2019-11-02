package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class UpdateMovieController extends BaseController { //TODO to be reused by passing index from ListMovieInfoController
    public UpdateMovieController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Please choose the attribute you want to edit";
        logMenu = new ArrayList<String>();
        logMenu.add("Movie Name");
        logMenu.add("Rating");
        logMenu.add("Type");
        logMenu.add("Description");
        logMenu.add("Director");
        logMenu.add("Cast");
        logMenu.add("Type");
        logMenu.add("Status");
        logMenu.add("Back");
    }

    @Override
    public void enter() {

    }

    public void enter(int index) {
        while (true){
            try{
                ArrayList<Movie> movieList = DataBase.readList(MOVIEFILENAME, Movie.class);
                Movie chosenMovie = movieList.get(index);
                int choice = this.console.getInt("Enter index to proceed", 1, 9);
                switch (choice) {
                    case 1:
                        break; //TODO: call method inside. no need to create new controller
                    case 9:
                        return;
                }
            } catch (Exception e){
            }
        }
    }
}
