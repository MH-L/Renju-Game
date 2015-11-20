package renju.com.lmh.application;

import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.exception.WithdrawException;
import renju.com.lmh.model.BoardLocation;

public interface IPlayer {
	BoardLocation makeMove() throws InvalidIndexException;
	boolean withdraw() throws WithdrawException;
	void forceWithdraw();
	public BoardLocation getLastMove();

}
