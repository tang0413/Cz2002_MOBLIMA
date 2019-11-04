package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

public class Show extends BaseEntity {
    //id=1|movieId=1|cineplexId=1|cinemaname=a|time=14:30|date=14/12/2019
    private int movieId;
    private int cineplexId;
    private String cinemaname;
    private String time;
    private String date;
    public Show(ArrayList<String> paramList)
    {
        super(Integer.parseInt(paramList.get(0)));
        this.movieId = (Integer.parseInt(paramList.get(1)));
        this.cineplexId = (Integer.parseInt(paramList.get(2)));
        this.cinemaname = paramList.get(3);
        this.time = paramList.get(4);
        this.date = paramList.get(5);
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|movieId=" + this.movieId + "|cineplexId=" + this.cineplexId + "|cinemaname=" + this.cinemaname + "|time=" + this.time+ "|date=" + this.date;
    }

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
        try{
            DataBase.readList(Show.class);
        }catch (Exception e){
        }
    }

    public int getMovieId() { return movieId; }

    public int getCineplexId() { return cineplexId; }

    public String getCinemaname() { return cinemaname; }

    public String getTime() { return time; }

    public String getDate() { return date; }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public void setCineplexId(int cineplexId) {
        this.cineplexId = cineplexId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
