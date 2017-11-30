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
            // TODO: Figure out looking around
//            rotateOrStop(15);
//            rotateOrStop(-30);
//            rotateOrStop(15);
//            forwardOrStop(2000);
        }
        Fetchy.stop();
    }

//    private void forwardOrStop(int millis) {
//        if (suppressed) {
//            return;
//        }
//        Fetchy.pilot.forward();
//        stopIfSuppressed(millis);
//    }
//
//
//    private void rotateOrStop(double angle) {
//        Fetchy.pilot.rotate(angle, true);
//        while (Fetchy.pilot.isMoving()) {
//            if (suppressed) {
//                Delay.msDelay(200);
//                Fetchy.stop();
//                return;
//            }
//        }
//    }
//
//    private void stopIfSuppressed(int millis) {
//        Stopwatch watch = new Stopwatch();
//        while (Fetchy.pilot.isMoving()) {
//            try {
//                if (suppressed || watch.elapsed() > millis) {
//                    return;
//                }
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                return;
//            }
//
//        }
//    }
}