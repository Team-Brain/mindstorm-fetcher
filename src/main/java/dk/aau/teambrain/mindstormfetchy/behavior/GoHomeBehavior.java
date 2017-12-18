package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;

/**
 * GoHomeBehavior becomes active when robot's state changes to State.GOING_HOME.
 * <p>
 * The robot navigates to its starting position.
 * When the robot reaches the starting position and carries an object it switches
 * the state to State.CARRY_TO_USER, if not then the state is set to State.WAITING_FOR_COMMAND.
 */
public class GoHomeBehavior extends BaseBehavior {

    public static final String TAG = "GoHome";

    public GoHomeBehavior(BaseRobot robot) {
        super(robot);
    }

    public GoHomeBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        super(robot, listener);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    public boolean takeControl() {
        return robot.getCurrentState() == State.GOING_HOME;
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        robot.goToStart();
        while (!robot.pathCompleted() && !suppressed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (suppressed) {
            robot.stopNavigation();
            return;
        }

        if (robot.carryingObject) {
            robot.setCurrentState(State.CARRY_TO_USER);
        } else {
            if (robot.hasTask()) {
                robot.onTaskFinished();
            }
            robot.setCurrentState(State.WAITING_FOR_COMMAND);
        }
    }

}
