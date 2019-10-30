package modules.entity.movie;

import modules.entity.BaseEntity;

public class ShowStatus extends BaseEntity {
    private String status;
    public ShowStatus(int id, String status){
        super(id);
        this.status = status;
    }

    public String getStatus() { return status;}
}
