package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;

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
        // TODO: Only for demo testing, in production remove this code
//        Fetchy.createDemoRequest();
    }

    @Override
    public void suppress() {
    }
}
