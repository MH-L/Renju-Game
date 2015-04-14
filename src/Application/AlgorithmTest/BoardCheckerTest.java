package AlgorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import Algorithm.Algorithm;
import Algorithm.BoardChecker;
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
	public void testCheckDiscOpenPattern() throws InvalidIndexException {
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
	public void testCheckContOpenPattern() throws InvalidIndexException {
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

	@Test
	public void testCheckDiscClosedPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(4, 4), true);
		board.updateBoard(new BoardLocation(4, 5), true);
		board.updateBoard(new BoardLocation(4, 6), true);
		board.updateBoard(new BoardLocation(4, 8), true);
		board.updateBoard(new BoardLocation(4, 3), false);
		assertEquals(
				BoardChecker.checkClosedPatDisc(board.getRowByIndex(4), 4,
						Pattern.ON_ROW, true, 4, board).size(), 1);
		board.reset();
		assertTrue(board.isEmpty());
		for (int i = 0; i < 4; i++) {
			board.updateBoard(new BoardLocation(4 + i, 5 + i), false);
		}
		assertEquals(BoardChecker.checkBoardClosedPatDisc(board, false, 4)
				.size(), 0);
		board.withdrawMove(new BoardLocation(4, 5));
		board.updateBoard(new BoardLocation(8, 9), true);
		board.updateBoard(new BoardLocation(3, 4), false);
		assertEquals(BoardChecker.checkBoardClosedPatDisc(board, false, 4)
				.size(), 1);
	}

	@Test
	public void testCheckContClosedPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(4, 4), true);
		board.updateBoard(new BoardLocation(5, 5), true);
		board.updateBoard(new BoardLocation(6, 6), true);
		board.updateBoard(new BoardLocation(7, 7), true);
		board.updateBoard(new BoardLocation(8, 8), false);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.size(), 1);
	}

	@Test
	public void testCheckAllPatterns() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(5, 3), false);
		board.updateBoard(new BoardLocation(4, 4), false);
		board.updateBoard(new BoardLocation(5, 5), false);
		board.updateBoard(new BoardLocation(6, 6), false);
		board.updateBoard(new BoardLocation(7, 7), false);
		board.updateBoard(new BoardLocation(8, 8), true);
		board.updateBoard(new BoardLocation(6, 2), false);
		board.updateBoard(new BoardLocation(4, 5), false);
		board.updateBoard(new BoardLocation(4, 6), false);
		board.updateBoard(new BoardLocation(4, 7), false);
		board.updateBoard(new BoardLocation(4, 8), true);
		board.updateBoard(new BoardLocation(5, 8), true);
		board.updateBoard(new BoardLocation(6, 8), true);

		board.renderBoard(Board.FANCY_MODE);
		ArrayList<Pattern> pts = BoardChecker.checkAllPattern(board, false);
		assertEquals(BoardChecker.checkAllPattern(board, false).size(), 3);
	}

	@Test
	public void testMakePatterns() {
		BoardLocation testLocation = new BoardLocation(1,2);
		Pattern newPat = BoardChecker.makeContiguousPattern(testLocation, 0, 0, false, board);
	}
}
