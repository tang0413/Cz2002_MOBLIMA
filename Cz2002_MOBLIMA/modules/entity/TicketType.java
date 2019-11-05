package modules.entity;

import modules.data.DataBase;
import modules.entity.BaseEntity;

import java.util.ArrayList;

public class TicketType extends BaseEntity {
    //id=1|days=Sen|r=4|d=0
    private String days;
    private Double rPrice;
    private Double dPrice;

    public TicketType(ArrayList<String> paramaList)
    {
        super(Integer.parseInt(paramaList.get(0)));
        this.days=paramaList.get(2);
        this.rPrice=(Double.parseDouble(paramaList.get(2)));
        this.dPrice=(Double.parseDouble(paramaList.get(2)));
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|days=" + this.dPrice + "|r=" + this.rPrice+ "|d=" + this.dPrice;
    }

    public int getId() { return id; }

    @Deprecated //TODO remove
    public void setId(int id){
        this.id = id;
        try{
            DataBase.setData(this);
        }catch (Exception e){
        }
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Double getrPrice() {
        return rPrice;
    }

    public void setrPrice(Double rPrice) {
        this.rPrice = rPrice;
    }

    public Double getdPrice() {
        return dPrice;
    }

    public void setdPrice(Double dPrice) {
        this.dPrice = dPrice;
    }
}

