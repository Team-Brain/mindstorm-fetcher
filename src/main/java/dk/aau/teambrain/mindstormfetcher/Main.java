package dk.aau.teambrain.mindstormfetcher;

import dk.aau.teambrain.mindstormfetcher.socket.SocketIo;
import lejos.hardware.Button;

public class Main {

    public static void main(String[] args) throws Exception {

        SocketIo.run();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Button.ESCAPE.isDown()) {
                        System.exit(200);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).run();


        Button.waitForAnyEvent();

    }


}
