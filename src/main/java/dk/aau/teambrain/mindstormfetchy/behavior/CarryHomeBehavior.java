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
        String currentMove = "Stop";

        SensorMode seek = Fetchy.seekerSensor.getSeekMode();

        while (distance>0) {
            float[] sample = new float[seek.sampleSize()];
            seek.fetchSample(sample, 0);
            int direction = (int) sample[0];
            System.out.println("Direction: " + direction);
            distance = (int) sample[1];
            System.out.println("Distance: " + distance);

            if (direction > 5) {
            	if(!currentMove.equals("Left")) {
                Fetchy.rightMotor.backward();
                Fetchy.leftMotor.stop(true);
                currentMove = "Left";
            	}
            } else if (direction < -5) {
            	if(!currentMove.equals("Right")) {
                Fetchy.leftMotor.backward();
                Fetchy.rightMotor.stop(true);
                currentMove = "Right";
            	}
            } else {
            	if(!currentMove.equals("Backwards")) {
            		if (distance < Integer.MAX_VALUE) {
            			Fetchy.backward();
            			currentMove = "Backwards";
            		}
            	}
            }
        }
        Fetchy.stop();
        Fetchy.letGo();
        Fetchy.currentState = State.WAITING_FOR_COMMAND;
        Fetchy.requestQueue.remove(0);
    }

}
