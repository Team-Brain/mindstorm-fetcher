package dk.aau.teambrain.mindstormfetcher;

import dk.aau.teambrain.mindstormfetcher.behavior.CarryHomeBehavior;
import dk.aau.teambrain.mindstormfetcher.behavior.ScanObjectBehavior;
import dk.aau.teambrain.mindstormfetcher.behavior.SearchBehavior;
import dk.aau.teambrain.mindstormfetcher.behavior.WaitForCommandBehavior;
import dk.aau.teambrain.mindstormfetcher.model.Request;
import dk.aau.teambrain.mindstormfetcher.utils.ColorSensor;
import dk.aau.teambrain.mindstormfetcher.utils.IRSensor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

import java.util.ArrayList;
import java.util.List;

public class Fetchy {

    public static ColorSensor colorSensor;
    public static IRSensor irSensor;
    private static EV3MediumRegulatedMotor gripMotor;
    public static List<Request> requestQueue = new ArrayList<>();

    public static MovePilot pilot;

    public static State currentState;

    public static void init() {
        System.out.println("Initializing Fetchy");
        currentState = State.WAITING_FOR_COMMAND;

        // Initialize sensors
        colorSensor = new ColorSensor(SensorPort.S3);
        irSensor = new IRSensor(SensorPort.S4);

        // Initialize grip motor
        gripMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        gripMotor.setSpeed(200);

        // Initialize pilot
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 30.4).offset(-70);
        Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 30.4).offset(70);
        Chassis chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(50);

        // Initialize behaviours
        Behavior b1 = new SearchBehavior();
        Behavior b2 = new ScanObjectBehavior();
        Behavior b3 = new CarryHomeBehavior();
        Behavior b4 = new WaitForCommandBehavior();
        Behavior[] bArray = {b1, b2, b3, b4};

        Arbitrator arb = new Arbitrator(bArray);
        arb.go();
        System.out.println("Initialization complete");
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

    public static void turn180() {
        pilot.rotate(180);
    }

}