package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;
import java.util.Arrays;

public class Cineplex extends BaseEntity{
    //id=1|cineplexname=Jurong
    private String cineplexName;
    private ArrayList<String> cinemaList;
    public Cineplex (ArrayList<String> paramList)
    {
        super(Integer.parseInt(paramList.get(0)));
        this.cineplexName = paramList.get(1);
        String splittedCinema[] = paramList.get(2).split(",");
        this.cinemaList = new ArrayList<>( Arrays.asList(splittedCinema));
    }

    @Override
    public String StringlizeEntity() {
        String cinemaListString = String.join(",", this.cinemaList);
        return "id=" + this.id + "|cineplexname=" + this.cineplexName + "|cinema=" + cinemaListString;
    }

    public int getId() { return id; }

    public String getCineplexName() { return cineplexName; }

    public ArrayList<String> getCinemaList(){
        return cinemaList;
    }

}
