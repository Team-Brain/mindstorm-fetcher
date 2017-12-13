package dk.aau.teambrain.mindstormfetchy.test.testcase;

import dk.aau.teambrain.mindstormfetchy.behavior.CarryToUserBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.GoHomeBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.ScanObjectBehavior;
import dk.aau.teambrain.mindstormfetchy.behavior.WaitForCommandBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;

import java.util.ArrayList;
import java.util.Arrays;

// Basic success use case
public class BehaviorTestCase1 extends BaseBehaviorTestCase {

    @Override
    protected String getTag() {
        return "Test_Case_1";
    }

    public BehaviorTestCase1(TestRobot robot) {
        super(robot);

        states = new ArrayList<>(Arrays.asList(
                WaitForCommandBehavior.TAG,
                ScanObjectBehavior.TAG,
                GoHomeBehavior.TAG,
                CarryToUserBehavior.TAG,
                GoHomeBehavior.TAG
        ));

    }

    @Override
    public void executeStep(String behavior) {
        super.executeStep(behavior);

        if (behavior.equals(WaitForCommandBehavior.TAG)) {
            Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
            robot.onNewTask(task);
            robot.setColorId(Color.BLACK);
        }
    }
}
