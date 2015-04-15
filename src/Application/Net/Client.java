package Net;

import application.IPlayer;
import application.NetworkGame;
import application.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by kelvin on 14/04/15.
 */
public class Client {
    private static final int CONNECTION_TIME_OUT = 30000;
    private Socket socket;
    private IPlayer player;

    public Client() {
        socket = new Socket();
        this.player = new Player();
    }

    public void connect(String addr) throws IOException {
        socket.connect(new InetSocketAddress(addr, NetworkGame.PORT), CONNECTION_TIME_OUT);
    }

    public IPlayer getPlayer() {
        return player;
    }

}
