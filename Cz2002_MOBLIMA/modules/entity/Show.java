package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

/**
 * Represents a show or, in another word, a movie session inside a cinema of a cineplex
 */
public class Show extends BaseEntity {
    //id=1|movieId=1|cineplexId=1|cinemaname=a|time=14:30|date=14/12/2019
    /**
     * The id of the movie which the show is going to play
     */
    private int movieId;
    /**
     * The id of the movie which the show is going to play
     */
    private int cineplexId;
    /**
     * The name of the cinema where the show is on
     */
    private String cinemaname;
    /**
     * The starting time of the show
     */
    private String time;
    /**
     * The date of the show
     */
    private String date;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList ArrayList of String with contains the id, movie id, cineplex id, cinema name, time and date in sequence
     */
    public Show(ArrayList<String> paramList)
    {
        super(Integer.parseInt(paramList.get(0)));
        this.movieId = (Integer.parseInt(paramList.get(1)));
        this.cineplexId = (Integer.parseInt(paramList.get(2)));
        this.cinemaname = paramList.get(3);
        this.time = paramList.get(4);
        this.date = paramList.get(5);
    }

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String e.g. id=6|movieId=3|cineplexId=2|cinemaname=b2|time=15:00|date=3/11/2019
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|movieId=" + this.movieId + "|cineplexId=" + this.cineplexId + "|cinemaname=" + this.cinemaname + "|time=" + this.time+ "|date=" + this.date;
    }

    /**
     * @return the id of the show
     */
    public int getId() { return id; }

    /**
     * @return the id of the shown movie
     */
    public int getMovieId() { return movieId; }

    /**
     * @return the id of the cineplex as venue
     */
    public int getCineplexId() { return cineplexId; }

    /**
     * @return the name of the cinema where the show is on
     */
    public String getCinemaname() { return cinemaname; }

    /**
     * @return the time of the show
     */
    public String getTime() { return time; }

    /**
     * @return the date of the show
     */
    public String getDate() { return date; }

    /**
     * This is to (re)assign the cinema of a show
     * @param cinemaname the name of the cinema where the show is re-scheduled to be on
     */
    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    /**
     * This is to (re)assign the cineplex of a show
     * @param cineplexId the id of the cineplex where the show is re-scheduled to be on
     */
    public void setCineplexId(int cineplexId) {
        this.cineplexId = cineplexId;
    }

    /**
     * This is to (re)assign the movie of a show
     * @param movieId the id of the movie where the show is re-scheduled to be play
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * This is to (re)assign the time of a show
     * @param time the new time when the show will be scheduled on
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * This is to (re)assign the date of a show
     * @param date the new date when the show will be scheduled on
     */
    public void setDate(String date) {
        this.date = date;
    }
}
