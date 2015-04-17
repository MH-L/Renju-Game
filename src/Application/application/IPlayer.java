package application;

import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

public interface IPlayer {
	BoardLocation makeMove() throws InvalidIndexException;
	boolean withdraw() throws WithdrawException;
	void forceWithdraw();
	public BoardLocation getLastMove();

}
