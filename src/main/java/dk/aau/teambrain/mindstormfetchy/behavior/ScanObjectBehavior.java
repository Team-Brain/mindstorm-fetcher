package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.utility.Delay;

public class ScanObjectBehavior extends BaseBehavior {

    private static final int SCAN_COLOR_TRIES = 10;
    private static final int SCAN_COLOR_DELAY = 100;
    private static final float MIN_SUCCESS_THRESHOLD = 0.75f;

    @Override
    protected String getName() {
        return "ScanObject";
    }

    public boolean takeControl() {
        return Fetchy.robot.getIRDistance() < 5;
    }

    public void action() {
        super.action();
        suppressed = false;
        Fetchy.robot.travel(20);
        Delay.msDelay(100);
        if (checkColor()) {
            Fetchy.grabObject();
            if (!suppressed) {
                Fetchy.setCurrentState(State.GOING_HOME);
            }
        } else {
            Fetchy.grabObject();
            if (!suppressed) {
                Fetchy.leaveOnTheSide(true);
            }
        }

    }

    /**
     * Take a number of samples from the color sensor and
     * return whether the success rate is over the given threshold.
     */
    private boolean checkColor() {
//        Sound.beep();
        int correct = 0;
        for (int i = 0; i < SCAN_COLOR_TRIES; i++) {
            int scannedColorId = Fetchy.robot.getColorID();
            if (ColorSensorWrapper.colorName(scannedColorId).toLowerCase()
                    .equals(Fetchy.getCurrentTask().getColor().toLowerCase())) {
                correct++;
            }
            Delay.msDelay(SCAN_COLOR_DELAY);
        }
//        Sound.beep();
        return ((float) correct / SCAN_COLOR_TRIES) >= MIN_SUCCESS_THRESHOLD;
    }


}