package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Cineplex;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ListCineplexController extends BaseController {
    protected ArrayList<Cineplex> cineplexList;
    private static final String FILENAME = "CineplexList.txt";
    public ListCineplexController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are the Cineplex";
        logMenu = new ArrayList<String>();
        try {
            cineplexList = DataBase.readCineList(FILENAME);
            for (Cineplex c : cineplexList) {
                logMenu.add(c.getCineplexName());
            }
        } catch (Exception e) {
        }
        logMenu.add("Back");
    }
    @Override
    public void enter() {
        while(true)
        {
            this.console.logText(logText);
            this.console.logMenu(logMenu);
            int choice = this.console.getInt("Enter index to see cineplex details",1,cineplexList.size()+1);
            if (choice == cineplexList.size()+1){
                return;
            } else {
                ListCineplexInfoController cineplexInfo = new ListCineplexInfoController(console, choice, cineplexList.get(choice - 1));
                cineplexInfo.enter();
                //display info controller (choice)
            }

        }
    }
}
