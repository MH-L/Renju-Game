package Application;

import Exceptions.MultipleWithdrawException;
import Exceptions.NothingToWithdrawException;
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
		this.lastMove = null;
		this.first = false;
	}

	public Player(boolean first) {
		this.num_hints = 3;
		this.num_regrets = 3;
		this.lastMove = null;
		this.first = first;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		if (this.lastMove == null)
			throw new NothingToWithdrawException("It is your first turn! You have nothing to withdraw!");
		else if (Model.Board.isReachable(lastMove)) {
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

	public int getRegrets() {
		return num_regrets;
	}

	public int getNumHints() {
		return num_hints;
	}


}
