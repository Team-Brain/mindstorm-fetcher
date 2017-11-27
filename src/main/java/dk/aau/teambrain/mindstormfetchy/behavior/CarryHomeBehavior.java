package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

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
		return (Fetchy.currentState == State.CARRYING_HOME) || (Fetchy.currentState == State.ABORT);
	}

	@Override
	public void action() {
		super.action();
		suppressed = false;
		if (Fetchy.currentState == State.ABORT) {
			Fetchy.leaveOnTheSide();
		}
		Fetchy.navigator.followPath(SearchBehavior.getSearchPath());
		while (!Fetchy.navigator.pathCompleted() && !suppressed) {
		}
		// Fetchy.letGo();
		// Fetchy.requestQueue.remove(0);
		// Fetchy.pilot.travel(-100);
		// Fetchy.turn(180);
		SearchBehavior.printLocation(Fetchy.getCurrentLocation());
		SearchBehavior.clearSearchPath();
		if (Fetchy.currentState != State.ABORT) {
			Fetchy.currentState = State.CARRY_TO_USER;
		}
		else {
			Fetchy.currentState = State.WAITING_FOR_COMMAND;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	private static void goHome() {
		int distance = Integer.MAX_VALUE;

		SensorMode seek = Fetchy.seekerSensor.getSeekMode();
		MovementState state = MovementState.STOP;

		while (distance > 0 && !suppressed) {
			Delay.msDelay(200);
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
						// Fetchy.leftMotor.backward();
						// Fetchy.rightMotor.backward();
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
