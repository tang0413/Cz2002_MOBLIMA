package modules.control;

import modules.boundary.Console;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class ListAvailableCineplexController extends BaseController{
    private Movie movie;
    private Boolean valid;
    public ListAvailableCineplexController(Console inheritedConsole, Movie mv) {
        super(inheritedConsole);
        this.movie = mv;
        this.valid = mv.getStatus().equals("Preview") || mv.getStatus().equals("Now Showing");
        if (this.valid) {
            this.logText = "Movie: " + mv.getName() + " is available in the following cineplex(es)";
//            this.logMenu = checkAvailableCineplex();
        } else {
            this.logText = "Movie: " + mv.getName() + " is currently unavailable!";
        }

    }

    @Override
    public void enter() {
        while (true){
            if (this.valid){
                this.console.logText(logText);
            } else {
                this.console.logWarning(logText);
                return;
            }
//            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to proceed", 1, 1);
            if (choice == 1){
                return;
            }
        }
    }

//    private ArrayList<String> checkAvailableCineplex(){
//
//    }
}
