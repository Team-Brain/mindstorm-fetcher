package dk.aau.teambrain.mindstormfetchy.thread;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import dk.aau.teambrain.mindstormfetchy.Fetchy;
import dk.aau.teambrain.mindstormfetchy.model.Request;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.net.URISyntaxException;

public class SocketIoThread extends Thread {

    private static final String SOCKET_URL = "http://fetchy-dialogflow-webhook.herokuapp.com/";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static Socket socket;

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
                System.out.println("Connection successful.");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("Connection error.");
            }
        }).on("request", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    Request request = JSON_FACTORY
                            .createJsonParser(String.valueOf(args[0]))
                            .parse(Request.class);
                    System.out.println(request);
                    Fetchy.requestQueue.add(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).on("abort", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Fetchy.currentState = dk.aau.teambrain.mindstormfetchy.State.ABORT;
//                Fetchy.requestQueue.remove(0);
            }
        });

        System.out.println("Connecting to SocketIO server..");
        socket.connect();
    }

}
