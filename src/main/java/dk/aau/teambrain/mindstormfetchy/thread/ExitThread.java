package dk.aau.teambrain.mindstormfetchy.thread;

import lejos.hardware.Button;

public class ExitThread extends Thread {

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
