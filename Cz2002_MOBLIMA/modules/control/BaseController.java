package modules.control;

import modules.boundary.ConsoleUI;

import java.util.ArrayList;

/**
 * Generally represents a controller that is able to perform a specific task
 * This is the superclass of all classes under controller folder
 */
public abstract class BaseController {
    /**
     * A instance of ConsoleUI that is passed down between controllers to do I/O
     */
    protected ConsoleUI consoleUI;

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
     * @param inheritedConsoleUI the ConsoleUI instance passed down from the previous controller
     */
    public BaseController(ConsoleUI inheritedConsoleUI) {
        consoleUI = inheritedConsoleUI;
    }
}
