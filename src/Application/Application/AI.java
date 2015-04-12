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

	public static AI getInstance() {
		if (instance == null)
			instance = new AI();
		return instance;
	}

	public static void initAI(int difficulty, Board board){
		AI.difficulty = difficulty;
		AI.board = board;
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
			// TODO Auto-generated method stub
			board.withdrawMove(lastMove);
		} catch (InvalidIndexException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		BoardLocation nextMove = AI.getNextMove(board);
		return nextMove;
	}
}
