package modules.control;

import modules.boundary.Console;
import modules.entity.Cineplex;

import java.util.ArrayList;

public class ListCinplexInfoController extends BaseController {
    private int cineplexId;
    private Cineplex cineplex;
    public ListCinplexInfoController(Console inheritedConsole, int cineplexId, Cineplex cineplex) {
        super(inheritedConsole);
        this.cineplexId = cineplexId;
        this.cineplex = cineplex;
        logMenu = new ArrayList<String>();
        logMenu.add("Check Movie Timing");
        logMenu.add("Back");
    }

    @Override
    public void enter() {
        this.console.logText("This is the basic information of " + cineplex.getCinplexName());
        this.console.log("Cineplex Name:" + cineplex.getCinplexName());
        this.console.log("");
        this.console.logMenu(logMenu);
        int choice = this.console.getInt("Enter index to proceed",1,2);
        if (choice == 2)
            return;
    }
}
