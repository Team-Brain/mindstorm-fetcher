package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.AbortBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbortBehaviorTest {

    private AbortBehavior behavior;
    private TestRobot robot;

    @Before
    public void setUp() {
        robot = new TestRobot();
        Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
        robot.onNewTask(task);
        behavior = new AbortBehavior(robot);
    }

    @Test
    public void testAbort() {
        robot.carryingObject = true;
        behavior.action();

        assertEquals(false, robot.carryingObject);
        assertEquals(State.GOING_HOME, robot.getCurrentState());
    }

}