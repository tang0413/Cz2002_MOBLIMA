package modules.entity;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.util.ArrayList;

public class TicketType extends BaseEntity {
    //id=1|name=Senior Citizen|code=Sen|regular=4|3d=11
    private String name;
    private String code;
    private Double regularPrice;
    private Double threeDPrice;

    public TicketType(ArrayList<String> paramaList)
    {
        super(Integer.parseInt(paramaList.get(0)));
        this.name=paramaList.get(1);
        this.code=paramaList.get(2);
        this.regularPrice=(Double.parseDouble(paramaList.get(3)));
        this.threeDPrice=(Double.parseDouble(paramaList.get(4)));
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|name=" + this.name + "|code=" + this.code+ "|regular=" + this.regularPrice + "|3d=" + this.threeDPrice;
    }

    public int getId() { return id; }

    @Deprecated
    public String getDays() {
        return code;
    }

    @Deprecated
    public Double getrPrice() {
        return regularPrice;
    }

    @Deprecated
    public Double getdPrice() {
        return threeDPrice;
    }

    public String getName() {
        return name;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getThreeDPrice() {
        return threeDPrice;
    }

    public void setThreeDPrice(Double threeDPrice) {
        this.threeDPrice = threeDPrice;
    }

    public String getCode() {
        return code;
    }
}

