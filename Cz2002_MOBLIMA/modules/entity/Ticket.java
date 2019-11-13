package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

/**
 * Represents a ticket to a show (for one seat)
 */
public class Ticket extends BaseEntity {
    //id=1|movieId=1|cineplexId=1|showId=1|priceId=1|seats=E07
    /**
     * The id of the movie which the show is going to play
     */
    private int movieId;
    /**
     * The id of the movie which the show is going to play
     */
    private int cineplexId;
    /**
     * The id of the show
     */
    private int showId;
    /**
     * The id of the type ticket, which ticket price depends on
     */
    private int priceId;
    /**
     * The seat number of the ticket
     */
    private String seats; //TODO not seats, but seat!!
    /**
     * The id of the customer who bought the ticket
     */
    private int custId;
    /**
     * The transaction ID of the payment when generating the ticket
     */
    private String tId;// TODO use full name!

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramaList ArrayList of String with contains the id, movie id, cineplex id, show id, price id (ticket type), seat number, customer number and transaction id in sequence
     */
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

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String e.g. id=14|movieId=1|cineplexId=1|showId=5|tickettype=1|seats=G01|customerId=6|tid=b1201911061856
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|movieId=" + this.movieId + "|cineplexId=" + this.cineplexId + "|showId=" + this.showId + "|tickettype=" + this.priceId + "|seats=" + this.seats + "|customerId=" + this.custId + "|tid=" + this.tId;
    }

    /**
     * @return the id of the ticket
     */
    public int getId() { return id; }

    /**
     * @return the movie id of the ticket
     */
    public int getMovieId() { return movieId; }

    /**
     * This is to (re)assign the movie of a show
     * @param newMovieId id of the new movie
     */
    @Deprecated
    public void setMovieId(int newMovieId) { this.movieId = newMovieId; }

    /**
     * @return the cineplex id of the ticket
     */
    public int getCineplexId() { return cineplexId; }

    /**
     * This is to (re)assign the cinplex
     * @param newCineplexId id of the new cineplex
     */
    @Deprecated
    public void setCineplexId(int newCineplexId) { this.cineplexId = newCineplexId; }

    /**
     * @return the show id of the ticket
     */
    public int getShowId() { return showId; }

    @Deprecated
    public void setShowId(int newShowId) { this.showId = newShowId; }

    /**
     * @return the id of the ticket type
     */
    public int getPriceId() { return priceId; }

    @Deprecated
    public void setPriceId(int newPriceId) { this.priceId = newPriceId; }

    /**
     * @return the seat of the ticket
     */
    public String getSeats() { return seats; }

    @Deprecated
    public void setSeats(String newSeats) { this.seats = newSeats; }

    /**
     * @return the customer id who booked the ticket
     */
    public int getCustId() { return custId; }

    @Deprecated
    public void setCustId(int newCustId) { this.custId = newCustId; }

    /**
     * @return the transaction id of the ticket
     */
    public String gettId() { return tId; }

    @Deprecated
    public void settId(String newtId) { this.tId = newtId; }
}
