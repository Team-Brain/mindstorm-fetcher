package dk.aau.teambrain.mindstormfetchy.thread;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class ArbitratorThread extends Thread {

    private Arbitrator arb;

    public ArbitratorThread(BaseRobot robot) {

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior(robot);
        Behavior scanBeh = new ScanObjectBehavior(robot);
        Behavior goHomeBehavior = new GoHomeBehavior(robot);
        Behavior carryToUserBeh = new CarryToUserBehavior(robot);
        Behavior abortBeh = new AbortBehavior(robot);
        Behavior waitForCommandBeh = new WaitForCommandBehavior(robot);
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};
        arb = new Arbitrator(bArray);
    }

    @Override
    public void run() {
        super.run();
        arb.go();
    }
}
