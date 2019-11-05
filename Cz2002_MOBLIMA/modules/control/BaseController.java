package modules.control;

import modules.boundary.Console;

import java.util.ArrayList;

/**
 * This is the superclass of all classes under controller folder
 */
public abstract class BaseController {
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
    public abstract void enter();
}
