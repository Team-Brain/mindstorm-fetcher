package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.utility.Stopwatch;

public class SearchBehavior extends BaseBehavior {

    private static final int TIMEOUT_SEARCH = 20 * 1000;

    public SearchBehavior(BaseRobot robot) {
        super(robot);
    }

    @Override
    protected String getName() {
        return "Search";
    }

    public boolean takeControl() {
        return robot.hasTask();
    }

    public void action() {
        super.action();
        Stopwatch stopwatch = new Stopwatch();
        suppressed = false;
        robot.forward();
        while (!suppressed) {
            try {
                if (stopwatch.elapsed() > TIMEOUT_SEARCH) {
                    robot.setCurrentState(State.ABORT);
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        robot.stop();
    }

}