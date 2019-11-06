package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

/**
 * Represents a manipulation on Show object. e.g. Update, create, or delete Show
 */
public class UpdateShowController extends BaseController {
    /**
     * This is to instantiate a controller with Show manipulation ability
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UpdateShowController(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    @Deprecated
    public void enter() {
    }

    /**
     * This is to enter a series of process to update, create or delete a Show
     * @param actionChoice 0 for creating new Show. 1 - 5 for changing a specific attribute of a existing Show. 6 for deleting the specified Show
     * @param showId id of the Show that is going to be updated
     */
    public void enter(int actionChoice, int showId){
        while (true){
            try{
                if (actionChoice == 0){
                    insetNewShow();
                    return;
                }
                if (actionChoice == 6){
                    deleteShow(showId);
                    return;
                }
                Show chosenShow = (Show)DataBase.getObjById(showId, Show.class);
                alterShow(chosenShow, actionChoice);
                return;
            } catch (Exception e){
                this.console.log(e.getMessage());
                console.logWarning("Failed to update the show!");
                return;
            }
        }
    }

    /**
     * This is to change a specific attribute of a pointed Shown by action code
     * @param chosenShow the Show object that is going to be changed
     * @param actionChoice the action code that is chosen by the user, ranging from 1 - 5
     */
    private void alterShow(Show chosenShow, int actionChoice){//TODO can add some more descriptions
        try{
            switch (actionChoice){
                case 1:
                    chosenShow.setMovieId(this.console.getMovieId("Please enter a new movie ID"));
                    break;
                case 2:
                    int newCineplexId = this.console.getCineplexId("Please enter a new Cinplex ID");
                    Cineplex newCineplex = (Cineplex)DataBase.getObjById(newCineplexId, Cineplex.class);
                    console.logReminder("Please choose from the following cinemas" + newCineplex.getCinemaList());
                    String newCinemaName = this.console.getStr("Cinema Name", newCineplex.getCinemaList());
                    chosenShow.setCineplexId(newCineplexId);
                    chosenShow.setCinemaname(newCinemaName);
                    break;
                case 3:
                    Cineplex chosenCineplex = (Cineplex)DataBase.getObjById(chosenShow.getCineplexId(), Cineplex.class);
                    console.logReminder("Please choose from the following cinemas" + chosenCineplex.getCinemaList());
                    chosenShow.setCinemaname(this.console.getStr("Cinema Name", chosenCineplex.getCinemaList()));
                    break;
                case 4:
                    chosenShow.setTime(this.console.getTime());
                    break;
                case 5:
                    chosenShow.setTime(this.console.getDate());
                    break;
            }
            DataBase.setData(chosenShow);
            console.logSuccess();
        } catch (Exception e){
            console.logWarning("Failed to update!");
        }

    }

    /**
     * This is to create a new Show object from user input and then save it to the txt file (database)
     * Inside this method, the user will asked to enter all necessary information for a Show
     */
    private void insetNewShow() {
        try{
            //id=5|movieId=1|cineplexId=1|cinemaname=b|time=17:30|date=11/12/2019
            this.console.logText("Please key in the following necessary information");
            ArrayList<String> newShowParam = new ArrayList();
            newShowParam.add(Integer.toString(DataBase.getNewId(Show.class)));
            newShowParam.add(Integer.toString(this.console.getMovieId("Movie ID")));
            int cineplexId = this.console.getCineplexId("Cineplex ID");
            newShowParam.add(Integer.toString(cineplexId));
            Cineplex chosenCineplex = (Cineplex)DataBase.getObjById(cineplexId, Cineplex.class);
            console.logReminder("Please choose from the following cinemas" + chosenCineplex.getCinemaList());
            newShowParam.add(this.console.getStr("Cinema Name", chosenCineplex.getCinemaList()));
            newShowParam.add(this.console.getTime()); //TODO validate if there is a clash
            newShowParam.add(this.console.getDate()); //TODO validate if is earlier than now
            Show newShow = new Show(newShowParam);
            DataBase.setData(newShow);
            console.logSuccess();
        } catch (Exception e){
            console.logWarning("Failed!");
            return;
        }
    }

    /**
     * This is to deleted a show, specified by showIdm, from the txt file (databse)
     * @param showId the id of the Show to be deleted
     */
    private void deleteShow(int showId){
        try{
            Show showToDelete = (Show)DataBase.getObjById(showId, Show.class);
            DataBase.deleteData(showToDelete);
            console.logSuccess();
        } catch (Exception e){
            console.logWarning("Failed!");
            return;
        }
    }
}
