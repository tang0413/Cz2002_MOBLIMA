package modules.control;

import modules.boundary.Console;

public class UserSelecting extends BaseController{

    public UserSelecting(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    public void run() {
        this.console.log("inside UserSelecting");
    }
}
