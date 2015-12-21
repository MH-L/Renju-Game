package renju.com.lmh.test;

import org.junit.Before;
import org.junit.Test;

import renju.com.lmh.algorithm.BoardTree;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

public class BoardTreeTest {
	private Board testBoard;
	@Before
	public void initialize() throws InvalidIndexException {
		this.testBoard = new Board(16);
//		testBoard.updateBoard(new BoardLocation(7, 7), true);
//		testBoard.updateBoard(new BoardLocation(8, 7), false);
//		testBoard.updateBoard(new BoardLocation(8, 8), true);
//		testBoard.updateBoard(new BoardLocation(6, 6), false);
//		testBoard.updateBoard(new BoardLocation(6, 8), true);
//		testBoard.updateBoard(new BoardLocation(5, 9), false);
		testBoard.updateBoardSolitaire(new BoardLocation(7, 8), true);
		testBoard.updateBoardSolitaire(new BoardLocation(8, 7), false);
		testBoard.updateBoardSolitaire(new BoardLocation(8, 6), true);
		testBoard.updateBoardSolitaire(new BoardLocation(6, 8), false);
		testBoard.updateBoardSolitaire(new BoardLocation(6, 6), true);
		testBoard.updateBoardSolitaire(new BoardLocation(9, 8), false);
		testBoard.updateBoardSolitaire(new BoardLocation(5, 8), true);
		testBoard.updateBoardSolitaire(new BoardLocation(7, 9), false);
		testBoard.updateBoardSolitaire(new BoardLocation(6, 7), true);
		testBoard.updateBoardSolitaire(new BoardLocation(4, 9), false);
		testBoard.renderBoard(Board.FANCY_MODE);
		System.out.println(testBoard.getSecondPattern().size());
	}

	@Test
	public void testAnalyseMoves() {
		BoardTree tree = new BoardTree(testBoard, Board.TURN_SENTE);
		BoardLocation loc = tree.getBestMove(6);
		System.out.println(String.format("The xcoordinate is %d, and the y is %d.",
				loc.getXPos(), loc.getYPos()));
		System.out.println(BoardTree.totalTimeElapsed);
	}

	@Test
	public void testEvaluateBoard() throws InvalidIndexException {
		double score = BoardTree.evalBoard(testBoard, Board.TURN_SENTE);
		System.out.println("The score is: " + score);
		testBoard.reset();
		testBoard.updateBoard(new BoardLocation(7, 7), true);
		testBoard.updateBoard(new BoardLocation(8, 7), false);
		testBoard.updateBoard(new BoardLocation(7, 8), true);
		testBoard.updateBoard(new BoardLocation(7, 6), false);
		testBoard.updateBoard(new BoardLocation(6, 6), true);
		testBoard.updateBoard(new BoardLocation(6, 7), false);
		testBoard.updateBoard(new BoardLocation(6, 8), true);
		testBoard.updateBoard(new BoardLocation(5, 8), false);
		testBoard.updateBoard(new BoardLocation(4, 9), true);
		testBoard.updateBoard(new BoardLocation(8, 8), false);
		testBoard.updateBoard(new BoardLocation(5, 7), true);
		testBoard.updateBoard(new BoardLocation(6, 9), false);
		testBoard.updateBoard(new BoardLocation(7, 9), true);
		testBoard.renderBoard(2);
		score = BoardTree.evalBoard(testBoard, Board.TURN_SENTE);
		System.out.println(score);
		score = BoardTree.evalBoard(testBoard, Board.TURN_GOTE);
		System.out.println(score);
		BoardTree tr = new BoardTree(testBoard, Board.TURN_GOTE);
		BoardLocation move = tr.getBestMove(6);
		System.out.println(String.format("The move's x coordinate is %d, and its y coordi"
				+ "nate is %d.", move.getXPos(), move.getYPos()));
	}
}
