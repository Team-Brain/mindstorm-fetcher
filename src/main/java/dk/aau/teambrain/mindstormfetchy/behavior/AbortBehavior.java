package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.State;

public class AbortBehavior extends BaseBehavior {

    public AbortBehavior(BaseRobot robot) {
        super(robot);
    }

    @Override
    protected String getName() {
        return "Abort";
    }

    @Override
    public boolean takeControl() {
        return robot.getCurrentState() == State.ABORT;
    }

    @Override
    public void action() {
        super.action();
        if (robot.carryingObject) {
            robot.leaveObjectOnSide(false);
        }
        robot.setCurrentState(State.GOING_HOME);
    }
}
