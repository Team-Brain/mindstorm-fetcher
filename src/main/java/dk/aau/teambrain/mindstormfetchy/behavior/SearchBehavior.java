package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class SearchBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "Search";
    }

    public boolean takeControl() {
        return !Fetchy.requestQueue.isEmpty();
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        super.action();
        suppressed = false;
        Fetchy.forward();
        while (!suppressed) {
            Thread.yield();
        }
        Fetchy.stop();
    }

}