package application;

import model.Board;
import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import algorithm.Algorithm;

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
		this.lastMove = model.Board.getInvalidBoardLocation();
	}

	public static AI getInstance() {
		if (instance == null)
			instance = new AI();
		return instance;
	}

	public static void initAI(int difficulty, Board board){
		// TODO Board param may not be needed if game board is static
		AI.difficulty = difficulty;
		AI.board = board;
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
		BoardLocation nextMove = null;
		// TODO
		return nextMove;
	}
}