package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.robotics.subsumption.Behavior;

public class WaitForCommandBehavior implements Behavior {

    @Override
    public boolean takeControl() {
        return Fetchy.requestQueue.isEmpty();
    }

    @Override
    public void action() {
    }

    @Override
    public void suppress() {
    }
}
