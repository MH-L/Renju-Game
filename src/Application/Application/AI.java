package Application;

import Exceptions.WithdrawException;
import Model.BoardLocation;

/**
 * This is a class for the AI of the
 * Renju game. It has different difficulty levels.
 * @author Minghao Liu
 *
 */
public class AI implements IPlayer{
// Please do not forget to add a difficulty level field.

	private int difficulty;
	private static AI instance = null;

	private AI(int difficulty) {
		this.difficulty = difficulty;
	}

	public static AI getInstance(int difficulty) {
		if (instance == null)
			instance = new AI(difficulty);
		return instance;
	}

	@Override
	public void makeMove(BoardLocation location) {

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
}
