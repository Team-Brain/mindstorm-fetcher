package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.thread.ExitThread;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;

public class Main {

    public static void main(String[] args) throws Exception {

        // Start the socketIO thread
        new SocketIoThread().start();
        // Start the exit listener thread
        new ExitThread().start();

        // Initialize robot
        Fetchy.init();

    }


}
