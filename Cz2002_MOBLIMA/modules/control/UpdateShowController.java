package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.movie.Movie;

import java.util.ArrayList;

public class UpdateShowController extends BaseController {
    /**
     * This is to instantiate a Controller object with I/O access
     *
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UpdateShowController(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    @Deprecated
    public void enter() {
    }

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
                return;
            }
        }
    }

    private void alterShow(Show chosenShow, int actionChoice){
        switch (actionChoice){ //TODO finish
        }
    }

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
