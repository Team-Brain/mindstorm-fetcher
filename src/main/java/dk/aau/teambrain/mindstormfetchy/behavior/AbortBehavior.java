package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;

/**
 * AbortBehavior becomes active when robot's state changes to State.ABORT.
 * <p>
 * If the robot carries an object, it leaves it on a side and returns home
 * by switching the state to State.GOING_HOME.
 */
public class AbortBehavior extends BaseBehavior {

    public static final String TAG = "Abort";

    public AbortBehavior(BaseRobot robot) {
        super(robot);
    }

    public AbortBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        super(robot, listener);
    }

    @Override
    protected String getTag() {
        return TAG;
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
