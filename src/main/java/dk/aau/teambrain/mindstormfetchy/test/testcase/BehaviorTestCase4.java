package dk.aau.teambrain.mindstormfetchy.test.testcase;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;

import java.util.ArrayList;
import java.util.Arrays;

// User beacon timeout use case
public class BehaviorTestCase4 extends BaseBehaviorTestCase {

    @Override
    protected String getTag() {
        return "Test_Case_4";
    }

    public BehaviorTestCase4(TestRobot robot) {
        super(robot);

        states = new ArrayList<>(Arrays.asList(
                WaitForCommandBehavior.TAG,
                ScanObjectBehavior.TAG,
                GoHomeBehavior.TAG,
                CarryToUserBehavior.TAG,
                AbortBehavior.TAG,
                GoHomeBehavior.TAG
        ));
    }

    @Override
    public void executeStep(String behavior) {
        super.executeStep(behavior);

        if (behavior.equals(WaitForCommandBehavior.TAG)) {
            Task task = new Task(ColorSensorWrapper.colorName(Color.RED));
            robot.onNewTask(task);
            robot.setSeekerDistance(Integer.MAX_VALUE);
            robot.setSeekerDirection(0);
            robot.setColorId(Color.RED);
        }
    }
}
