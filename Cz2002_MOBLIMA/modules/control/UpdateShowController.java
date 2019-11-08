package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;

import java.util.ArrayList;

/**
 * Represents a type of controller that is able to do manipulation on Show object. e.g. Update, create, or delete Show
 */
public class UpdateShowController extends BaseController {
    /**
     * This is to instantiate a controller with Show manipulation ability
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public UpdateShowController(ConsoleUI inheritedConsoleUI) {
        super(inheritedConsoleUI);
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
                this.consoleUI.log(e.getMessage());
                consoleUI.logWarning("Failed to update the show!");
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
                    chosenShow.setMovieId(this.consoleUI.getMovieId("Please enter a new movie ID"));
                    break;
                case 2:
                    int newCineplexId = this.consoleUI.getCineplexId("Please enter a new Cinplex ID");
                    Cineplex newCineplex = (Cineplex)DataBase.getObjById(newCineplexId, Cineplex.class);
                    consoleUI.logReminder("Please choose from the following cinemas" + newCineplex.getCinemaList());
                    String newCinemaName = this.consoleUI.getStr("Cinema Name", newCineplex.getCinemaList());
                    chosenShow.setCineplexId(newCineplexId);
                    chosenShow.setCinemaname(newCinemaName);
                    break;
                case 3:
                    Cineplex chosenCineplex = (Cineplex)DataBase.getObjById(chosenShow.getCineplexId(), Cineplex.class);
                    consoleUI.logReminder("Please choose from the following cinemas" + chosenCineplex.getCinemaList());
                    chosenShow.setCinemaname(this.consoleUI.getStr("Cinema Name", chosenCineplex.getCinemaList()));
                    break;
                case 4:
                    chosenShow.setTime(this.consoleUI.getTime());
                    break;
                case 5:
                    chosenShow.setDate(this.consoleUI.getDate());
                    break;
            }
            DataBase.setData(chosenShow);
            consoleUI.logSuccess();
        } catch (Exception e){
            consoleUI.logWarning("Failed to update!");
        }

    }

    /**
     * This is to create a new Show object from user input and then save it to the txt file (database)
     * Inside this method, the user will asked to enter all necessary information for a Show
     */
    private void insetNewShow() {
        try{
            //id=5|movieId=1|cineplexId=1|cinemaname=b|time=17:30|date=11/12/2019
            this.consoleUI.logText("Please key in the following necessary information");
            ArrayList<String> newShowParam = new ArrayList();
            newShowParam.add(Integer.toString(DataBase.getNewId(Show.class)));
            newShowParam.add(Integer.toString(this.consoleUI.getMovieId("Movie ID")));
            int cineplexId = this.consoleUI.getCineplexId("Cineplex ID");
            newShowParam.add(Integer.toString(cineplexId));
            Cineplex chosenCineplex = (Cineplex)DataBase.getObjById(cineplexId, Cineplex.class);
            consoleUI.logReminder("Please choose from the following cinemas" + chosenCineplex.getCinemaList());
            newShowParam.add(this.consoleUI.getStr("Cinema Name", chosenCineplex.getCinemaList()));
            newShowParam.add(this.consoleUI.getTime()); //TODO validate if there is a clash
            newShowParam.add(this.consoleUI.getDate()); //TODO validate if is earlier than now
            Show newShow = new Show(newShowParam);
            DataBase.setData(newShow);
            consoleUI.logSuccess();
        } catch (Exception e){
            consoleUI.logWarning("Failed!");
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
            consoleUI.logSuccess();
        } catch (Exception e){
            consoleUI.logWarning("Failed!");
            return;
        }
    }
}
