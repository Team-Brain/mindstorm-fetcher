package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.utility.Delay;

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
        Fetchy.pilot.setAngularSpeed(20);
        while (!suppressed) {
            rotateOrStop(-30);
            rotateOrStop(60);
            rotateOrStop(-30);
            Fetchy.forward(2000);
        }
        Fetchy.stop();
        Fetchy.pilot.setAngularSpeed(Fetchy.pilot.getMaxAngularSpeed());
    }


    private void rotateOrStop(double angle) {
        Fetchy.pilot.rotate(angle, true);
        while (Fetchy.pilot.isMoving()) {
            if (suppressed) {
                Delay.msDelay(200);
                Fetchy.stop();
                return;
            }
        }
    }
}