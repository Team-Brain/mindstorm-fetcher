package dk.aau.teambrain.mindstormfetchy.utils;

import dk.aau.teambrain.mindstormfetchy.Main;

/**
 * Simple logger class.
 */
public class Log {

    /**
     * Log debug message.
     *
     * @param message Message to log.
     */
    public static void d(String message) {
        if (Main.DEBUG) {
            System.out.println(message);
        }
    }

    /**
     * Log info message.
     *
     * @param message Message to log.
     */
    public static void i(String message) {
        System.out.println(message);
    }
}
