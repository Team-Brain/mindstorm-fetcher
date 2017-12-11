package dk.aau.teambrain.mindstormfetchy.thread;

import dk.aau.teambrain.mindstormfetchy.Fetchy;
import lejos.hardware.Button;

public class ExitThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (Button.ENTER.isDown()) {
                Fetchy.abortCurrentTask();
            }
            if (Button.ESCAPE.isDown()) {
                Fetchy.letGo();
                Fetchy.close();
                System.exit(200);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
