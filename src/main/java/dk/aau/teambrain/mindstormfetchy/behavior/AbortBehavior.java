package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;

public class AbortBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "Abort";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.getCurrentState() == State.ABORT;
    }

    @Override
    public void action() {
        super.action();
        if (Fetchy.carryingObject) {
            Fetchy.leaveOnTheSide(false);
        }
        Fetchy.setCurrentState(State.GOING_HOME);
    }
}
