package modules.entity;

public class Cineplex extends BaseEntity{
    //id=1|cineplexname=Jurong
    private int id;
    private String cineplexName;
    public Cineplex (int id, String cineplexName)
    {
        super(id);
        this.cineplexName = cineplexName;
    }

    @Override
    public String StringlizeEntity() {
        return "id=" + this.id + "|cineplexname=" + this.cineplexName;
    }

    public int getId() { return id; }

    public String getCineplexName() { return cineplexName; }

}
