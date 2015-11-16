package test.algorithmTest;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import algorithm.BoardTree;

public class BoardTreeTest {
	private Board testBoard;
	@Before
	public void initialize() throws InvalidIndexException {
		this.testBoard = new Board();
//		testBoard.updateBoard(new BoardLocation(7, 7), true);
//		testBoard.updateBoard(new BoardLocation(8, 7), false);
//		testBoard.updateBoard(new BoardLocation(8, 8), true);
//		testBoard.updateBoard(new BoardLocation(6, 6), false);
//		testBoard.updateBoard(new BoardLocation(6, 8), true);
//		testBoard.updateBoard(new BoardLocation(5, 9), false);
		testBoard.updateBoard(new BoardLocation(7, 7), true);
		testBoard.updateBoard(new BoardLocation(8, 8), false);
		testBoard.updateBoard(new BoardLocation(8, 6), true);
		testBoard.updateBoard(new BoardLocation(6, 8), false);
		testBoard.updateBoard(new BoardLocation(6, 6), true);
		testBoard.updateBoard(new BoardLocation(7, 8), false);
		testBoard.updateBoard(new BoardLocation(5, 8), true);
		testBoard.updateBoard(new BoardLocation(7, 9), false);
		System.out.println(testBoard.getSecondPattern().size());
	}

	@Test
	public void testAnalyseMoves() {
		BoardTree tree = new BoardTree(testBoard, Board.TURN_SENTE);
		BoardLocation loc = tree.getBestMove(3);
		System.out.println(String.format("The xcoordinate is %d, and the y is %d.",
				loc.getXPos(), loc.getYPos()));
	}
}
