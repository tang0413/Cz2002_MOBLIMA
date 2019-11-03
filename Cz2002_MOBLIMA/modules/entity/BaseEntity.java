package modules.entity;

/**
 * This is the superclass / baseclass of all the other classes under modules.entity package
 */
public abstract class BaseEntity {

    /**
     * The name of the file which storing the needed information of Movie
     */
    protected static final String MOVIEFILENAME = "MovieList.txt";

    /**
     * The name of the file which storing the needed information of Director
     */
    protected static final String DIRECTORFILENAME = "DirectorList.txt";

    /**
     * The name of the file which storing the needed information of Actor (Cast)
     */
    protected static final String CASTFILENAME = "ActorList.txt";

    /**
     * The name of the file which storing the needed information of Review
     */
    protected static final String REVIEWFILENAME = "ReviewList.txt";

    /**
     * The name of the file which storing the needed information of Movie-goner
     */
    protected static final String MOVIEGONERFILENAME = "MoviegonerList.txt";
    protected static final String SHOWTIMEFILENAME = "ShowList.txt";
    protected static final String CINEPLEXFILENAME = "CineplexList.txt";

    /**
     * The unique id for each object by class
     */
    protected int id;

    /**
     * Instantiate the obj using id
     * @param id The id passed in to assign to the obj
     */
    public BaseEntity(int id){
        this.id = id;
    }

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String
     */
    public abstract String StringlizeEntity(); //WARNING: include the ones in db only!!

    /**
     * @return the object's id
     */
    public int getId() {
        return id;
    }
}
