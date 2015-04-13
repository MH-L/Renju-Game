package AlgorithmTest;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import Algorithm.Pattern;
import Exceptions.InvalidIndexException;
import Model.Board;
import Model.BoardLocation;

public class BoardCheckerTest {
	private Board board;

	@Before
	public void initBoard() {
		this.board = new Board();
	}

	@Test
	public void testCheckDiscPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		assertEquals(
				Algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 3)
						.size(), 1);
		assertEquals(
				Algorithm.BoardChecker.checkOpenPatDisc(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 1);
		board.reset();

	}

	@Test
	public void testCheckContPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		board.updateBoard(new BoardLocation(2, 4), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		assertEquals(
				Algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 0);
		assertEquals(
				Algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 4, board).size(), 1);
		assertEquals(
				Algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(3),
						3, Pattern.ON_ROW, true, 4, board).size(), 0);
		assertEquals(
				Algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_COL, true, 4, board).size(), 1);
	}
}
