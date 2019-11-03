package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
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

//    public void enter(int actionChoice, int showId){
//        while (true){
//            try{
//                if (actionChoice == 0){
//                    insetNewShow();
//                    return;
//                }
//                Show chosenShow = DataBase.getShowById(showId);
//                alterShow(chosenShow, actionChoice);
//                return;
//            } catch (Exception e){
//                this.console.log(e.getMessage());
//                return;
//            }
//        }
//    }
//
//    private void insetNewShow() {
//        //id=5|movieId=1|cineplexId=1|cinemaname=b|time=17:30|date=11/12/2019
//        this.console.logText("Please key in the following necessary information");
//        ArrayList<String> newShowParam = new ArrayList();
//        int newShowId = DataBase.getNewId(Show.class);
//        newShowParam.add(Integer.toString(newShowId));
//        newShowParam.add(Integer.toString(this.console.getMovieId("Movie ID")));
//        newShowParam.add(this.console.getStr(""));
//        newShowParam.add(this.console.getStr("Description"));
//        newShowParam.add(this.console.getMovieType("Status"));
//        newShowParam.add(this.console.getStr("Movie Type"));
//        newShowParam.add(this.console.getStr("Category"));//TODO add checking
//        this.console.logReminder("Please separate names by ',' with no space");
//        String DirectorList = this.console.getStr("Director(s)");
//        String Cast = this.console.getStr("Cast(s)");
//        try{
//            alterDirector(DirectorList, newMovieId);
//            alterCast(Cast, newMovieId);
//            Movie newMovie = new Movie(newMovieParam);
//            DataBase.setData(MOVIEFILENAME, newMovie);
//            autoReturn();
//        } catch (Exception e){
//            this.console.log(e.getMessage());
//        }
//    }
//
//    private void getValidMovieId()
}
