package modules.control;
//package control;

import modules.boundary.Console;
public abstract class BaseController {
    protected Console console;
    protected String logText;
    protected String[] logMenu;

    public BaseController(Console inheritedConsole) {
        console = inheritedConsole;
    }

    public abstract void enter();
}
