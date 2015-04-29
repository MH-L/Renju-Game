package application;

import application.command.Command;
import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

public class MockAI implements IPlayer {

	public BoardLocation makeMove() throws InvalidIndexException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean withdraw() throws WithdrawException {
		// TODO Auto-generated method stub
		return false;
	}

	public void forceWithdraw() {
		// do nothing
	}

	public BoardLocation getLastMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command getCommand() {
		return null;
	}

}
