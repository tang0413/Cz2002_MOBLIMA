package modules.control;

import modules.boundary.Console;
public abstract class BaseController {
    protected Console console;
    public BaseController(Console inheritedConsole) {
        console = inheritedConsole;
    }
    public abstract void run();
}
