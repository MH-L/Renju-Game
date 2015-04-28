package Net;

import application.Network;
import application.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by kelvin on 14/04/15.
 */
public class Host extends Player {
    private ServerSocket socket;

    public Host () throws IOException {
        super();
        socket = new ServerSocket(Network.PORT);
    }

    public void listen() throws IOException {
        // TODO improve getting IP to work over NAT
        InetAddress iAddr = InetAddress.getLocalHost();
        String addr = iAddr.getHostAddress();
        System.out.println("Your address is: " + addr);
        System.out.println("Give this address to your opponent.");
        System.out.println("\n.... Listening for a connection ....");
        socket.accept();
    }
}
