package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class CarryToUserBehavior extends BaseBehavior {

    private static final int TIMEOUT_BEACON_SIGNAL = 7 * 1000;

    @Override
    protected String getName() {
        return "CarryToUser";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.getCurrentState() == State.CARRY_TO_USER;
    }

    public void action() {
        super.action();
        suppressed = false;
        navigateToBeacon();
        if (!suppressed) {
            Fetchy.robot.turn(180);
            Fetchy.leaveObject();
            Fetchy.robot.travel(-100);
            Fetchy.robot.stop();
            Fetchy.setCurrentState(State.GOING_HOME);
        }
    }

    private static void navigateToBeacon() {
        float direction = 0;
        // Wait for signal
        Stopwatch stopwatch = new Stopwatch();
        while (direction == 0 && !suppressed) {
            direction = Fetchy.robot.getSeekerDirection();
            Log.d("Direction: " + direction);
            Delay.msDelay(100);
            if (stopwatch.elapsed() > TIMEOUT_BEACON_SIGNAL) {
                Fetchy.setCurrentState(State.ABORT);
            }
        }

        if (suppressed) {
            return;
        }

        Fetchy.robot.setAngularSpeed(20);

        // Turn around
        if (direction > 1) {
            Fetchy.robot.turn(90, true);
        } else if (direction < -1) {
            Fetchy.robot.turn(-90, true);
        }

        while (Math.abs(direction) > 1) {
            direction = Fetchy.robot.getSeekerDirection();
        }

        Fetchy.robot.stop();
        Fetchy.robot.setAngularSpeed(75);

        if (!suppressed) {
            Fetchy.robot.turn(direction);
            Fetchy.robot.backward();
        }

        float distance = Integer.MAX_VALUE;
        while (distance > 5 && !suppressed) {
            distance = Fetchy.robot.getSeekerDistance();
        }

        Fetchy.robot.stop();
    }
}
