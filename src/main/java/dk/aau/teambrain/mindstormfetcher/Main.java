package dk.aau.teambrain.mindstormfetcher;

import dk.aau.teambrain.mindstormfetcher.thread.ExitThread;
import dk.aau.teambrain.mindstormfetcher.thread.SocketIoThread;

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
