package modules.entity;

public class Cineplex extends BaseEntity{
    private int id;
    private String cinplexName;
    public Cineplex (int id, String cinplexName)
    {
        super(id);
        this.cinplexName = cinplexName;
    }

    public int getId() { return id; }

    public String getCinplexName() { return cinplexName; }

}
