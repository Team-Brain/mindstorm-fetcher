package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Main;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import lejos.utility.Delay;

public class WaitForCommandBehavior extends BaseBehavior {

    public WaitForCommandBehavior(BaseRobot robot) {
        super(robot);
    }

    @Override
    protected String getName() {
        return "WaitForCommand";
    }

    @Override
    public boolean takeControl() {
        return !robot.hasTask();
    }

    @Override
    public void action() {
        super.action();
//        if (SocketIoThread.isLostConnection()) {
//            System.exit(500);
//        }
        if (Main.DEBUG) {
            Delay.msDelay(2000);
            robot.createDemoTask();
        } else {
            while (!robot.hasTask()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
