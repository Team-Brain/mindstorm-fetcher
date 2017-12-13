package dk.aau.teambrain.mindstormfetchy.robot;

/**
 * Represent an operable robot that provides simple movement, gripping and sensor mechanisms.
 */
public interface FetchingRobot {

    void travel(int distance);

    void forward();

    void backward();

    void stop();

    void grab();

    void letGo();

    void turn(double angle);

    void turn(double angle, boolean immediateReturn);

    void goToStart();

    void stopNavigation();

    boolean pathCompleted();

    float getIRDistance();

    float getSeekerDistance();

    float getSeekerDirection();

    int getColorID();

    void setAngularSpeed(double speed);

    void leaveObjectOnSide(boolean turnToStartAngle);

}
