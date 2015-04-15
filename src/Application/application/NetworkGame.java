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

    public NetworkGame (boolean host) throws IOException {
        game = Game.getInstance();
        if (host) {
            this.host = new Host();
            this.host.listen();
        } else {
            client = new Client();
            System.out.println("What is the host address?");
            Scanner reader = new Scanner(System.in);
            String addr = reader.nextLine();
            client.connect(addr);
        }
    }

}
