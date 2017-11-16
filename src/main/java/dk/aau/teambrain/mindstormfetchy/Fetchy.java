package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.model.Request;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import dk.aau.teambrain.mindstormfetchy.utils.IRSensorWrapper;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
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

    public static MovePilot pilot;

    public static State currentState;

    public static EV3LargeRegulatedMotor leftMotor;
    public static EV3LargeRegulatedMotor rightMotor;

    public static void init() {
        System.out.println("Initializing Fetchy");

        // Initialize sensors
        seekerSensor = new EV3IRSensor(SensorPort.S2);
        colorSensor = new ColorSensorWrapper(SensorPort.S3);
//        irSensor = new IRSensorWrapper(SensorPort.S4);

        seekerSensor.getSeekMode();

        // Initialize grip motor
        gripMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        gripMotor.setSpeed(200);

        // Initialize pilot
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel leftWheel = WheeledChassis.modelWheel(leftMotor, 30.4).offset(-72);
        Wheel rightWheel = WheeledChassis.modelWheel(rightMotor, 30.4).offset(72);
        Chassis chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
        pilot = new MovePilot(chassis);
        pilot.setLinearSpeed(50);

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior();
        Behavior goToBeh = new GoToObjectBehavior();
        Behavior scanBeh = new ScanObjectBehavior();
        Behavior carryHomeBeh = new CarryHomeBehavior();
        Behavior waitForCommandBeh = new WaitForCommandBehavior();
        Behavior[] bArray = {searchBeh, goToBeh, scanBeh, carryHomeBeh, waitForCommandBeh};

        Arbitrator arb = new Arbitrator(bArray);
        arb.go();
        System.out.println("Initialization complete");

    }

    public static void forward() {
        pilot.forward();
    }

    public static void forward(int millis) {
        pilot.forward();
        Delay.msDelay(millis);
        pilot.stop();
    }

    public static void backward() {
        pilot.backward();
    }

    public static void backward(int millis) {
        pilot.backward();
        Delay.msDelay(millis);
        pilot.stop();
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

    public static void turn180() {
        pilot.rotate(180);
    }

    public static void createDemoRequest() {
        Delay.msDelay(2000);
        Request request = new Request();
        request.color = "Blue";
        Fetchy.requestQueue.add(request);
    }

    // TODO: Create nice intro message
    public static void introMessage() {

        GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
        g.drawString("Fetchy Demo", 5, 0, 0);
        g.setFont(Font.getSmallFont());
        g.drawString("Demonstration of Fetchy", 2, 20, 0);
        g.drawString("Requires a wheeled ", 2, 30, 0);
        g.drawString("vehicle with two", 2, 40, 0);
        g.drawString("independently controlled", 2, 50, 0);
        g.drawString("motors connected to motor", 2, 60, 0);
        g.drawString("ports B and C, and an", 2, 70, 0);
        g.drawString("infrared sensor connected", 2, 80, 0);
        g.drawString("to port 4.", 2, 90, 0);

        // Quit GUI button:
        g.setFont(Font.getSmallFont()); // can also get specific size using Font.getFont()
        int y_quit = 100;
        int width_quit = 45;
        int height_quit = width_quit / 2;
        int arc_diam = 6;
        g.drawString("QUIT", 9, y_quit + 7, 0);
        g.drawLine(0, y_quit, 45, y_quit); // top line
        g.drawLine(0, y_quit, 0, y_quit + height_quit - arc_diam / 2); // left line
        g.drawLine(width_quit, y_quit, width_quit, y_quit + height_quit / 2); // right line
        g.drawLine(arc_diam / 2, y_quit + height_quit, width_quit - 10, y_quit + height_quit); // bottom line
        g.drawLine(width_quit - 10, y_quit + height_quit, width_quit, y_quit + height_quit / 2); // diagonal
        g.drawArc(0, y_quit + height_quit - arc_diam, arc_diam, arc_diam, 180, 90);

        // Enter GUI button:
        g.fillRect(width_quit + 10, y_quit, height_quit, height_quit);
        g.drawString("GO", width_quit + 15, y_quit + 7, 0, true);

        Button.waitForAnyPress();
        if (Button.ESCAPE.isDown()) {
            System.exit(0);
        }
        g.clear();
    }


}