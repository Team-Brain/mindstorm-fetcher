package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.thread.ExitThread;

public class Main {

    public static void main(String[] args) throws Exception {

        // Start the exit listener thread
        new ExitThread().start();

        // Start the socketIO thread
//        new SocketIoThread().start();

        // Initialize robot
        Fetchy.init();

    }

}
