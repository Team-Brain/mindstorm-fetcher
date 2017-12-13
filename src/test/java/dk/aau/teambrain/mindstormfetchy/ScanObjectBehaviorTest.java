package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.ScanObjectBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScanObjectBehaviorTest {

    private ScanObjectBehavior behavior;
    private TestRobot robot;

    @Before
    public void setUp() {
        robot = new TestRobot();
        Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
        robot.onNewTask(task);
        behavior = new ScanObjectBehavior(robot);
    }

    @Test
    public void testSuccessScan() {
        robot.setColorId(Color.BLACK);
        behavior.action();

        assertEquals(State.GOING_HOME, robot.getCurrentState());
        assertEquals(true, robot.carryingObject);
    }

    @Test
    public void testFailScan() {
        robot.setColorId(Color.WHITE);
        behavior.action();

        assertEquals(State.SEARCHING, robot.getCurrentState());
        assertEquals(false, robot.carryingObject);
    }

}