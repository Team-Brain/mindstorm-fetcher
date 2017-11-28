package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class CarryToUserBehavior extends BaseBehavior {

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
//		goHome();
        Fetchy.letGo();
    }

	@Override
	protected String getName() {
		return "CarryToUser";
	}

	private static void goHome() {
		SensorMode seek = Fetchy.seekerSensor.getSeekMode();
		float[] sample = new float[seek.sampleSize()];
		seek.fetchSample(sample, 0);

		int direction = (int) sample[0];
		System.out.println("Direction: " + direction);

		int distance = (int) sample[1];
		System.out.println("Distance: " + distance);

		Fetchy.turn(direction);

		if (distance < Integer.MAX_VALUE) {
			Fetchy.backward();
			while (distance > 0 && !suppressed) {
				seek.fetchSample(sample, 0);
				distance = (int) sample[1];
				System.out.println("Distance: " + distance);
			}
		}

		Fetchy.stop();
		Fetchy.turn(180);
		Fetchy.letGo();
		Fetchy.navigator.goTo(0, 0);
		Fetchy.currentState = State.WAITING_FOR_COMMAND;
		Fetchy.requestQueue.remove(0);
	}

}
