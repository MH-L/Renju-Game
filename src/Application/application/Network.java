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

    /**
     * Create a new game between two players on separate networks.
     * @param host
     *      true if this player is the host
     *      false if this player is the client
     * @throws IOException
     */
    public Network(boolean host) throws IOException {
        activePlayer = player1;

        boolean connectionSuccess = false;
        while (!connectionSuccess){
            try {
                if (host) {
                    this.player1 = new Host();
                    // This cast and the next one are violating
                    // abstraction principles, but it is Okay.
                    ((Host) this.player1).listen();
                    // TODO get player2
                } else {
                    this.player2 = new Client();
                    System.out.println("What is the host address?");
                    Scanner reader = new Scanner(System.in);
                    String addr = reader.nextLine();
                    ((Client) this.player2).connect(addr);
                    // TODO get player1
                }
                connectionSuccess = true;
            } catch (IOException e) {
                System.out.println("Connection failed. Please try again");
            }
        }
    }

}
