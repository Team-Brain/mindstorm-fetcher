package dk.aau.teambrain.mindstormfetcher.thread;

import dk.aau.teambrain.mindstormfetcher.Fetchy;
import lejos.hardware.lcd.LCD;

/**
 * Robot monitor class that outputs sensor data onto LCD screen.
 */
public class RobotMonitorThread extends Thread {

    private static final int MONITOR_DELAY = 2000;

    public RobotMonitorThread() {
        this.setDaemon(true);
    }

    public void run() {
        while (true) {
            LCD.clear();
            LCD.drawString("Color = " + Fetchy.colorSensor.getColorID(), 0, 0);
            LCD.drawString("Distance = " + Fetchy.irSensor.getRange(), 0, 1);
            LCD.drawString("Movement = " + Fetchy.pilot.getLinearSpeed(), 0, 2);
            try {
                sleep(MONITOR_DELAY);
            } catch (Exception ignored) {
            }
        }
    }
}