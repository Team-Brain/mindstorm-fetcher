package dk.aau.teambrain.mindstormfetchy;

import lejos.robotics.Color;
import lejos.robotics.navigation.Move;

public class TestRobot implements FetchingRobot {

    private static final int IR_START_DISTANCE = 10;
    private static final int IR_STEP_DISTANCE = 1;

    private static final int SEEKER_START_DISTANCE = 10;
    private static final int SEEKER_STEP_DISTANCE = 1;

    private static final int SEEKER_START_ANGLE = 10;
    private static final int SEEKER_STEP_ANGLE = 1;

    private int irDistance = IR_START_DISTANCE;
    private int seekerDistance = SEEKER_START_DISTANCE;
    private int seekerAngle = SEEKER_START_ANGLE;

    public TestRobot() {

    }

    @Override
    public void travel(int distance) {
        //
    }

    @Override
    public void forward() {
        //
    }

    @Override
    public void backward() {
        //
    }

    @Override
    public void stop() {
        //
    }

    @Override
    public void grab() {
        //
    }

    @Override
    public void letGo() {
        //
    }

    @Override
    public void turn(double angle) {
        //
    }

    @Override
    public void turn(double angle, boolean immediateReturn) {
        //
    }

    @Override
    public void goToStart() {
        //
    }

    @Override
    public boolean pathCompleted() {
        return true;
    }

    @Override
    public Move.MoveType getMoveType() {
        return Move.MoveType.STOP;
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public float getIRDistance() {
        return 0;
    }

    @Override
    public float getSeekerDistance() {
        return 0;
    }

    @Override
    public float getSeekerDirection() {
        return 0;
    }

    @Override
    public int getColorID() {
        return Color.BLUE;
    }

    @Override
    public void setAngularSpeed(double speed) {
        //
    }

    @Override
    public void leaveObjectOnSide(boolean turnToStartAngle) {
        //
    }

    @Override
    public void close() {
        //
    }
}
