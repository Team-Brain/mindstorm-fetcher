package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.utility.Stopwatch;

public class SearchBehavior extends BaseBehavior {

    private static final int TIMEOUT_SEARCH = 20 * 1000;

    @Override
    protected String getName() {
        return "Search";
    }

    public boolean takeControl() {
        return Fetchy.hasTask();
    }

    public void suppress() {
        suppressed = true;
    }

    public void action() {
        super.action();
        Stopwatch stopwatch = new Stopwatch();
        suppressed = false;
        Fetchy.forward();
        while (!suppressed) {
            try {
                if (stopwatch.elapsed() > TIMEOUT_SEARCH) {
                    Fetchy.currentState = State.ABORT;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Fetchy.stop();
    }

}