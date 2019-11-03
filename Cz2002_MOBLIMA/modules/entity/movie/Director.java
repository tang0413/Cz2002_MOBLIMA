package modules.entity.movie;

import modules.data.DataBase;

import java.util.ArrayList;

public class Director extends MoviePeople {

    public Director(ArrayList<String> paramList) {
        super(paramList);
    }
    private static final String DIRECTORFILENAME = "DirectorList.txt";


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
