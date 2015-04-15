package application;

import Net.Client;
import Net.Host;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by kelvin on 14/04/15.
 */
public class NetworkGame {

    public static final int PORT = 9321;
    public static final int HOST = 1;
    public static final int CLIENT = 2;

    private Game game;
    private Host host;
    private Client client;
    IPlayer player1;
    IPlayer player2;

    public NetworkGame (boolean host) throws IOException {
        game = Game.getInstance();
        game.initNetwork(player1, player2);

        if (host) {
            this.host = new Host();
            this.host.listen();
            player1 = this.host.getPlayer();
            // TODO get player2
        } else {
            client = new Client();
            System.out.println("What is the host address?");
            Scanner reader = new Scanner(System.in);
            String addr = reader.nextLine();
            client.connect(addr);
            player2 = client.getPlayer();
            // TODO get player1
        }
    }

    public IPlayer getPlayer1() {
        return player1;
    }

    public IPlayer getPlayer2() {
        return player2;
    }
}
