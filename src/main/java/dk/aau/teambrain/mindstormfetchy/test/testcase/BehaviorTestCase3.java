package dk.aau.teambrain.mindstormfetchy.test.testcase;

import dk.aau.teambrain.mindstormfetchy.behavior.AbortBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.GoHomeBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.ScanObjectBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.WaitForCommandBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;

import java.util.ArrayList;
import java.util.Arrays;

// Abort after scan use case
public class BehaviorTestCase3 extends BaseBehaviorTestCase {

    @Override
    protected String getTag() {
        return "Test_Case_3";
    }

    public BehaviorTestCase3(TestRobot robot) {
        super(robot);

        states = new ArrayList<>(Arrays.asList(
                WaitForCommandBehavior.TAG,
                ScanObjectBehavior.TAG,
                AbortBehavior.TAG,
                GoHomeBehavior.TAG
        ));
    }

    @Override
    public void executeStep(String behavior) {
        super.executeStep(behavior);

        if (behavior.equals(WaitForCommandBehavior.TAG)) {
            Task task = new Task(ColorSensorWrapper.colorName(Color.BLUE));
            robot.onNewTask(task);
        } else if (behavior.equals(ScanObjectBehavior.TAG)) {
            robot.onAbortTask();
        }
    }
}
