package Application;

import Exceptions.WithdrawException;
import Model.BoardLocation;

public interface IPlayer {
	public void makeMove(BoardLocation location);
	public boolean withdraw() throws WithdrawException;
	public void forceWithdraw();

}
