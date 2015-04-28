package Net;

import application.Network;
import application.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by kelvin on 14/04/15.
 */
public class Client extends Player {
    private static final int CONNECTION_TIME_OUT = 30000;
    private Socket socket;

    public Client() {
        super();
        socket = new Socket();
    }

    public void connect(String addr) throws IOException {
        socket.connect(new InetSocketAddress(addr, Network.PORT), CONNECTION_TIME_OUT);
    }

}
