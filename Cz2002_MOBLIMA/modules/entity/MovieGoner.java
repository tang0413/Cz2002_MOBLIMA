package modules.entity;

import java.util.ArrayList;

public class MovieGoner extends BaseEntity{
    //id=3|name=Customer3|phone=93214821|email=test3@test.com
    private String name;
    private String phone;
    private String email;

    public MovieGoner(ArrayList<String> paramList) {
        super(Integer.parseInt(paramList.get(0)));
        this.name = paramList.get(1);
        this.phone = paramList.get(2);
        this.email = paramList.get(3);
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|phone=" + this.phone + "|email=" + this.email;
    }


}
