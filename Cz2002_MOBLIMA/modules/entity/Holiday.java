package modules.entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a day that is recognized as public holiday
 */
public class Holiday extends BaseEntity {
    /**
     * The date of the holiday
     */
    private String date;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList ArrayList of String with contains the id and date in sequence
     */
    public Holiday (ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.date = paramList.get(1);
    }

    /**
     * @return the id of the holiday
     */
    public int getId() {
        return id;
    }

    /**
     * @return the date of the holiday
     */
    public String getDate() {
        return date;
    }

    /**
     * This is to re-set a holiday to another date
     * @param date a new date which the holiday shall be reassigned to
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String e.g. id=5|date=10/11/2019
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|date=" + this.date;
    }
}
