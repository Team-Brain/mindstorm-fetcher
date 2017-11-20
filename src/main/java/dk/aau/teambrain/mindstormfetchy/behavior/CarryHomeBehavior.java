package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.sensor.SensorMode;

public class CarryHomeBehavior extends BaseBehavior {

    private enum MovementState {
        LEFT, RIGHT, BACKWARDS, STOP
    }

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
        /*Fetchy.turn180();
        Fetchy.letGo();
        Fetchy.backward(2000);
        Fetchy.turn180();
        Fetchy.requestQueue.remove(0);
        */
        goHome();

    }

    @Override
    public void suppress() {
        suppressed = true;
    }

    private static void goHome() {
        int distance = Integer.MAX_VALUE;
//        String currentMove = "Stop";

        SensorMode seek = Fetchy.seekerSensor.getSeekMode();
        MovementState state = MovementState.STOP;

        while (distance > 0 && !suppressed) {
            float[] sample = new float[seek.sampleSize()];
            seek.fetchSample(sample, 0);
            int direction = (int) sample[0];
            System.out.println("Direction: " + direction);
            distance = (int) sample[1];
            System.out.println("Distance: " + distance);
            if (direction > 5) {
                if (state != MovementState.LEFT) {
                    state = MovementState.LEFT;
                    Fetchy.rightMotor.backward();
                    Fetchy.leftMotor.stop(true);
                }
            } else if (direction < -5) {
                if (state != MovementState.RIGHT) {
                    state = MovementState.RIGHT;
                    Fetchy.leftMotor.backward();
                    Fetchy.rightMotor.stop(true);
                }
            } else {
                if (distance < Integer.MAX_VALUE) {
                    if (state != MovementState.BACKWARDS) {
                        state = MovementState.BACKWARDS;
//                        Fetchy.leftMotor.backward();
//                        Fetchy.rightMotor.backward();
                        Fetchy.backward();
                    }
                } else {
                    state = MovementState.STOP;
                    Fetchy.stop();
                }
            }
        }
        Fetchy.stop();
        Fetchy.letGo();
        if (!suppressed) {
            Fetchy.currentState = State.WAITING_FOR_COMMAND;
            Fetchy.requestQueue.remove(0);
        }
    }

}
