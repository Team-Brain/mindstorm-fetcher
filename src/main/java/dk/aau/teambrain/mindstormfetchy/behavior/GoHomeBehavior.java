package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;

public class GoHomeBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "GoHome";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.currentState == State.GOING_HOME;
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        Fetchy.goToStart();
        while (!Fetchy.pathCompleted() && !suppressed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Fetchy.carryingObject) {
            Fetchy.currentState = State.CARRY_TO_USER;
        } else {
            if (Fetchy.hasTask()) {
                Fetchy.finishRequest();
            }
            Fetchy.currentState = State.WAITING_FOR_COMMAND;
        }
    }

    @Override
    public void suppress() {
        suppressed = true;
    }


}
