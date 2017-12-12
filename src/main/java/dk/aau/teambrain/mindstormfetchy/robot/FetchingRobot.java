package dk.aau.teambrain.mindstormfetchy.robot;

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

    boolean pathCompleted();

    float getIRDistance();

    float getSeekerDistance();

    float getSeekerDirection();

    int getColorID();

    void setAngularSpeed(double speed);

    void leaveObjectOnSide(boolean turnToStartAngle);

}
