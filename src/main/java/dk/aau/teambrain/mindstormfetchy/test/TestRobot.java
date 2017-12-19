package dk.aau.teambrain.mindstormfetchy.test;

import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import lejos.robotics.Color;

/**
 * TestRobot class represents mocked robot object.
 */
public class TestRobot extends BaseRobot {

    private int irDistance = 0;
    private int seekerDistance = 0;
    private int seekerDirection = 1;
    private int colorId = Color.NONE;

    public TestRobot() {
        super();
    }

    @Override
    public void init() {

    }

    @Override
    public void travel(int distance) {
        //
    }
    
    @Override
    public void travel(int distance, boolean immediateReturn) {
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
        super.letGo();
    }

    @Override
    public float getIRDistance() {
        return irDistance;
    }

    @Override
    public float getSeekerDistance() {
        return seekerDistance;
    }

    @Override
    public float getSeekerDirection() {
        return seekerDirection;
    }

    @Override
    public int getColorID() {
        return colorId;
    }

    @Override
    public void onNewTask(Task task) {
        super.onNewTask(task);
    }

    @Override
    public void onTaskFinished() {
        currentTask = null;
    }

    /**
     * Set mocked IRSensor distance.
     *
     * @param irDistance mocked IRSensor distance
     */
    public void setIrDistance(int irDistance) {
        this.irDistance = irDistance;
    }

    /**
     * Set mocked IRSensor seeker mode distance.
     *
     * @param seekerDistance mocked IRSensor seeker mode distance
     */
    public void setSeekerDistance(int seekerDistance) {
        this.seekerDistance = seekerDistance;
    }

    /**
     * Set mocked IRSensor seeker mode direction.
     *
     * @param seekerDirection mocked IRSensor seeker mode direction
     */
    public void setSeekerDirection(int seekerDirection) {
        this.seekerDirection = seekerDirection;
    }

    /**
     * Set mocked ColorSensor colorId.
     *
     * @param colorId mocked ColorSensor colorId
     */
    public void setColorId(int colorId) {
        this.colorId = colorId;
    }


}
