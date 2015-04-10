package Application;

import Exceptions.MultipleWithdrawException;
import Exceptions.OverWithdrawException;
import Exceptions.WithdrawException;
import Model.Board;
import Model.BoardLocation;

/**
 * This class records the recent moves of the player.
 * @author Minghao Liu
 *
 */
public class Player implements IPlayer{
	private BoardLocation lastMove;
	private int num_hints;
	private int num_regrets;
	private boolean first;

	public Player() {
		this.num_hints = 3;
		this.num_regrets = 3;
		this.lastMove = Model.Board.getInvalidBoardLocation();
		this.first = false;
	}

	public Player(boolean first) {
		this.num_hints = 3;
		this.num_regrets = 3;
		this.lastMove = Model.Board.getInvalidBoardLocation();
		this.first = first;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		if (this.lastMove.isReachable()) {
			if (this.num_regrets > 0) {
				num_regrets --;
				// TODO refactor
				lastMove = Model.Board.getInvalidBoardLocation();
				return true;
			}
			else
				throw new OverWithdrawException("You don't have any withdrawals left!");
		} else
			throw new MultipleWithdrawException("You cannot withdraw two times!");
	}

	@Override
	public void makeMove(BoardLocation location) {
		lastMove = location;
	}

	@Override
	public void forceWithdraw() {
		lastMove = Model.Board.getInvalidBoardLocation();
	}


}
