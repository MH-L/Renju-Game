package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;
import Model.BoardLocation;

/**
 * This is a class for the AI of the
 * Renju game. It has different difficulty levels.
 * @author Minghao Liu
 *
 */
public class AI implements IPlayer{

	private static AI instance = null;
	private static int difficulty;
	private static Board board;
	private BoardLocation lastMove;

	private AI(int difficulty, Board board) {
		this.lastMove = Model.Board.getInvalidBoardLocation();
		this.difficulty = difficulty;
		this.board = board;
	}

	public static AI getInstance(int difficulty, Board board) {
		if (instance == null)
			instance = new AI(difficulty, board);
		return instance;
	}

	private static BoardLocation getNextMove(Board board) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		BoardLocation nextMove = AI.getNextMove(board);
		return nextMove;
	}
}
