package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.robot.Fetchy;
import dk.aau.teambrain.mindstormfetchy.thread.ExitThread;
import dk.aau.teambrain.mindstormfetchy.thread.WebSocketThread;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

    public static final boolean DEBUG = false;

    private static boolean initialized = false;

    public static void main(String[] args) throws Exception {

        // Initialize robot
        final BaseRobot robot = new Fetchy();

        // Start the exit listener thread
        new ExitThread().start();

        // Start the socketIO thread
        new WebSocketThread(robot).start();

        while (!WebSocketThread.isConnected()) {
            Thread.sleep(200);
        }

        robot.init();

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior(robot);
        Behavior scanBeh = new ScanObjectBehavior(robot);
        Behavior goHomeBehavior = new GoHomeBehavior(robot);
        Behavior carryToUserBeh = new CarryToUserBehavior(robot);
        Behavior abortBeh = new AbortBehavior(robot);
        Behavior waitForCommandBeh = new WaitForCommandBehavior(robot);
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};
        Arbitrator arb = new Arbitrator(bArray);
        arb.go();

    }

}
