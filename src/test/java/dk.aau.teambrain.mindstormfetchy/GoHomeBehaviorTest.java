package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.GoHomeBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class GoHomeBehaviorTest {

    private GoHomeBehavior behavior;
    private TestRobot robot;

    @Before
    public void setUp() throws Exception {
        robot = new TestRobot();
        Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
        robot.onNewTask(task);
        robot.setCurrentState(State.GOING_HOME);
        behavior = new GoHomeBehavior(robot);
    }

    @Test
    public void testCarryingObject() throws Exception {
        robot.carryingObject = true;
        behavior.action();
        assertEquals(State.CARRY_TO_USER, robot.getCurrentState());
    }

    @Test
    public void testNotCarryingObject() throws Exception {
        robot.carryingObject = false;
        behavior.action();
        assertEquals(State.WAITING_FOR_COMMAND, robot.getCurrentState());
    }

}