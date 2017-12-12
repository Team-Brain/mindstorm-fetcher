package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.State;

public class GoHomeBehavior extends BaseBehavior {

    public GoHomeBehavior(BaseRobot robot) {
        super(robot);
    }

    @Override
    protected String getName() {
        return "GoHome";
    }

    @Override
    public boolean takeControl() {
        return robot.getCurrentState() == State.GOING_HOME;
    }

    @Override
    public void action() {
        super.action();
        suppressed = false;
        robot.goToStart();
        while (!robot.pathCompleted() && !suppressed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (suppressed) {
            return;
        }
        if (robot.carryingObject) {
            robot.setCurrentState(State.CARRY_TO_USER);
        } else {
            if (robot.hasTask()) {
                robot.finishTask();
            }
            robot.setCurrentState(State.WAITING_FOR_COMMAND);
        }
        robot.stop();
    }

}
