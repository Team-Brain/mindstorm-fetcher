package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.Main;

public class WaitForCommandBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "WaitForCommand";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.requestQueue.isEmpty();
    }

    @Override
    public void action() {
        super.action();
        if (Main.DEBUG) {
            Fetchy.createDemoRequest();
        }
    }

    @Override
    public void suppress() {
    }
}
