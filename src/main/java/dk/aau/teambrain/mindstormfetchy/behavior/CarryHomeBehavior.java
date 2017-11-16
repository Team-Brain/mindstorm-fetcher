package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class CarryHomeBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "CarryHome";
    }

    @Override
    public boolean takeControl() {
        return Fetchy.currentState == State.CARRYING_HOME;
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        Fetchy.turn180();
        Fetchy.letGo();
        Fetchy.backward(2000);
        Fetchy.turn180();
        Fetchy.requestQueue.remove(0);
        Fetchy.currentState = State.WAITING_FOR_COMMAND;
//        goHome();
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    @SuppressWarnings("Duplicates")
    private static void goHome() {
        int distance = Integer.MAX_VALUE;

        SensorMode seek = Fetchy.seekerSensor.getSeekMode();

        while (true) {
            Delay.msDelay(200);
            float[] sample = new float[seek.sampleSize()];
            seek.fetchSample(sample, 0);
            int direction = (int) sample[0];
            System.out.println("Direction: " + direction);
            distance = (int) sample[1];
            System.out.println("Distance: " + distance);

            if (direction > 5) {
                Fetchy.rightMotor.backward();
                Fetchy.leftMotor.stop(true);
            } else if (direction < -5) {
                Fetchy.leftMotor.backward();
                Fetchy.rightMotor.stop(true);
            } else {
                if (distance < Integer.MAX_VALUE) {
                    Fetchy.backward();
                }
            }
        }
//        Fetchy.stop();
//        Fetchy.letGo();
//        Fetchy.requestQueue.remove(0);
    }

}
