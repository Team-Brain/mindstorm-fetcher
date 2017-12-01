package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.thread.ExitThread;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;

public class Main {

    public static final boolean DEBUG = false;

    public static void main(String[] args) throws Exception {

        // Start the exit listener thread
        new ExitThread().start();

        // Start the socketIO thread
        if (!DEBUG) {
            new SocketIoThread().start();
        }

        // Initialize robot
        Fetchy.init();

    }

}
