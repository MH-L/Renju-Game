package application;

import algorithm.*;
import application.command.Command;
import application.command.Move;
import application.game.Game.Difficulty;
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

	private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.ULTIMATE;
	private Difficulty difficulty = DEFAULT_DIFFICULTY;
	private Board board;
	private Algorithm solver;

	public AI(Difficulty difficulty, Board board, boolean isFirst) {
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

	public BoardLocation makeMove() {
		// start to check for patterns after three moves.
		// which is five or six stones on the board.
		BoardLocation nextMove = null;
		if (board.getTotalStones() < 10)
			nextMove = solver.makeMoveBeginning();
		// TODO modify this function since it is not complete.
		else
			nextMove = solver.makeMoveEnd();
		System.out.format("AI: I got (%d, %d) for this move.\n", nextMove.getXPos(), nextMove.getYPos());
		return nextMove;
	}

	@Override
	public Command getCommand() {
		return new Move(this, makeMove());
	}
}