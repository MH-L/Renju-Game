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

    public static final String OP_MOVE = "M";       // place new stone. msg: board location
    public static final String OP_WITHDRAW = "W";   // withdraw player's last stone

    /**
     * Create a new game between two players on separate networks.
     * @param host
     *      true if this player is the host
     *      false if this player is the client
     */
    public Network(boolean host) {
        super();
        boolean connectionSuccess = false;
        while (!connectionSuccess){
            try {
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
                connectionSuccess = true;
            } catch (IOException e) {
                System.out.println("Connection failed. Please try again");
            }
        }
    }

    private void addPlayer(String userID) {
    }

    private void receiveMessage(String userID, Object message) {
        // apply message to the board
    }

    private void playerDisconnected(String userID, Object message) {
        // run when a player disconnects
    }

}
