package dk.aau.teambrain.mindstormfetchy;

import lejos.robotics.navigation.Move;

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

    Move.MoveType getMoveType();

    boolean isMoving();

    float getIRDistance();

    float getSeekerDistance();

    float getSeekerDirection();

    int getColorID();

    void setAngularSpeed(double speed);

    void leaveObjectOnSide(boolean turnToStartAngle);

    void close();
}
