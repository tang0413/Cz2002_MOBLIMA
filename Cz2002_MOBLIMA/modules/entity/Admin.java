package modules.entity;

import java.util.ArrayList;

/**
 * Represents a staff who has access to the editing system
 */
public class Admin extends BaseEntity{
    //id=2|username=admin|password=password
    /**
     * the unhashed user name of the staff
     */
    private String username;
    /**
     * the unhashed password of the staff
     */
    private String password;

    /**
     * Constructor used by DataBase to instantiate using txt file info
     * @param paramList ArrayList of String with contains the id, username and password in sequence
     */
//    public Admin(int id, String username, String password){
//        super(id);
//        this.username = username;
//        this.password = password;
//    }
    public Admin(ArrayList<String> paramList){
        super(Integer.parseInt(paramList.get(0)));
        this.username = paramList.get(1);
        this.password = paramList.get(2);
    }

    /**
     * Check if the user entered username and password matches this staff's
     * @param username the user entered username
     * @param password the user entered password
     * @return true if matches. Otherwise, false
     */
    public boolean auth(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is used in DataBase when setData/deleteData is called
     * @return the Stringlized Admin properties to store back to txt file. e.g. id=2|username=admin|password=password
     */
    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|username=" + this.username + "|password=" + this.password;
    }
}
