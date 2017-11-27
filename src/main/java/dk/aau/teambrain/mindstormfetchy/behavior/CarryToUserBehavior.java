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
		goHome();
	}

	@Override
	protected String getName() {
		return "CarryToUser";
	}

	private static void goHome() {
		int distance = Integer.MAX_VALUE;
		SensorMode seek = Fetchy.seekerSensor.getSeekMode();

		while (distance > 0 && !suppressed) {
			Delay.msDelay(200);
			float[] sample = new float[seek.sampleSize()];
			seek.fetchSample(sample, 0);
			int direction = (int) sample[0];
			System.out.println("Direction: " + direction);
			distance = (int) sample[1];
			System.out.println("Distance: " + distance);
			if (direction > 2) {
				Fetchy.rightMotor.backward();
				Fetchy.leftMotor.stop(true);
			} else if (direction < -2) {
				Fetchy.leftMotor.backward();
				Fetchy.rightMotor.stop(true);
			} else {
				if (distance < Integer.MAX_VALUE) {
					Fetchy.backward();
					while (distance > 0 && !suppressed) {
						seek.fetchSample(sample, 0);
						distance = (int) sample[1];
						System.out.println("Distance: " + distance);
					}
				}
			}
		}
		Fetchy.stop();
		Fetchy.turn(180);
		Fetchy.letGo();
		Fetchy.currentState = State.WAITING_FOR_COMMAND;
		Fetchy.requestQueue.remove(0);
	}

}
