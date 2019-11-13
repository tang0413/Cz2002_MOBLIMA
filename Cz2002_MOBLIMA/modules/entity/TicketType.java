package modules.entity;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.util.ArrayList;

/**
 * Represents a type of ticket which ticket prices depend on
 */
public class TicketType extends BaseEntity {
    //id=1|name=Senior Citizen|code=Sen|regular=4|3d=11
    /**
     * The human-readable name of a ticket type
     */
    private String name;
    /**
     * The unique three-character code of a ticket type
     */
    private String code;
    /**
     * The price of a regular movie (non-3D) for this ticket type
     */
    private Double regularPrice;
    /**
     * The price of a 3D movie for this ticket type
     */
    private Double threeDPrice;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramaList ArrayList of String with contains the id, type name, code, regular price and 3D movie price in sequence
     */
    public TicketType(ArrayList<String> paramaList)
    {
        super(Integer.parseInt(paramaList.get(0)));
        this.name=paramaList.get(1);
        this.code=paramaList.get(2);
        this.regularPrice=(Double.parseDouble(paramaList.get(3)));
        this.threeDPrice=(Double.parseDouble(paramaList.get(4)));
    }

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String e.g. id=9|name=Saturday|code=Sat|regular=11|3d=15
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|code=" + this.code+ "|regular=" + this.regularPrice + "|3d=" + this.threeDPrice;
    }

    /**
     * @return id of the ticket type
     */
    public int getId() { return id; }

    /**
     * @return name of the ticket type
     */
    public String getName() {
        return name;
    }

    /**
     * @return regular price of the ticket type
     */
    public Double getRegularPrice() {
        return regularPrice;
    }

    /**
     * This is to (re)set the price for regular movie of this ticket type
     * @param regularPrice the new price for regular movie to be set
     */
    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    /**
     * @return 3D movie price of the ticket type
     */
    public Double getThreeDPrice() {
        return threeDPrice;
    }

    /**
     * This is to (re)set the price for 3D movie of this ticket type
     * @param threeDPrice the new price for 3D movie to be set
     */
    public void setThreeDPrice(Double threeDPrice) {
        this.threeDPrice = threeDPrice;
    }

    /**
     * @return code of the ticket type
     */
    public String getCode() {
        return code;
    }
}

