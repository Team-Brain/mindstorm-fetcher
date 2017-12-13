package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.CarryToUserBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarryToUserBehaviorTest {

    private CarryToUserBehavior behavior;
    private TestRobot robot;

    @Before
    public void setUp() throws Exception {
        robot = new TestRobot();
        Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
        robot.onNewTask(task);
        robot.setCurrentState(State.CARRY_TO_USER);
        robot.carryingObject = true;
        behavior = new CarryToUserBehavior(robot);
    }

    @Test
    public void testSuccess() throws Exception {
        behavior.action();
        assertEquals(false, robot.carryingObject);
        assertEquals(State.GOING_HOME, robot.getCurrentState());
    }

    @Test
    public void testTimeout() throws Exception {
        robot.setSeekerDistance(0);
        robot.setSeekerDirection(0);

        long start = System.currentTimeMillis();

        behavior.action();

        long total = System.currentTimeMillis() - start;

        assertEquals(State.ABORT, robot.getCurrentState());
        assertTrue(total > CarryToUserBehavior.TIMEOUT_BEACON_SIGNAL);

    }

}