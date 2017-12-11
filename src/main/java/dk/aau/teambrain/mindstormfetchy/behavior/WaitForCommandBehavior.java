package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.Main;
import lejos.utility.Delay;

public class WaitForCommandBehavior extends BaseBehavior {

    @Override
    protected String getName() {
        return "WaitForCommand";
    }

    @Override
    public boolean takeControl() {
        return !Fetchy.hasTask();
    }

    @Override
    public void action() {
        super.action();
//        if (SocketIoThread.isLostConnection()) {
//            System.exit(500);
//        }
        if (Main.DEBUG) {
            Delay.msDelay(2000);
            Fetchy.createDemoRequest();
        } else {
            while (!Fetchy.hasTask()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
