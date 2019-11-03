package modules.entity;

import modules.data.DataBase;

import java.util.ArrayList;

public class Cineplex extends BaseEntity{
    //id=1|cineplexname=Jurong
    private int id;
    private String cineplexName;
    public Cineplex (ArrayList<String> paramList)
    {
        super(Integer.parseInt(paramList.get(0)));
        this.cineplexName = paramList.get(1);
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|cineplexname=" + this.cineplexName;
    }

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
        try{
            DataBase.setData(CINEPLEXFILENAME,this);
        }catch (Exception e){
        }
    }

    public String getCineplexName() { return cineplexName; }

}
