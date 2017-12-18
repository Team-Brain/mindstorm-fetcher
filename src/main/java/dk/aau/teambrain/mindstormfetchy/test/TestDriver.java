package dk.aau.teambrain.mindstormfetchy.test;

import dk.aau.teambrain.mindstormfetchy.behavior.WaitForCommandBehavior;
import dk.aau.teambrain.mindstormfetchy.test.testcase.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TestDriver listens to behavior changes and handles BehaviorTestCases.
 * When the robot switches its state to State.WAITING_FOR_COMMAND, the
 * test driver switches the current test case.
 */
public class TestDriver implements BehaviorChangeListener {

    // List of test cases to execute
    private List<BaseBehaviorTestCase> testCasesToRun = new ArrayList<>();

    // Currently executed test case
    private BaseBehaviorTestCase currentTestCase;

    public TestDriver(TestRobot robot) {
        testCasesToRun.add(new BehaviorTestCase1(robot));
        testCasesToRun.add(new BehaviorTestCase2(robot));
        testCasesToRun.add(new BehaviorTestCase3(robot));
        testCasesToRun.add(new BehaviorTestCase4(robot));
    }

    @Override
    public void onBehaviorChanged(String behaviorName) {
        // With every WaitForCommand start new test case
        if (behaviorName.equals(WaitForCommandBehavior.TAG)) {
            if (testCasesToRun.isEmpty()) {
                System.exit(0);
            } else {
                currentTestCase = testCasesToRun.remove(0);
            }
        }

        // Execute next step of currentTestCase
        currentTestCase.executeStep(behaviorName);
    }

}


