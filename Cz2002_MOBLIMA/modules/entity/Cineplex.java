package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a cineplex which contains multiple cinemas
 */
public class Cineplex extends BaseEntity{
    //id=1|cineplexname=Jurong
    /**
     * The name of the cineplex
     */
    private String cineplexName;
    /**
     * The list of names of all cinemas that the cineplex has
     */
    private ArrayList<String> cinemaList;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList ArrayList of String with contains the id, cineplex name and cinema list in sequence
     */
    public Cineplex (ArrayList<String> paramList)
    {
        super(Integer.parseInt(paramList.get(0)));
        this.cineplexName = paramList.get(1);
        String splittedCinema[] = paramList.get(2).split(",");
        this.cinemaList = new ArrayList<>( Arrays.asList(splittedCinema));
    }

    /**
     * This is used in DataBase when setData/deleteData is called
     * @return the Stringlized properties to store back to txt file. e.g. id=4|cineplexname=Bishan|cinema=c1,a1
     */
    @Override
    public String StringlizeEntity() {
        String cinemaListString = String.join(",", this.cinemaList);
        return "id=" + this.id + "|cineplexname=" + this.cineplexName + "|cinema=" + cinemaListString;
    }

    /**
     * @return the id of the cineplex
     */
    public int getId() { return id; }

    /**
     * @return the string of the cineplex
     */
    public String getCineplexName() { return cineplexName; }

    /**
     * @return the list of cinemas that the cineplex contains
     */
    public ArrayList<String> getCinemaList(){
        return cinemaList;
    }

    /**
     * This is to add a new cinema to the cineplex
     * @param newCinemaName name of the cinema to be added e.g. a1
     */
    public void addCinema(String newCinemaName){
        this.cinemaList.add(newCinemaName);
    }

}
