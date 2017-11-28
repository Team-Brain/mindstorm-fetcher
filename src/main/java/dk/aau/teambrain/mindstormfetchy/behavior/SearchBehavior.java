package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class SearchBehavior extends BaseBehavior {

    private static final Path searchPath = new Path();

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
        addLocation(Fetchy.getCurrentLocation());
        Fetchy.forward();
        while (!suppressed) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // TODO: Figure out looking around
//            rotateOrStop(15);
//            rotateOrStop(-30);
//            rotateOrStop(15);
//            forwardOrStop(2000);
        }
        Fetchy.stop();
    }

    public static void addLocation(Waypoint location) {
        System.out.println(((int) location.x) + ":" + ((int) location.y));
        searchPath.add(0, location);
    }


    public static Path getSearchPath() {
        return searchPath;
    }

    public static void clearSearchPath() {
        searchPath.clear();
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