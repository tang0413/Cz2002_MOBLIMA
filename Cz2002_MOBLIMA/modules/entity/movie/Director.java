package modules.entity.movie;

import modules.data.DataBase;

import java.util.ArrayList;

/**
 * Represents a specific type of MoviePeople: Director
 */
public class Director extends MoviePeople {

    public Director(ArrayList<String> paramList) {
        super(paramList);
    }

    public Boolean addInMovie(int movieId){
        this.inMovie.add(Integer.toString(movieId));
        try{
            DataBase.setData(DIRECTORFILENAME, this);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
