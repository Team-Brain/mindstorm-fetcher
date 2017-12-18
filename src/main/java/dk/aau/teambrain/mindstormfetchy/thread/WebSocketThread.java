package dk.aau.teambrain.mindstormfetchy.thread;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.robot.TaskHandler;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * WebSocketThread connects to the cloud app using the Socket.IO WebSocket and
 * listens to events.
 * This class handles all the communication with the cloud application.
 */
public class WebSocketThread extends Thread {

    private static final String SOCKET_URL = "http://fetchy-dialogflow-webhook.herokuapp.com/";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static Socket socket;

    private static boolean lostConnection = false;

    private TaskHandler robot;

    public WebSocketThread(TaskHandler robot) {
        this.setDaemon(true);
        this.robot = robot;
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
                    Task task = JSON_FACTORY
                            .createJsonParser(String.valueOf(args[0]))
                            .parse(Task.class);
                    Log.d(task.toString());
                    robot.onNewTask(task);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).on("abort", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Log.d("abort");
                robot.onAbortTask();
            }
        });

        Log.i("Connecting to SocketIO server..");
        socket.connect();
    }

    public static boolean isLostConnection() {
        return lostConnection;
    }

    public static void notifyRequestCompleted() {
        if (socket != null && socket.connected()) {
            socket.emit("task_finished");
        }
    }

}
