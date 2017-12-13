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

// Incorrect objects use case
public class BehaviorTestCase2 extends BaseBehaviorTestCase {

    @Override
    protected String getTag() {
        return "Test_Case_2";
    }

    public BehaviorTestCase2(TestRobot robot) {
        super(robot);

        wrongObjects = 1;

        states = new ArrayList<>(Arrays.asList(
                WaitForCommandBehavior.TAG,
                ScanObjectBehavior.TAG,
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
            robot.setColorId(Color.RED);
        } else if (behavior.equals(ScanObjectBehavior.TAG)) {
            if (wrongObjectAttempts == wrongObjects) {
                robot.setColorId(Color.BLACK);
            } else {
                wrongObjectAttempts++;
            }
        }
    }
}
