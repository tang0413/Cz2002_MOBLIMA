package modules.entity.movie;

import modules.data.DataBase;

import java.util.ArrayList;

/**
 * Represents a specific type of MoviePeople: Actor or Actress
 */
public class Actor extends MoviePeople {

    public Actor(ArrayList<String> paramList) {
        super(paramList);
    }

    public Boolean addInMovie(int movieId){
        this.inMovie.add(Integer.toString(movieId));
        try{
            DataBase.setData(CASTFILENAME, this);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
