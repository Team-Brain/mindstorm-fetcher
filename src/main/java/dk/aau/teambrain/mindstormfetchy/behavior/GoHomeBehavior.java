package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;

public class GoHomeBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "CarryHome";
    }

    @Override
    public boolean takeControl() {
        return (Fetchy.currentState == State.GOING_HOME) || (Fetchy.currentState == State.ABORT);
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        if (Fetchy.currentState == State.ABORT) {
            Fetchy.leaveOnTheSide();
        }
        Fetchy.goToStart();
        while (!Fetchy.navigator.pathCompleted() && !suppressed) {
            Thread.yield();
        }
        if (Fetchy.currentState != State.ABORT) {
            Fetchy.currentState = State.CARRY_TO_USER;
        } else {
            Fetchy.currentState = State.WAITING_FOR_COMMAND;
        }
    }

    @Override
    public void suppress() {
        suppressed = true;
    }


}
