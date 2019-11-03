package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

public class Ticket extends BaseEntity {
    //id=1|movieId=1|cineplexId=1|showId=1|priceId=1|seats=E07
    private int id;
    private int movieId;
    private int cineplexId;
    private int showId;
    private int priceId;
    private String seats;
    public Ticket(ArrayList<String> paramaList)
    {
        super(Integer.parseInt(paramaList.get(0)));
        this.movieId = (Integer.parseInt(paramaList.get(1)));
        this.cineplexId = (Integer.parseInt(paramaList.get(2)));
        this.showId = (Integer.parseInt(paramaList.get(3)));
        this.priceId = (Integer.parseInt(paramaList.get(4)));
        this.seats = paramaList.get(5);
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|movieId=" + this.movieId + "|cineplexId=" + this.cineplexId + "|showId=" + this.showId + "|priceId=" + this.priceId + "|seats=" + this.seats;
    }

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
        try{
            DataBase.setData(TICKETFILENAME,this);
        }catch (Exception e){
        }
    }

    public int getMovieId() { return movieId; }

    public int getCineplexId() { return cineplexId; }

    public int getShowId() { return showId; }

    public int getPriceId() { return priceId; }

    public String getSeats() { return seats; }
}
