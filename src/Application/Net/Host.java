package Net;

import Application.NetworkGame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by kelvin on 14/04/15.
 */
public class Host {
    private ServerSocket socket;

    public Host () throws IOException {
        socket = new ServerSocket(NetworkGame.PORT);
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
