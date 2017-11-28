package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.model.Request;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import dk.aau.teambrain.mindstormfetchy.utils.IRSensorWrapper;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

import java.util.ArrayList;
import java.util.List;

public class Fetchy {

    public static ColorSensorWrapper colorSensor;
    public static IRSensorWrapper irSensor;
    public static EV3IRSensor seekerSensor;
    private static EV3MediumRegulatedMotor gripMotor;
    public static List<Request> requestQueue = new ArrayList<>();

    private static MovePilot pilot;

    public static State currentState;

    public static Navigator navigator;

    public static void init() {
        System.out.println("Initializing Fetchy");

        // Initialize sensors
        seekerSensor = new EV3IRSensor(SensorPort.S2);
        colorSensor = new ColorSensorWrapper(SensorPort.S3);
        irSensor = new IRSensorWrapper(SensorPort.S4);

        // Initialize grip motor
        gripMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        gripMotor.setSpeed(200);

        // Initialize pilot
        RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 30.7).offset(-75);
        Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 30.7).offset(75);
        Chassis chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(75);
        pilot.setAngularSpeed(30);
        navigator = new Navigator(pilot, chassis.getPoseProvider());

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior();
        Behavior goToBeh = new GoToObjectBehavior();
        Behavior scanBeh = new ScanObjectBehavior();
        Behavior goHomeBehavior = new GoHomeBehavior();
        Behavior carryToUserBeh = new CarryToUserBehavior();
        Behavior waitForCommandBeh = new WaitForCommandBehavior();
        Behavior[] bArray = {searchBeh, goToBeh, scanBeh, goHomeBehavior, carryToUserBeh, waitForCommandBeh};

        Arbitrator arb = new Arbitrator(bArray);
        arb.go();

        System.out.println("Initialization complete");
    }

    public static void travel(int distance) {
        pilot.travel(distance);
    }

    public static void forward() {
        pilot.forward();
    }

    public static void backward() {
        pilot.backward();
    }

    public static void stop() {
        pilot.stop();
    }

    public static void grab() {
        gripMotor.rotate(600);
    }

    public static void letGo() {
        gripMotor.rotate(-600);
    }

    public static void turn(double angle) {
        pilot.rotate(angle);
    }

    public static Waypoint getCurrentLocation() {
        return new Waypoint(navigator.getPoseProvider().getPose().getX(),
                navigator.getPoseProvider().getPose().getY());
    }

    public static void leaveOnTheSide() {
        Delay.msDelay(200);
        turn(90);
//        printCurrentLocation();
        pilot.travel(100);
//        printCurrentLocation();
        letGo();
        pilot.travel(-100);
//        printCurrentLocation();
        turn(-90);
//        printCurrentLocation();
    }

    public static void printCurrentLocation() {
        printLocation(Fetchy.getCurrentLocation());
    }

    public static void printLocation(Waypoint location) {
        System.out.println(((int) location.x) + ":" + ((int) location.y));
    }

    public static void createDemoRequest() {
        Delay.msDelay(2000);
        Request request = new Request();
        request.color = "Red";
        Fetchy.requestQueue.add(request);
    }

}