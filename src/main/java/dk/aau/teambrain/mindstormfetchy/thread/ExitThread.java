package dk.aau.teambrain.mindstormfetchy.thread;

import lejos.hardware.Button;

/**
 * Exit threads runs as a daemon in the background and listens to
 * the Button.ESCAPE press to exit the current program.
 */
public class ExitThread extends Thread {

    public ExitThread() {
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            if (Button.ESCAPE.isDown()) {
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
