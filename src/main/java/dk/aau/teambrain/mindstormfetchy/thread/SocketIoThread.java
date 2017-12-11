package dk.aau.teambrain.mindstormfetchy.thread;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.net.URISyntaxException;

public class SocketIoThread extends Thread {

    private static final String SOCKET_URL = "http://fetchy-dialogflow-webhook.herokuapp.com/";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static Socket socket;

    private static boolean lostConnection = false;

    public SocketIoThread() {
        this.setDaemon(true);
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
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Log.i("Disconnected.");
                lostConnection = true;
                Fetchy.abortCurrentTask();
            }
        }).on("task", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    Task task = JSON_FACTORY
                            .createJsonParser(String.valueOf(args[0]))
                            .parse(Task.class);
                    Log.d(task.toString());
                    Fetchy.setCurrentTask(task);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).on("abortCurrentTask", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Log.d("abortCurrentTask");
                Fetchy.abortCurrentTask();
            }
        });

        Log.i("Connecting to SocketIO server..");
        socket.connect();
    }

    public static boolean isLostConnection() {
        return lostConnection;
    }

    public static void notifyRequestCompleted() {
        if (socket.connected()) {
            socket.emit("task_finished");
        }
    }

}
