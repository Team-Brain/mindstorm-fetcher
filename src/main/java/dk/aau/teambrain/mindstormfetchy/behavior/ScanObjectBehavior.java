package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.hardware.Sound;
import lejos.utility.Delay;

/**
 * ScanObjectBehavior becomes active when robot's IRSensor detects an object in front of it.
 * <p>
 * The robot moves towards the object and scans its color.
 * If the color is correct one, it grabs the object and sets the state to State.GOING_HOME,
 * otherwise it puts the object away from the path and continues searching.
 */
public class ScanObjectBehavior extends BaseBehavior {

    public static final String TAG = "ScanObject";

    public static final int SCAN_COLOR_TRIES = 10;
    private static final int SCAN_COLOR_DELAY = 100;
    private static final float MIN_SUCCESS_THRESHOLD = 0.75f;

    public ScanObjectBehavior(BaseRobot robot) {
        super(robot);
    }

    public ScanObjectBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        super(robot, listener);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    public boolean takeControl() {
        return robot.getIRDistance() < 5;
    }

    public void action() {
        super.action();
        suppressed = false;
        robot.travel(20);
        Delay.msDelay(100);
        if (checkColor()) {
            robot.grab();
            if (!suppressed) {
                robot.setCurrentState(State.GOING_HOME);
            }
        } else {
            robot.grab();
            if (!suppressed) {
                robot.leaveObjectOnSide(true);
            }
        }

    }

    /**
     * Take a number of samples from the color sensor and
     * return whether the success rate is over the given threshold.
     *
     * @return Whether the scanned color is correct one.
     */
    public boolean checkColor() {
        int correct = 0;
        for (int i = 0; i < SCAN_COLOR_TRIES; i++) {
            int scannedColorId = robot.getColorID();
            if (ColorSensorWrapper.colorName(scannedColorId).toLowerCase()
                    .equals(robot.getCurrentTask().getColor().toLowerCase())) {
                correct++;
            }
            Delay.msDelay(SCAN_COLOR_DELAY);
        }
        return ((float) correct / SCAN_COLOR_TRIES) >= MIN_SUCCESS_THRESHOLD;
    }


}