package dk.aau.teambrain.mindstormfetchy.thread;

import dk.aau.teambrain.mindstormfetchy.robot.TaskHandler;
import lejos.hardware.Button;

public class ExitThread extends Thread {

    private TaskHandler robot;

    public ExitThread(TaskHandler taskHandler) {
        this.robot = taskHandler;
    }

    @Override
    public void run() {
        while (true) {
            if (Button.ENTER.isDown()) {
                robot.onAbortTask();
            }
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
