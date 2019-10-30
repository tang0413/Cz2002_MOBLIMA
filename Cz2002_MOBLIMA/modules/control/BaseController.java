package modules.control;
//package control;

import modules.boundary.Console;

import java.util.ArrayList;

public abstract class BaseController {
    protected Console console;
    protected String logText;
    protected ArrayList<String> logMenu;

    public BaseController(Console inheritedConsole) {
        console = inheritedConsole;
    }

    public abstract void enter();
}
