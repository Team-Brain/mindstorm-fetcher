package dk.aau.teambrain.mindstormfetchy.test.testcase;

import dk.aau.teambrain.mindstormfetchy.behavior.WaitForCommandBehavior;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.Log;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class BaseBehaviorTestCase {

    // TestRobot reference to set-up the environment
    protected TestRobot robot;

    // List of expected states
    protected List<String> states;

    // Number of wrongly colored objects before the correct one
    protected int wrongObjects = 0;
    // Number of scanning attempts
    protected int wrongObjectAttempts = 0;

    protected abstract String getTag();

    public BaseBehaviorTestCase(TestRobot robot) {
        this.robot = robot;
    }

    /**
     * Checks whether expected behaviour matches the actual one.
     *
     * @param behavior actual robot's behavior
     */
    public void executeStep(String behavior) {
        if (behavior.equals(WaitForCommandBehavior.TAG)) {
            System.out.println("STARTING TEST CASE : " + getTag());
        }
        System.out.println(behavior);
        String expected = states.remove(0);
        try {
            assert expected.equals(behavior);
        } catch (AssertionError e) {
            System.out.println("TEST CASE : " + getTag() + " FAIL");
            throw new AssertionError("Expected [" + expected + "] Got [" + behavior + "]");
        }
        if (states.isEmpty()) {
            System.out.println("TEST CASE : " + getTag() + " OK");
        }
    }

}