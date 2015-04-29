package application;

import algorithm.*;
import application.command.Command;
import application.game.Game;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import model.Board;
import model.BoardLocation;

/**
 * This is a class for the AI of the Renju game. It has different difficulty
 * levels.
 *
 * @author Minghao Liu
 *
 */
public class AI implements IPlayer {

	private static final int DEFAULT_DIFFICULTY = Game.ULTIMATE_DIFFICULTY;
	private int difficulty = DEFAULT_DIFFICULTY;
	private Board board;
	private BoardLocation lastMove;
	private Algorithm solver;

	public AI(int difficulty, Board board, boolean isFirst) {
		this.lastMove = model.Board.getInvalidBoardLocation();
		// TODO Board param may not be needed if game board is static
		this.difficulty = difficulty;
		this.board = board;
		switch (this.difficulty) {
			case Game.NOVICE_DIFFICULTY:
				solver = new BasicAlgorithm(board, isFirst);
				break;
			case Game.INTERMEDIATE_DIFFICULTY:
				solver = new IntermediateAlgorithm(board, isFirst);
				break;
			case Game.ADVANCED_DIFFICULTY:
				solver = new AdvancedAlgorithm(board, isFirst);
				break;
			case Game.ULTIMATE_DIFFICULTY:
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
		if (board.getTotalStones() < 10)
			nextMove = solver.makeMoveBeginning();
		// TODO modify this function since it is not complete.
		else
			nextMove = solver.makeMoveEnd();
		lastMove = nextMove;
		System.out.format("AI: I got (%d, %d) for this move.\n", nextMove.getXPos(), nextMove.getYPos());
		return nextMove;
	}

	@Override
	public BoardLocation getLastMove() {
		return lastMove;
	}

	@Override
	public Command getCommand() {
		// TODO
		return null;
	}
}