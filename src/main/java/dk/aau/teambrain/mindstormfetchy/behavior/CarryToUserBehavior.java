package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class CarryToUserBehavior extends BaseBehavior {

    private static final int TIMEOUT_BEACON_SIGNAL = 7 * 1000;

    public CarryToUserBehavior(BaseRobot robot) {
        super(robot);
    }

    @Override
    protected String getName() {
        return "CarryToUser";
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
            Delay.msDelay(100);
            if (stopwatch.elapsed() > TIMEOUT_BEACON_SIGNAL) {
                robot.setCurrentState(State.ABORT);
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
        robot.setAngularSpeed(75);

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
