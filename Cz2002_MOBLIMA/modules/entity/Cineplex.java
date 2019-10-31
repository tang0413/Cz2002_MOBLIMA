package modules.entity;

public class Cineplex extends BaseEntity{
    private int id;
    private String cineplexName;
    public Cineplex (int id, String cineplexName)
    {
        super(id);
        this.cineplexName = cineplexName;
    }

    public int getId() { return id; }

    public String getCineplexName() { return cineplexName; }

}
