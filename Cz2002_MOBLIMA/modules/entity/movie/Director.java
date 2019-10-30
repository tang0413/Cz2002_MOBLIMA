package modules.entity.movie;

import modules.entity.BaseEntity;

import java.util.ArrayList;

public class Director extends BaseEntity {
    private String name;
    private ArrayList<String> inMovie;
    public Director(int id, String name, ArrayList<String> inMovie) {
        super(id);
        this.name = name;
        this.inMovie = inMovie;
    }

    public ArrayList<String> getInMovie() {
        return inMovie;
    }

    public String getName(){
        return name;
    }


}
