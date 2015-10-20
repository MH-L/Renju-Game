package application;

import Net.Client;
import Net.Host;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by kelvin on 14/04/15.
 */
public class Network extends Game {

    public static final int PORT = 9321;
    public static final int HOST = 1;
    public static final int CLIENT = 2;

    private Host host;
    private Client client;

    /**
     * Create a new game between two players on separate networks.
     * @param host
     *      true if this player is the host
     *      false if this player is the client
     */
    public Network(boolean host) {
        player1 = new Player();
        player2 = new Player();
        activePlayer = player1;

        boolean connectionSuccess = false;
        while (!connectionSuccess){
            try {
                if (host) {
                    this.host = new Host();
                    this.host.listen();
                    // TODO get player2
                } else {
                    client = new Client();
                    System.out.println("What is the host address?");
                    Scanner reader = new Scanner(System.in);
                    String addr = reader.nextLine();
                    client.connect(addr);
                    // TODO get player1
                }
                connectionSuccess = true;
            } catch (IOException e) {
                System.out.println("Connection failed. Please try again");
            }
        }
    }

}
