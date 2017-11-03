package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.robotics.subsumption.Behavior;

public class SearchBehavior implements Behavior {

    private static boolean suppressed;

    public boolean takeControl() {
        return !Fetchy.requestQueue.isEmpty();
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        suppressed = false;
        Fetchy.forward();
        while (!suppressed) {
            try {
                Thread.sleep(200);
            } catch (Exception ignored) {
            }
        }
        Fetchy.stop();
    }
}