package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;
import lejos.utility.Stopwatch;

/**
 * SearchBehavior becomes active when robot's state changes to State.SEARCHING.
 * <p>
 * The robot searches moves forward and searches for objects in front of it.
 * The forward movement can either by suppressed by the object being close by
 * or a time out.
 */
public class SearchBehavior extends BaseBehavior {

    public static final String TAG = "Search";

    public static final int TIMEOUT_SEARCH = 20 * 1000;

    public SearchBehavior(BaseRobot robot) {
        super(robot);
    }

    public SearchBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        super(robot, listener);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    public boolean takeControl() {
        return robot.getCurrentState() == State.SEARCHING;
    }

    public void action() {
        super.action();
        Stopwatch stopwatch = new Stopwatch();
        suppressed = false;
        robot.forward();
        while (!suppressed) {
            try {
                if (stopwatch.elapsed() > TIMEOUT_SEARCH) {
                    robot.setCurrentState(State.ABORT);
                    robot.stop();
                    suppressed = true;
                    return;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        robot.stop();
    }

}