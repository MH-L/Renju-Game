package Application;

import Algorithm.Algorithm;
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
	private Algorithm solver;

	private AI() {
		this.lastMove = Model.Board.getInvalidBoardLocation();
	}

	public static AI getInstance(int difficulty, Board board) {
		if (instance == null)
			instance = new AI();
		return instance;
	}

	@Override
	public void makeMove(BoardLocation location) {
		this.lastMove = location;
	}

	public void makeMove(Board board) {
		this.lastMove = AI.getNextMove(board);
		try {
			board.updateBoard(lastMove, false);
		} catch (InvalidIndexException e) {
			System.out.println("The system needs maintenance.");
		}
	}

	private static BoardLocation getNextMove(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		// TODO Auto-generated method stub
		// Since AI will never withdraw, just do not do anything.
		return false;
	}

	@Override
	public void forceWithdraw() {
		try {
			board.withdrawMove(lastMove);
		} catch (InvalidIndexException e) {
			e.printStackTrace();
		}
	}
}
