package modules.entity;

public abstract class BaseEntity {
    protected static final String MOVIEFILENAME = "MovieList.txt";
    protected static final String DIRECTORFILENAME = "DirectorList.txt";
    protected static final String CASTFILENAME = "ActorList.txt";
    protected static final String REVIEWFILENAME = "ReviewList.txt";
    protected static final String MOVIEGONERFILENAME = "MoviegonerList.txt";
    protected int id;
    public BaseEntity(int id){
        this.id = id;
    }
    public abstract String StringlizeEntity(); //WARNING: include the ones in db only!!

    public int getId() {
        return id;
    }
}
