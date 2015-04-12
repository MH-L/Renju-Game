package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.BoardLocation;

public interface IPlayer {
	BoardLocation makeMove() throws InvalidIndexException;
	boolean withdraw() throws WithdrawException;
	void forceWithdraw();

}
