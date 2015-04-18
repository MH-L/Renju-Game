package application;

import model.Board;
import model.BoardLocation;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import algorithm.AdvancedAlgorithm;
import algorithm.Algorithm;
import algorithm.BasicAlgorithm;
import algorithm.IntermediateAlgorithm;
import algorithm.UltimateAlgorithm;

/**
 * This is a class for the AI of the Renju game. It has different difficulty
 * levels.
 *
 * @author Minghao Liu
 *
 */
public class AI implements IPlayer {

	private static final int DEFAULT_DIFFICULTY = Game.ULTIMATE_DIFFICULTY;
	private static AI instance = null;
	private static int difficulty = DEFAULT_DIFFICULTY;
	private static Board board;
	private BoardLocation lastMove;
	private static Algorithm solver;

	private AI() {
		this.lastMove = model.Board.getInvalidBoardLocation();
	}

	public static AI getInstance() {
		if (instance == null)
			instance = new AI();
		return instance;
	}

	public static void initAI(int difficulty, Board board, boolean isFirst) {
		// TODO Board param may not be needed if game board is static
		AI.difficulty = difficulty;
		AI.board = board;
		switch (difficulty) {
		case Game.NOVICE_DIFFICULTY:
			AI.solver = new BasicAlgorithm(board, isFirst);
			break;
		case Game.INTERMEDIATE_DIFFICULTY:
			AI.solver = new IntermediateAlgorithm(board, isFirst);
			break;
		case Game.ADVANCED_DIFFICULTY:
			AI.solver = new AdvancedAlgorithm(board, isFirst);
			break;
		case Game.ULTIMATE_DIFFICULTY:
			AI.solver = new UltimateAlgorithm(board, isFirst);
		default:
			AI.solver = new UltimateAlgorithm(board, isFirst);
			break;
		}
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		// TODO Auto-generated method stub
		// Since AI will never withdraw, just do not do anything.
		this.lastMove = Board.getInvalidBoardLocation();
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
		lastMove = Board.getInvalidBoardLocation();
	}

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		// start to check for patterns after three moves.
		// which is five or six stones on the board.
		BoardLocation nextMove = null;
		if (board.getTotalStones() < 10)
			nextMove = solver.makeMoveBeginning();
		// TODO
		else
			nextMove = solver.makeMoveEnd();
		lastMove = nextMove;
		return nextMove;
	}

	@Override
	public BoardLocation getLastMove() {
		return lastMove;
	}
}