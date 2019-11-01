package modules.entity.movie;

import modules.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class MoviePeople extends BaseEntity {
    protected String name;
    protected ArrayList<String> inMovie;
//    public Actor(int id, String name, ArrayList<String> inMovie) {
//        super(id);
//        this.name = name;
//        this.inMovie = inMovie;
//    }

    public MoviePeople(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        String splittedInMovie[] = paramList.get(2).split(",");
        this.inMovie = new ArrayList<String>( Arrays.asList(splittedInMovie));
    }

    public ArrayList<String> getInMovie() {
        return inMovie;
    }

    public String getName() {
        return name;
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|inMovie=" + this.inMovie; //TODO: chonvertType
    }
}