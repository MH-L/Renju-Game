package renju.com.lmh.application;

import renju.com.lmh.algorithm.AdvancedAlgorithm;
import renju.com.lmh.algorithm.Algorithm;
import renju.com.lmh.algorithm.BasicAlgorithm;
import renju.com.lmh.algorithm.IntermediateAlgorithm;
import renju.com.lmh.algorithm.UltimateAlgorithm;
import renju.com.lmh.application.Game.Difficulty;
import renju.com.lmh.application.IPlayer;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.exception.WithdrawException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

import static renju.com.lmh.application.Game.Difficulty.*;

/**
 * This is a class for the AI of the Renju game. It has different difficulty
 * levels.
 *
 * @author Minghao Liu
 *
 */
public class AI implements IPlayer {

	private static final Difficulty DEFAULT_DIFFICULTY = ULTIMATE;
	private Difficulty difficulty = DEFAULT_DIFFICULTY;
	private Board board;
	private BoardLocation lastMove;
	private Algorithm solver;

	public AI(Difficulty difficulty, Board board, boolean isFirst) {
		this.lastMove = Board.getInvalidBoardLocation();
		// TODO Board param may not be needed if game board is static
		this.difficulty = difficulty;
		this.board = board;
		switch (this.difficulty) {
			case NOVICE:
				solver = new BasicAlgorithm(board, isFirst);
				break;
			case INTERMEDIATE:
				solver = new IntermediateAlgorithm(board, isFirst);
				break;
			case ADVANCED:
				solver = new AdvancedAlgorithm(board, isFirst);
				break;
			case ULTIMATE:
				solver = new UltimateAlgorithm(board, isFirst);
			default:
				solver = new UltimateAlgorithm(board, isFirst);
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
		// Only do naive moves if the difficulty of the AI is intermediate or novice.
		if (difficulty == Difficulty.INTERMEDIATE || difficulty == Difficulty.NOVICE) {
			if (board.getTotalStones() < 10)
				nextMove = solver.makeMoveBeginning();
			// TODO modify this function since it is not complete.
			else
				nextMove = solver.makeMoveEnd();
		} else {
			nextMove = solver.makeMoveUsingGameTree();
		}
		lastMove = nextMove;
		System.out.format("AI: I got (%d, %d) for this move.\n", nextMove.getYPos(), nextMove.getXPos());
		return nextMove;
	}

	@Override
	public BoardLocation getLastMove() {
		return lastMove;
	}
}