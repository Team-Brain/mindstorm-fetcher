package dk.aau.teambrain.mindstormfetchy.robot;

/**
 * Interface for an operable robot that provides simple movement, gripping and sensor mechanisms.
 */
public interface FetchingRobot {

    /**
     * Move the robot by specified distance.
     * If negative move backward.
     *
     * @param distance Distance to travel.
     */
    void travel(int distance);
    
    void travel(int distance, boolean immediateReturn);

    /**
     * Move the robot forward.
     */
    void forward();

    /**
     * Move the robot backward.
     */
    void backward();

    /**
     * Stop the robot's movement.
     */
    void stop();

    /**
     * Grab an object using a gripping mechanism.
     */
    void grab();

    /**
     * Put object down.
     */
    void letGo();

    /**
     * Leave object on the side of current path.
     *
     * @param turnToStartAngle Should turn to starting angle after return
     *                         to the original path.
     */
    void leaveObjectOnSide(boolean turnToStartAngle);

    /**
     * Turn the robot by specified angle.
     *
     * @param angle Angle to turn the robot by.
     */
    void turn(double angle);

    /**
     * Turn the robot by specified angle.
     * ImmediateReturn indicates whether the method should return immediately, or wait
     * until the movement is finished.
     *
     * @param angle           Angle to turn the robot by.
     * @param immediateReturn Return immediately.
     */
    void turn(double angle, boolean immediateReturn);

    /**
     * Set angular speed of the robot.
     *
     * @param speed Angular speed of the robot.
     */
    void setAngularSpeed(double speed);


    /**
     * Navigate the robot to the starting position.
     */
    void goToStart();

    /**
     * Stop the robot's navigation.
     */
    void stopNavigation();

    /**
     * Returns whether the robot's navigation has been completed.
     *
     * @return Has the robot's navigation been completed.
     */
    boolean pathCompleted();

    /**
     * Get the distance value from the IRSensor.
     *
     * @return IRSensor distance value.
     */
    float getIRDistance();

    /**
     * Get the seeker distance value from the IRSensor.
     *
     * @return IRSensor seeker distance value.
     */
    float getSeekerDistance();

    /**
     * Get the seeker direction value from the IRSensor.
     *
     * @return IRSensor seeker direction value.
     */
    float getSeekerDirection();

    /**
     * Get the color id value from the ColorSensor.
     *
     * @return ColorSensor colorId value.
     */
    int getColorID();

}
