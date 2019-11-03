package modules.entity;

import java.util.ArrayList;

/**
 * Represents a movie-goner that has gone through the ticket booking process
 */
public class MovieGoner extends BaseEntity{
    //id=3|name=Customer3|phone=93214821|email=test3@test.com
    private String name;
    private String phone;
    private String email;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList ArrayList of String with contains the id, name, phone, and email in sequence
     */
    public MovieGoner(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        this.phone = paramList.get(2);
        this.email = paramList.get(3);
    }

    /**
     * @return the name of the movie-goner
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id of the movie-goner
     */
    public int getId() {
        return id;
    }

    /**
     * @return the email of the movie-goner
     */
    public String getEmail() {
        return email;
    }

    /**
     * This is used in DataBase when setData/deleteData is called
     * @return  e.g. id=3|name=Customer3|phone=93214821|email=test3@test.com
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|phone=" + this.phone + "|email=" + this.email;
    }


}
