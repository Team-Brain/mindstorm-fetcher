package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class CarryToUserBehavior extends BaseBehavior {

    public static final int TIMEOUT_BEACON_SIGNAL = 5 * 1000;
    public static final String TAG = "CarryToUser";

    public CarryToUserBehavior(BaseRobot robot) {
        super(robot);
    }

    public CarryToUserBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        super(robot, listener);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    public boolean takeControl() {
        return robot.getCurrentState() == State.CARRY_TO_USER;
    }

    public void action() {
        super.action();
        suppressed = false;
        navigateToBeacon();
        if (!suppressed) {
            robot.turn(180);
            robot.letGo();
            robot.travel(-100);
            robot.stop();
            robot.setCurrentState(State.GOING_HOME);
        }
    }

    private void navigateToBeacon() {
        float direction = 0;
        // Wait for signal
        Stopwatch stopwatch = new Stopwatch();
        while (direction == 0 && !suppressed) {
            direction = robot.getSeekerDirection();
            Log.d("Direction: " + direction);
            Delay.msDelay(200);
            if (stopwatch.elapsed() > TIMEOUT_BEACON_SIGNAL) {
                robot.setCurrentState(State.ABORT);
                suppressed = true;
                return;
            }
        }

        if (suppressed) {
            return;
        }

        robot.setAngularSpeed(20);

        // Turn around
        if (direction > 1) {
            robot.turn(90, true);
        } else if (direction < -1) {
            robot.turn(-90, true);
        }

        while (Math.abs(direction) > 1) {
            direction = robot.getSeekerDirection();
        }

        robot.stop();
        robot.setAngularSpeed(35);

        if (!suppressed) {
            robot.turn(direction);
            robot.backward();
        }

        float distance = Integer.MAX_VALUE;
        while (distance > 5 && !suppressed) {
            distance = robot.getSeekerDistance();
        }

        robot.stop();
    }
}
