package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.hardware.Sound;

public class GoToObjectBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "GoTo";
    }

    @Override
    public boolean takeControl() {
        return !Fetchy.requestQueue.isEmpty()
                && Fetchy.irSensor.getRange() < 60;
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        Sound.beep();
        Fetchy.forward();
        while (!suppressed) {
            // ignored
        }
        Fetchy.stop();
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}
