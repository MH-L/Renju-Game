package renju.com.lmh.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import renju.com.lmh.application.Network;
import renju.com.lmh.application.Player;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.exception.WithdrawException;
import renju.com.lmh.model.BoardLocation;

/**
 * Created by kelvin on 14/04/15.
 */
public class Host extends Player {
    private ServerSocket socket;

    public Host () throws IOException {
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
        System.out.println("Your rival has connected successfully.");
    }

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		// TODO Auto-generated method stub
		// Need to send the result to the connected socket after
		// prompting the player for his input.
		return null;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		// TODO Auto-generated method stub
		// In addition to withdraw method of the superclass,
		// still need to send that to the connected socket.
		return false;
	}

	@Override
	public void forceWithdraw() {}

}
