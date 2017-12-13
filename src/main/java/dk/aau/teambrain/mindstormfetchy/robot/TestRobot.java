package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.behavior.ScanObjectBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import lejos.robotics.Color;

public class TestRobot extends BaseRobot {

    private int[] boxSetup = new int[]{Color.BLACK, Color.BLUE, Color.RED};

    private int currentBox = 0;

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
        super.grab();
        //
    }

    @Override
    public void letGo() {
        super.letGo();
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
    public void stopNavigation() {
        //
    }

    @Override
    public boolean pathCompleted() {
        return true;
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
    public float getIRDistance() {
        if (currentBox >= boxSetup.length * ScanObjectBehavior.SCAN_COLOR_TRIES) {
            return 100;
        }
        return 0;
    }

    @Override
    public float getSeekerDistance() {
        return 0;
    }

    @Override
    public float getSeekerDirection() {
        return 1;
    }

    @Override
    public int getColorID() {
        return boxSetup[currentBox++ / ScanObjectBehavior.SCAN_COLOR_TRIES];
    }


    @Override
    public void onNewTask(Task task) {
        super.onNewTask(task);
        resetDefaultValues();
    }

    private void resetDefaultValues() {
        currentBox = 0;
    }

}
