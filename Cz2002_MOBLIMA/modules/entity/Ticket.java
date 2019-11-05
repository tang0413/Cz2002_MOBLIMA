package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

public class Ticket extends BaseEntity {
    //id=1|movieId=1|cineplexId=1|showId=1|priceId=1|seats=E07
    private int movieId;
    private int cineplexId;
    private int showId;
    private int priceId;
    private String seats; //TODO not seats, but seat!!
    private int custId;
    private String tId;// TODO use full name!
    public Ticket(ArrayList<String> paramaList)
    {
        super(Integer.parseInt(paramaList.get(0)));
        this.movieId = (Integer.parseInt(paramaList.get(1)));
        this.cineplexId = (Integer.parseInt(paramaList.get(2)));
        this.showId = (Integer.parseInt(paramaList.get(3)));
        this.priceId = (Integer.parseInt(paramaList.get(4)));
        this.seats = paramaList.get(5);
        this.custId = (Integer.parseInt(paramaList.get(6)));
        this.tId = paramaList.get(7);
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|movieId=" + this.movieId + "|cineplexId=" + this.cineplexId + "|showId=" + this.showId + "|tickettype=" + this.priceId + "|seats=" + this.seats + "|customerId=" + this.custId + "|tid=" + this.tId;
    }

    public int getId() { return id; }

    @Deprecated
    public void setId(int id){
        this.id = id;
        try{
            DataBase.setData(this);
        }catch (Exception e){
        }
    }

    public int getMovieId() { return movieId; }

    public void setMovieId(int newMovieId) { this.movieId = newMovieId; }

    public int getCineplexId() { return cineplexId; }

    public void setCineplexId(int newCineplexId) { this.cineplexId = newCineplexId; }

    public int getShowId() { return showId; }

    public void setShowId(int newShowId) { this.showId = newShowId; }

    public int getPriceId() { return priceId; }

    public void setPriceId(int newPriceId) { this.priceId = newPriceId; }

    public String getSeats() { return seats; }

    public void setSeats(String newSeats) { this.seats = newSeats; }

    public int getCustId() { return custId; }

    public void setCustId(int newCustId) { this.custId = newCustId; }

    public String gettId() { return tId; }

    public void settId(String newtId) { this.tId = newtId; }
}
