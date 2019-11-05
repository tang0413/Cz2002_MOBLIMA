package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.movie.Actor;
import modules.entity.movie.Director;
import modules.entity.movie.Movie;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Represents a manipulation on Movie object. e.g. Update or create Movies
 */
public class UpdateMovieController extends BaseController {
    /**
     * This is to instantiate a controller with Movie manipulation ability
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UpdateMovieController(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    @Deprecated
    public void enter() {

    }

    /**
     * This is to enter a series of process to update or create a movie
     * @param actionChoice 0 for creating new Movie. 1 - 7 for changing a specific attribute of a Moive
     * @param movieId id of the movie that is going to be updated
     */
    public void enter(int actionChoice, int movieId) {
        while (true){
            try{
                if (actionChoice == 0){
                    insetNewMovie();
                    return;
                }
                Movie chosenMovie = (Movie) DataBase.getObjById(movieId, Movie.class);
                alterMovie(chosenMovie, actionChoice);
                return;
            } catch (Exception e){
                this.console.log(e.getMessage());
                console.logWarning("Failed to update the movie!");
                return;
            }
        }
    }

    /**
     * This is to create a new Movie object from user input and then save it to the txt file (database)
     * Inside this function, the user will asked to enter all necessary information for a Movie
     */
    private void insetNewMovie() {
         this.console.logText("Please key in the following necessary information");
         ArrayList<String> newMovieParam = new ArrayList();
         int newMovieId = DataBase.getNewId(Movie.class);
         newMovieParam.add(Integer.toString(newMovieId));
         newMovieParam.add(this.console.getStr("Movie Name"));
         newMovieParam.add(this.console.getStr("Description"));
         newMovieParam.add(this.console.getMovieStatus("Status"));
         newMovieParam.add(this.console.getStr("Movie Type"));
         newMovieParam.add(this.console.getStr("Category"));//TODO add checking
         this.console.logReminder("Please separate names by ',' with no space");
         String DirectorList = this.console.getStr("Director(s)");
         String Cast = this.console.getStr("Cast(s)");
         try{
             alterDirector(DirectorList, newMovieId);
             alterCast(Cast, newMovieId);
             Movie newMovie = new Movie(newMovieParam);
             DataBase.setData(newMovie);
             console.logSuccess();
         } catch (Exception e){
             this.console.log(e.getMessage());
         }
    }

    /**
     * This is to change a specific attribute of a pointed movie by action code
     * @param movieToChange the movie object that is going to be changed
     * @param actionChoice the action code that is chosen by the user, ranging from 1 - 7
     */
    private void alterMovie(Movie movieToChange, int actionChoice) throws IOException, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String newValue;
        switch(actionChoice){
            case 1:
                newValue = this.console.getStr("Please enter a new Name");
                movieToChange.setName(newValue);
                break;
            case 2:
                newValue = this.console.getStr("Please enter a new type");
                movieToChange.setType(newValue);
                break;
            case 3:
                newValue = this.console.getStr("Please enter a new category (Movie Rating)");
                movieToChange.setCat(newValue);
                break;
            case 4:
                newValue = this.console.getStr("Please enter a new description");
                movieToChange.setDescription(newValue);
                break;
            case 5:
                this.console.logReminder("Please separate names by ',' with no space");
                newValue = this.console.getStr("Please enter new director(s)");
                alterDirector(newValue, movieToChange.getId());
                break;
            case 6:
                this.console.logReminder("Please separate names by ',' with no space");
                newValue = this.console.getStr("Please enter new cast");
                alterCast(newValue, movieToChange.getId());
                break;
            case 7:
                newValue = this.console.getMovieStatus("Please enter a new status");
                movieToChange.setStatus(newValue);
                break;
        }
        DataBase.setData(movieToChange);
        console.logSuccess();
    }

    /**
     * This is to add new MovieId to the inMovie attribute of the directors
     * @param DirectorList the names of the new directors for the movie, separated by comma
     * @param movieId the id of the target movie object
     */
    private void alterDirector(String DirectorList, int movieId) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String splittedInMovie[] = DirectorList.split(",");
        ArrayList<String> directorNames= new ArrayList<>( Arrays.asList(splittedInMovie));
        ArrayList<Director> currentDirectorList = DataBase.readList(Director.class);
        for (Director d :currentDirectorList){
            if(directorNames.contains(d.getName())){
                d.addInMovie(movieId);
                directorNames.remove(d.getName());
            } else if ((!directorNames.contains(d.getName())) && d.getInMovie().contains(Integer.toString(movieId))){
                d.removeInMovie(movieId);
            }
        }
        if(directorNames.size() != 0){
            for (String dName: directorNames){
                ArrayList<String> newDirectorParam = new ArrayList<>();
                newDirectorParam.add(Integer.toString(DataBase.getNewId(Director.class)));
                newDirectorParam.add(dName);
                newDirectorParam.add(Integer.toString(movieId));
                Director newDirector = new Director(newDirectorParam);
                DataBase.setData(newDirector);
            }
        }
    }

    /**
     * This is to add new MovieId to the inMovie attribute of the actors. Similar to alterDirector.
     * @param Cast the names of the new cast for the movie, separated by comma
     * @param movieId the id of the target movie object
     */
    private void alterCast(String Cast, int movieId) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String splittedInMovie[] = Cast.split(","); //TODO get rid off this similar code
        ArrayList<String> castNames= new ArrayList<>( Arrays.asList(splittedInMovie));
        ArrayList<Actor> currentCastList = DataBase.readList(Actor.class);
        for (Actor a :currentCastList){
            if(castNames.contains(a.getName())){
                a.addInMovie(movieId);
                castNames.remove(a.getName());
            } else if((!castNames.contains(a.getName())) && a.getInMovie().contains(Integer.toString(movieId))){
                a.removeInMovie(movieId);
            }
        }
        if(castNames.size() != 0){
            for (String aName: castNames){
                ArrayList<String> newActorParam = new ArrayList<>();
                newActorParam.add(Integer.toString(DataBase.getNewId(Actor.class)));
                newActorParam.add(aName);
                newActorParam.add(Integer.toString(movieId));
                Actor newActor = new Actor(newActorParam);
                DataBase.setData(newActor);
            }
        }
    }
}
