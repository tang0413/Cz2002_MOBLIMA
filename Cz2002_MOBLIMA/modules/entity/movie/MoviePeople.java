package modules.entity.movie;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a people who is either a director or actor/actress
 */
public class MoviePeople extends BaseEntity {
    /**
     * The name of the person
     */
    protected String name;
    /**
     * A list of ids of the movies that he/she participated in
     */
    protected ArrayList<String> inMovie;
//    public Actor(int id, String name, ArrayList<String> inMovie) {
//        super(id);
//        this.name = name;
//        this.inMovie = inMovie;
//    }

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList  ArrayList of String with contains the id, name, and movie he/she participated in in sequence
     */
    public MoviePeople(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        String splittedInMovie[] = paramList.get(2).split(",");
        this.inMovie = new ArrayList<String>( Arrays.asList(splittedInMovie));
    }

    /**
     * @return the inMovie attribute of the MoviePerson
     */
    public ArrayList<String> getInMovie() {
        return inMovie;
    }

    /**
     * @return the name of the MoviePerson
     */
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    /**
     * This is to add a new movie to this MoviePerson's participated movie list
     * @param movieId the id of the new movie that this MoviePerson is in.
     * @return true if the data is correctly stored. false if not
     */
    public Boolean addInMovie(int movieId){
        if(!this.inMovie.contains(Integer.toString(movieId))){
            this.inMovie.add(Integer.toString(movieId));
        } else {
            return true;
        }
        try{
            DataBase.setData(this);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    /**
     * This is to remove a  movie from this MoviePerson's participated movie list
     * @param movieId the id of the new movie that this MoviePerson should not be in.
     * @return true if the data is correctly stored. false if not
     */
    public Boolean removeInMovie(int movieId){
        this.inMovie.remove(Integer.toString(movieId));
        if (this.inMovie.size() == 0){
            this.inMovie.add("0");
        }
        try{
            DataBase.setData(this);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * This is used in DataBase when setData/deleteData is called
     * @return the Stringlized properties to store back to txt file. e.g. id=3|name=John|inMovie=1,2
     */
    @Override
    public String StringlizeEntity() {
        String reinMovieString = String.join(",", this.inMovie);
        return "id=" + this.id + "|name=" + this.name + "|inMovie=" + reinMovieString;
    }
}
