package dk.aau.teambrain.mindstormfetchy.test.testcase;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;

import java.util.ArrayList;
import java.util.Arrays;

// 
public class BehaviorTestCase6 extends BaseBehaviorTestCase {

    @Override
    protected String getTag() {
        return "Test_Case_6";
    }

    public BehaviorTestCase6(TestRobot robot) {
        super(robot);

        states = new ArrayList<>(Arrays.asList(
                WaitForCommandBehavior.TAG,
                SearchBehavior.TAG,
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
            robot.setIrDistance(Integer.MAX_VALUE);
        } else if (behavior.equals(SearchBehavior.TAG)) {
            robot.onAbortTask();
        }
    }
}
