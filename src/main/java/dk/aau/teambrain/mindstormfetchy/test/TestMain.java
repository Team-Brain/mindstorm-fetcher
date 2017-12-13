package dk.aau.teambrain.mindstormfetchy.test;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.TestDriver;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TestMain {

    public static void main(String[] args) throws Exception {

        // Initialize robot
        BaseRobot fetchy = new TestRobot();
        TestDriver testDriver = new TestDriver((TestRobot) fetchy);

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior(fetchy, testDriver);
        Behavior scanBeh = new ScanObjectBehavior(fetchy, testDriver);
        Behavior goHomeBehavior = new GoHomeBehavior(fetchy, testDriver);
        Behavior carryToUserBeh = new CarryToUserBehavior(fetchy, testDriver);
        Behavior abortBeh = new AbortBehavior(fetchy, testDriver);
        Behavior waitForCommandBeh = new WaitForCommandBehavior(fetchy, testDriver);
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};
        Arbitrator arb = new Arbitrator(bArray);
        arb.go();

    }

}
