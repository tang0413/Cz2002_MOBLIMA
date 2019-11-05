package modules.entity;

import java.util.ArrayList;
import java.util.Arrays;

public class Holiday extends BaseEntity {
    private String date;
    /**
     * Instantiate the obj using id
     *
     * @param id The id passed in to assign to the obj
     */
    @Deprecated
    public Holiday(int id) {
        super(id);
    }

    public Holiday (ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.date = paramList.get(1);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Used by DataBase setData/deleteData
     *
     * @return A stringlized obj to store back to one's own txt file as a String
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|date=" + this.date;
    }
}
