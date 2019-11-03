package modules.control;

import modules.boundary.Console;

public class UpdateShowController extends BaseController {
    /**
     * This is to instantiate a Controller object with I/O access
     *
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UpdateShowController(Console inheritedConsole) {
        super(inheritedConsole);
    }

    @Override
    @Deprecated
    public void enter() {

    }
}
