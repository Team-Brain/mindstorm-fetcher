package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.hardware.Sound;
import lejos.utility.Delay;

import java.util.Random;

public class ScanObjectBehavior extends BaseBehavior {

    private static final int SCAN_COLOR_TRIES = 20;
    private static final int SCAN_COLOR_DELAY = 200;
    private static final float MIN_SUCCESS_THRESHOLD = 0.75f;

    @Override
    protected String getName() {
        return "ScanObject";
    }

    public boolean takeControl() {
        return (Fetchy.irSensor.getRange() < 5);
    }

    public void action() {
        super.action();
        suppressed = false;
        Delay.msDelay(1000);
        if (checkColor()) {
            Fetchy.grab();
            Fetchy.currentState = State.CARRYING_HOME;
        } else {
            Fetchy.backward(2000);
            turnRandomly();
        }

    }

    public void suppress() {
        suppressed = true;
    }

    private void turnRandomly() {
        double angle = 45 + (Math.random() * 135);
        double orientedAngle = new Random().nextBoolean() ? angle : -angle;
        Fetchy.turn(orientedAngle);
    }

    /**
     * Take a number of samples from the color sensor and
     * return whether the success rate is over the given threshold.
     */
    private boolean checkColor() {
        Sound.beep();
        int correct = 0;
        for (int i = 0; i < SCAN_COLOR_TRIES; i++) {
            int scannedColorId = Fetchy.colorSensor.getColorID();
            if (ColorSensorWrapper.colorName(scannedColorId).toLowerCase()
                    .equals(Fetchy.requestQueue.get(0).color.toLowerCase())) {
                correct++;
            }
            Delay.msDelay(SCAN_COLOR_DELAY);
        }
        Sound.beep();
        return ((float) correct / SCAN_COLOR_TRIES) >= MIN_SUCCESS_THRESHOLD;
    }


}