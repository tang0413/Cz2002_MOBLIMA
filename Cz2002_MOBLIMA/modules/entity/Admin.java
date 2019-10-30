package modules.entity;

public class Admin extends BaseEntity{
    private String username;
    private String password;
    public Admin(int id, String username, String password){
        super(id);
        this.username = username;
        this.password = password;
    }
    public boolean auth(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }
}
