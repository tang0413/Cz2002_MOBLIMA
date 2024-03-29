package modules.control;

import modules.boundary.ConsoleUI;
import modules.data.DataBase;
import modules.entity.movie.Actor;
import modules.entity.movie.Director;
import modules.entity.movie.Movie;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a type of controller that is able to do manipulation on Movie object. e.g. Update or create Movies
 */
public class UpdateMovieController extends BaseController {//TODO see if any way to prevent non-admin access
    /**
     * This is to instantiate a controller with Movie manipulation ability
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public UpdateMovieController(ConsoleUI inheritedConsoleUI) {
        super(inheritedConsoleUI);
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
                this.consoleUI.log(e.getMessage());
                consoleUI.logWarning("Failed to update the movie!");
                return;
            }
        }
    }

    /**
     * This is to create a new Movie object from user input and then save it to the txt file (database)
     * Inside this function, the user will asked to enter all necessary information for a Movie
     */
    private void insetNewMovie() {
         this.consoleUI.logText("Please key in the following necessary information");
         ArrayList<String> newMovieParam = new ArrayList();
         int newMovieId = DataBase.getNewId(Movie.class);
         newMovieParam.add(Integer.toString(newMovieId));
         newMovieParam.add(this.consoleUI.getStr("Movie Name"));
         newMovieParam.add(this.consoleUI.getStr("Description"));
         newMovieParam.add(this.consoleUI.getMovieStatus("Status"));
         newMovieParam.add(this.consoleUI.getStr("Movie Type"));
         newMovieParam.add(this.consoleUI.getStr("Category"));//TODO add checking
         this.consoleUI.logReminder("Please separate names by ',' with no space");
         String DirectorList = this.consoleUI.getStr("Director(s)");
         String Cast = this.consoleUI.getStr("Cast(s)");
         try{
             alterDirector(DirectorList, newMovieId);
             alterCast(Cast, newMovieId);
             Movie newMovie = new Movie(newMovieParam);
             DataBase.setData(newMovie);
             consoleUI.logSuccess();
         } catch (Exception e){
             this.consoleUI.log(e.getMessage());
         }
    }

    /**
     * This is to change a specific attribute of a pointed movie by action code
     * @param movieToChange the movie object that is going to be changed
     * @param actionChoice the action code that is chosen by the user, ranging from 1 - 7
     * @throws IOException thrown when failed to read/write file (store data)
     * @throws InterruptedException thrown when failed to sleep
     * @throws InvocationTargetException thrown when non-BaseEntity or subclass object was created
     * @throws NoSuchMethodException thrown when non-BaseEntity or subclass object was created
     * @throws InstantiationException thrown when non-BaseEntity or subclass object was created
     * @throws IllegalAccessException thrown when non-BaseEntity or subclass object was created
     */
    private void alterMovie(Movie movieToChange, int actionChoice) throws IOException, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String newValue;
        switch(actionChoice){
            case 1:
                newValue = this.consoleUI.getStr("Please enter a new Name");
                movieToChange.setName(newValue);
                break;
            case 2:
                newValue = this.consoleUI.getStr("Please enter a new type");
                movieToChange.setType(newValue);
                break;
            case 3:
                newValue = this.consoleUI.getStr("Please enter a new category (Movie Rating)");
                movieToChange.setCat(newValue);
                break;
            case 4:
                newValue = this.consoleUI.getStr("Please enter a new description");
                movieToChange.setDescription(newValue);
                break;
            case 5:
                this.consoleUI.logReminder("Please separate names by ',' with no space");
                newValue = this.consoleUI.getStr("Please enter new director(s)");
                alterDirector(newValue, movieToChange.getId());
                break;
            case 6:
                this.consoleUI.logReminder("Please separate names by ',' with no space");
                newValue = this.consoleUI.getStr("Please enter new cast");
                alterCast(newValue, movieToChange.getId());
                break;
            case 7:
                newValue = this.consoleUI.getMovieStatus("Please enter a new status");
                movieToChange.setStatus(newValue);
                break;
        }
        DataBase.setData(movieToChange);
        consoleUI.logSuccess();
    }

    /**
     * This is to add new MovieId to the inMovie attribute of the directors
     * @param DirectorList the names of the new directors for the movie, separated by comma
     * @param movieId the id of the target movie object
     * @throws IOException thrown when failed to read/write the file
     * @throws InvocationTargetException thrown when non-BaseEntity or subclass object was created
     * @throws NoSuchMethodException thrown when non-BaseEntity or subclass object was created
     * @throws InstantiationException thrown when non-BaseEntity or subclass object was created
     * @throws IllegalAccessException thrown when non-BaseEntity or subclass object was created
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
     * @throws IOException thrown when failed to read/write the file
     * @throws InvocationTargetException thrown when non-BaseEntity or subclass object was created
     * @throws NoSuchMethodException thrown when non-BaseEntity or subclass object was created
     * @throws InstantiationException thrown when non-BaseEntity or subclass object was created
     * @throws IllegalAccessException thrown when non-BaseEntity or subclass object was created
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
