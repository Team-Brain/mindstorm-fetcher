package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.robotics.subsumption.Behavior;

public class CarryHomeBehavior implements Behavior {

    private static boolean suppressed;

    @Override
    public boolean takeControl() {
        return Fetchy.currentState == State.CARRYING_HOME;
    }

    @Override
    public void action() {
        suppressed = false;
        Fetchy.turn180();
        Fetchy.letGo();
        Fetchy.requestQueue.remove(0);
        Fetchy.currentState = State.WAITING_FOR_COMMAND;
//        goHome();
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    private void goHome() {
        int distance = Integer.MAX_VALUE;
        while (!suppressed && distance > 50) {
            float[] sample = new float[2];
            Fetchy.seekerSensor.fetchSample(sample, 0);
            int direction = (int) sample[0];
            System.out.println("Direction: " + direction);
            distance = (int) sample[1];
            System.out.println("Distance: " + distance);

            if (direction > 5) {
                Fetchy.pilot.rotateLeft();
            } else if (direction < -5) {
                Fetchy.pilot.rotateRight();
            } else {
                if (distance < Integer.MAX_VALUE) {
                    Fetchy.forward();
                }
            }
        }
        Fetchy.stop();
        Fetchy.letGo();
        Fetchy.requestQueue.remove(0);
    }

}
