package dk.aau.teambrain.mindstormfetchy.thread;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.State;
import lejos.hardware.Button;

public class ExitThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (Button.ESCAPE.isDown()) {
                System.exit(200);
            }
            if(Button.ENTER.isDown()) {
                Fetchy.currentState = dk.aau.teambrain.mindstormfetchy.State.ABORT;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
