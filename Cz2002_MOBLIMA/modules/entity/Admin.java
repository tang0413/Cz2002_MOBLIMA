package modules.entity;

import java.util.ArrayList;

public class Admin extends BaseEntity{
    private String username;
    private String password;
    public Admin(int id, String username, String password){
        super(id);
        this.username = username;
        this.password = password;
    }
    public Admin(ArrayList<String> paramList){
        super(Integer.parseInt(paramList.get(0)));
        this.username = paramList.get(1);
        this.password = paramList.get(2);
    }
    public boolean auth(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }
}
