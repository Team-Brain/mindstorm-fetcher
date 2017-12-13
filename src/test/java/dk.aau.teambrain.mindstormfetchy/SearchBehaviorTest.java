package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.SearchBehavior;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.test.TestRobot;
import dk.aau.teambrain.mindstormfetchy.utils.ColorSensorWrapper;
import lejos.robotics.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchBehaviorTest {

    private SearchBehavior behavior;
    private TestRobot robot;

    @Before
    public void setUp() {
        robot = new TestRobot();
        Task task = new Task(ColorSensorWrapper.colorName(Color.BLACK));
        robot.onNewTask(task);
        behavior = new SearchBehavior(robot);
    }

    @Test
    public void testTimeout() throws Exception {
        long start = System.currentTimeMillis();

        behavior.action();

        long total = System.currentTimeMillis() - start;

        assertEquals(State.ABORT, robot.getCurrentState());
        assertTrue(total > SearchBehavior.TIMEOUT_SEARCH);
    }

}