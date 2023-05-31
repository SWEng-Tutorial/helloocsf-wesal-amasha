package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.App;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.entities.App.session;


public class SimpleChatServer {

    private static SimpleServer server;

    public static void main(String[] args) throws IOException {
        server = new SimpleServer(3000);
        server.listen();
        App.main(args);
        System.out.println("server is listening");
    }
}