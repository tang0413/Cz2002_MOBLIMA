package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public abstract class BaseController {
    /**
     * The name of the file which storing the needed information of Movie
     */
    protected static final String MOVIEFILENAME = "MovieList.txt";
    /**
     * The name of the file which storing the needed information of Movie-goner
     */
    protected static final String MOVIEGONERFILENAME = "MoviegonerList.txt";
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
     * The name of the file which storing the needed information of Cineplex
     */
    protected static final String CINEPLEXFILENAME = "CineplexList.txt";
    /**
     * The name of the file which storing the needed information of Admin
     */
    protected static final String ADMINFILENAME = "AdminList.txt";
    /**
     * A instance of Console that is passed down between controllers to do I/O
     */
    protected Console console;
    /**
     * The text that is to be shown as title to indicate different stages
     */
    protected String logText;
    /**
     * A list of action options for the users to choose from.
     */
    protected ArrayList<String> logMenu;

    /**
     * This is to instantiate a Controller object with I/O access
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public BaseController(Console inheritedConsole) {
        console = inheritedConsole;
    }

    /**
     * This is the basic method to enter a serious of process
     */
    public abstract void enter(); //TODO to update real data without restart, turn void into all possible changed objs. Too troublesome
}
