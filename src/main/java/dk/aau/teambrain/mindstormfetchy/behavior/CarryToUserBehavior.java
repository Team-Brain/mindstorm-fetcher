package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class CarryToUserBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "CarryToUser";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.currentState == State.CARRY_TO_USER;
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    public void action() {
        super.action();
        suppressed = false;
        navigateToBeacon();
        Fetchy.turn(180);
        Fetchy.letGo();
        Fetchy.travel(-100);
        Fetchy.goToStart();
        while (!Fetchy.navigator.pathCompleted() && !suppressed) {
            Thread.yield();
        }
        Fetchy.requestQueue.remove(0);
        Fetchy.currentState = State.WAITING_FOR_COMMAND;
    }

    private static void navigateToBeacon() {
        Fetchy.pilot.setAngularSpeed(20);
        SensorMode seek = Fetchy.seekerSensor.getSeekMode();
        int direction = 0;
        float[] sample = new float[seek.sampleSize()];
        // Wait for signal
        while (direction == 0 && !suppressed) {
            seek.fetchSample(sample, 0);
            direction = (int) sample[0];
            System.out.println("Direction: " + direction);
            Delay.msDelay(100);
        }

        // Turn around
        if (direction > 1) {
            Fetchy.turn(90, true);
        } else if (direction < -1) {
            Fetchy.turn(-90, true);
        }

        while (Math.abs(direction) > 1) {
            seek.fetchSample(sample, 0);
            direction = (int) sample[0];
        }

        Fetchy.stop();
        Fetchy.pilot.setAngularSpeed(75);

        if (!suppressed) {
            Fetchy.turn(direction);
            Fetchy.backward();
        }

        int distance = Integer.MAX_VALUE;
        while (distance > 5 && !suppressed) {
            seek.fetchSample(sample, 0);
            distance = (int) sample[1];
        }

        Fetchy.stop();
    }
}
