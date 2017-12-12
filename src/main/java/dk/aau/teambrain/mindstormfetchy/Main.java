package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.robot.TestRobot;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) throws Exception {

        // Start the exit listener thread
//        new ExitThread().start();

        // Initialize robot
        BaseRobot fetchy = new TestRobot();

        // Start the socketIO thread
        if (!DEBUG) {
            new SocketIoThread(fetchy).start();
        }

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior(fetchy);
        Behavior scanBeh = new ScanObjectBehavior(fetchy);
        Behavior goHomeBehavior = new GoHomeBehavior(fetchy);
        Behavior carryToUserBeh = new CarryToUserBehavior(fetchy);
        Behavior abortBeh = new AbortBehavior(fetchy);
        Behavior waitForCommandBeh = new WaitForCommandBehavior(fetchy);
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};
        Arbitrator arb = new Arbitrator(bArray);
        arb.go();

    }

}
