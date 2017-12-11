package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import dk.aau.teambrain.mindstormfetchy.utils.IRSensorWrapper;
import dk.aau.teambrain.mindstormfetchy.utils.SeekSensorWrapper;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.Move;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class Robot implements FetchingRobot {

    private static ColorSensorWrapper colorSensor;
    private static IRSensorWrapper irSensor;
    private static SeekSensorWrapper seekerSensor;

    private static EV3LargeRegulatedMotor leftMotor;
    private static EV3LargeRegulatedMotor rightMotor;
    private static EV3MediumRegulatedMotor gripMotor;

    private static MovePilot pilot;
    private static Navigator navigator;


    public Robot() {

        // Initialize sensors
        seekerSensor = new SeekSensorWrapper(SensorPort.S2);
        colorSensor = new ColorSensorWrapper(SensorPort.S3);
        irSensor = new IRSensorWrapper(SensorPort.S4);

        // Initialize grip motor
        gripMotor = new EV3MediumRegulatedMotor(MotorPort.B);
        gripMotor.setSpeed(200);

        // Initialize pilot
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 32.5).offset(-78);
        Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 32.5).offset(78);
        Chassis chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(100);
        pilot.setAngularSpeed(50);
        navigator = new Navigator(pilot, chassis.getPoseProvider());

    }

    @Override
    public void travel(int distance) {
        pilot.travel(distance);
    }

    @Override
    public void forward() {
        pilot.forward();
    }

    @Override
    public void backward() {
        pilot.backward();
    }

    @Override
    public void stop() {
        pilot.stop();
    }

    @Override
    public void grab() {
        gripMotor.rotate(500);
    }

    @Override
    public void letGo() {
        gripMotor.rotate(-500);
    }

    @Override
    public void turn(double angle) {
        pilot.rotate(angle);
    }

    @Override
    public void turn(double angle, boolean immediateReturn) {
        pilot.rotate(angle, immediateReturn);
    }

    @Override
    public void goToStart() {
        navigator.goTo(new Waypoint(0, 0, 0));
    }

    @Override
    public boolean pathCompleted() {
        return navigator.pathCompleted();
    }

    @Override
    public Move.MoveType getMoveType() {
        return navigator.getMoveController().getMovement().getMoveType();
    }

    @Override
    public boolean isMoving() {
        return navigator.isMoving();
    }

    @Override
    public float getIRDistance() {
        return irSensor.getRange();
    }

    @Override
    public float getSeekerDistance() {
        return seekerSensor.getSeekDistance();
    }

    @Override
    public float getSeekerDirection() {
        return seekerSensor.getSeekDirection();
    }

    @Override
    public int getColorID() {
        return colorSensor.getColorID();
    }

    @Override
    public void setAngularSpeed(double speed) {
        pilot.setAngularSpeed(speed);
    }

    @Override
    public void leaveObjectOnSide(boolean turnToStartAngle) {
        turn(90);
        pilot.travel(100);
        letGo();
        pilot.travel(-100);
        if (turnToStartAngle) {
            turn(-90);
        }
    }

    @Override
    public void close() {
        irSensor.close();
        colorSensor.close();
        seekerSensor.close();
        leftMotor.close();
        rightMotor.close();
        gripMotor.close();
    }
}
