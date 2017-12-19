package dk.aau.teambrain.mindstormfetchy.thread;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lejos.utility.Stopwatch;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * WebSocketThread connects to the cloud app using the Socket.IO WebSocket and
 * listens to events.
 * This class handles all the communication with the cloud application.
 */
public class WebSocketThread extends Thread {

    private static final String SOCKET_URL = "http://fetchy-webhook.herokuapp.com/";
    //    private static final String SOCKET_URL = "http://172.17.184.130:3000";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static Socket socket;

    private static BaseRobot baseRobot;
    private Stopwatch watch;

    public WebSocketThread(BaseRobot robot) {
        setDaemon(true);
        baseRobot = robot;
    }

    public static boolean isConnected() {
        return socket != null && socket.connected();
    }

    @Override
    public void run() {
        try {
            socket = IO.socket(SOCKET_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(500);
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.i("Connection successful.");
                Log.i("Took: " + watch.elapsed() + "ms");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Log.i("Connection error.");
            }
        }).on("task", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    Task task = JSON_FACTORY.createJsonParser(String.valueOf(args[0]))
                            .parse(Task.class);
                    Log.i(task.getColor());
                    // No task execution
                    if (baseRobot.getCurrentTask() == null) {
                        baseRobot.onNewTask(task);
                    } else if (baseRobot.getCurrentTask().isFinished()) {
                        notifyTaskCompleted();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).on("abort", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Log.i("abort");
                baseRobot.onAbortTask();
            }
        });

        Log.i("Connecting to SocketIO server..");
        watch = new Stopwatch();
        socket.connect();
    }

    public static void notifyTaskCompleted() {
        if (socket != null && socket.connected()) {
            socket.emit("task_finished", baseRobot.getCurrentTask().getId(), new Ack() {
                @Override
                public void call(Object... objects) {
                    baseRobot.clearCurrentTask();
                    Log.i("Finished ack");
                }
            });
        }
    }

}
