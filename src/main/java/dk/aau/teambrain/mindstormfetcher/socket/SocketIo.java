package dk.aau.teambrain.mindstormfetcher.socket;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import dk.aau.teambrain.mindstormfetcher.model.Request;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.io.IOException;
import java.net.URISyntaxException;

public class SocketIo {

    private static String SOCKET_URL = "https://fetchy-dialogflow-webhook.herokuapp.com/";
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    public static Socket socket;

    public static void run() throws URISyntaxException {
        System.out.println("Running SocketIO version..");

        socket = IO.socket(SOCKET_URL);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connection to the socket successful.");
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Connecting to SocketIO server..");
        socket.connect();

    }
}
