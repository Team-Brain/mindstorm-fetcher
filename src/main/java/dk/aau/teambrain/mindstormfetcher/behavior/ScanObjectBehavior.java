package dk.aau.teambrain.mindstormfetcher.behavior;

import dk.aau.teambrain.mindstormfetcher.Fetchy;
import dk.aau.teambrain.mindstormfetcher.State;
import dk.aau.teambrain.mindstormfetcher.utils.ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class ScanObjectBehavior implements Behavior {

    private static boolean suppressed;

    public boolean takeControl() {
        return (Fetchy.irSensor.getRange() < 5) && Fetchy.currentState != State.CARRYING_HOME;
    }

    public void action() {
        suppressed = false;
        Fetchy.stop();
        Delay.msDelay(1000);
        if (ColorSensor.colorName(Fetchy.colorSensor.getColorID()).toLowerCase().
                equals(Fetchy.requestQueue.get(0).color.toLowerCase())) {
            Fetchy.grab();
            Fetchy.currentState = State.CARRYING_HOME;
        }

    }

    public void suppress() {
        suppressed = true;
    }


}