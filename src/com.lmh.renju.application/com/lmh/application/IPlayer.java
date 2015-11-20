package com.lmh.application;

import com.lmh.exception.InvalidIndexException;
import com.lmh.exception.WithdrawException;
import com.lmh.model.BoardLocation;

public interface IPlayer {
	BoardLocation makeMove() throws InvalidIndexException;
	boolean withdraw() throws WithdrawException;
	void forceWithdraw();
	public BoardLocation getLastMove();

}
