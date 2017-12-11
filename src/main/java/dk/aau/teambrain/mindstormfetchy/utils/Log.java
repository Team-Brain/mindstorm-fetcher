package dk.aau.teambrain.mindstormfetchy.utils;

import dk.aau.teambrain.mindstormfetchy.Main;

public class Log {

    public static void d(String message) {
//        if (Main.DEBUG) {
            System.out.println(message);
//        }
    }

    public static void i(String message) {
        System.out.println(message);
    }
}
