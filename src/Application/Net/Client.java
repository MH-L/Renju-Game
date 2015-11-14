package Net;

import application.IPlayer;
import application.Network;
import application.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

/**
 * Created by kelvin on 14/04/15.
 */
public class Client extends Player {
    private static final int CONNECTION_TIME_OUT = 30000;
    private Socket socket;

    public Client() {
        socket = new Socket();
    }

    public void connect(String addr) throws IOException {
        socket.connect(new InetSocketAddress(addr, Network.PORT), CONNECTION_TIME_OUT);
    }

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void forceWithdraw() {
		// TODO Auto-generated method stub

	}

	@Override
	public BoardLocation getLastMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
