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

	public static void initAI(int difficulty, Board board) {
		// TODO Board param may not be needed if game board is static
		AI.difficulty = difficulty;
		AI.board = board;
		switch (difficulty) {
		case Game.NOVICE_DIFFICULTY:
			AI.solver = new BasicAlgorithm(board);
			break;
		case Game.INTERMEDIATE_DIFFICULTY:
			AI.solver = new IntermediateAlgorithm(board);
			break;
		case Game.ADVANCED_DIFFICULTY:
			AI.solver = new AdvancedAlgorithm(board);
			break;
		case Game.ULTIMATE_DIFFICULTY:
			AI.solver = new UltimateAlgorithm(board);
		default:
			AI.solver = new UltimateAlgorithm(board);
			break;
		}
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
		// start to check for patterns after three moves.
		// which is five or six stones on the board.
		BoardLocation nextMove = null;
		if (board.getTotalStones() < 10)
			nextMove = solver.makeMoveBeginning();
		// TODO
		return nextMove;
	}
}