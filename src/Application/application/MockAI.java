package application;

import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

public class MockAI implements IPlayer {

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
		// do nothing
	}

	@Override
	public BoardLocation getLastMove() {
		// TODO Auto-generated method stub
		return null;
	}

}
