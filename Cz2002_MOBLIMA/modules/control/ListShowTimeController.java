package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.ShowTime;

import java.util.ArrayList;

public class ListShowTimeController extends BaseController{
    protected ArrayList<ShowTime> showList;
    private static final String FILENAME = "showTime.txt";
    public ListShowTimeController(Console inheritedConsole) {
        super(inheritedConsole);
        logText = "Here are the list of show time :";
        logMenu = new ArrayList<String>();
        try
        {
            showList = DataBase.readList(FILENAME, ShowTime.class);
            for(ShowTime s: showList){
                logMenu.add(m.get);
            }
        }
    }

}
