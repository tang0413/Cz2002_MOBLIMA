package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

public abstract class BaseController {
    protected static final String MOVIEFILENAME = "MovieList.txt";
    protected static final String MOVIEGONERFILENAME = "MoviegonerList.txt";
    protected static final String DIRECTORFILENAME = "DirectorList.txt";
    protected static final String CASTFILENAME = "ActorList.txt";
    protected static final String REVIEWFILENAME = "ReviewList.txt";
    protected static final String CINEPLEXFILENAME = "CineplexList.txt";
    protected static final String ADMINFILENAME = "AdminList.txt";
    protected static final String SHOWTIMEFILENAME = "ShowList.txt";
    protected Console console;
    protected String logText;
    protected ArrayList<String> logMenu;

    public BaseController(Console inheritedConsole) {
        console = inheritedConsole;
    }

    public abstract void enter(); //TODO to update real data without restart, turn void into all possible changed objs. Too troublesome
}
