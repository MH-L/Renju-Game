package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import algorithm.BoardChecker;
import algorithm.ContOpenPattern;
import algorithm.Pattern;

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
		assertEquals(algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 3)
						.size(), 1);
		assertEquals(algorithm.BoardChecker.checkOpenPatDisc(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(3,3), true);
		board.updateBoard(new BoardLocation(4,4), true);
		board.updateBoard(new BoardLocation(6,6), true);
		board.updateBoard(new BoardLocation(7,7), true);
		assertEquals(algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 1);
		assertEquals(algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 3)
				.size(), 0);
		board.updateBoard(new BoardLocation(8,8), false);
		assertEquals(algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 0);
		board.withdrawMove(new BoardLocation(8,8));
		board.updateBoard(new BoardLocation(9,9), false);
		assertEquals(algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 1);
	}

	@Test
	public void testCheckContOpenPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		board.updateBoard(new BoardLocation(2, 4), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 0);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 4, board).size(), 1);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(3),
						3, Pattern.ON_ROW, true, 4, board).size(), 0);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_COL, true, 4, board).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(4, 4), true);
		board.updateBoard(new BoardLocation(5, 5), true);
		board.updateBoard(new BoardLocation(6, 6), true);
		board.updateBoard(new BoardLocation(8, 8), true);
		board.updateBoard(new BoardLocation(9, 9), false);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(15), 15, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(14), 16, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(16), 16, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				algorithm.BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(15), 15, Pattern.ON_ULDIAG,
						true, 4, board).size(), 0);
		assertEquals(BoardChecker.checkAllPatterns(board, true).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(3,10), true);
		board.updateBoard(new BoardLocation(2,10), true);
		board.updateBoard(new BoardLocation(1,10), true);
		board.updateBoard(new BoardLocation(0,10), true);
		board.updateBoard(new BoardLocation(5,10), false);
		assertEquals(BoardChecker.checkAllPatterns(board, true).size(), 1);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 4).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(14,0), true);
		board.updateBoard(new BoardLocation(13,1), true);
		board.updateBoard(new BoardLocation(12,2), true);
		board.updateBoard(new BoardLocation(11,3), true);
		board.updateBoard(new BoardLocation(9,5), false);
		assertEquals(BoardChecker.checkAllPatterns(board, true).size(), 1);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 4).size(), 1);
		board.withdrawMove(new BoardLocation(11,3));
		board.withdrawMove(new BoardLocation(9,5));
		board.updateBoard(new BoardLocation(10,4), true);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 3).size(), 0);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 4).size(), 0);
		board.updateBoard(new BoardLocation(11,3), true);
		board.withdrawMove(new BoardLocation(13,1));
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 3).size(), 0);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 4).size(), 0);
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
		board.updateBoard(new BoardLocation(4, 5), false);
		board.updateBoard(new BoardLocation(5, 6), false);
		board.updateBoard(new BoardLocation(6, 7), false);
		board.updateBoard(new BoardLocation(7, 8), false);
		assertEquals(BoardChecker.checkBoardClosedPatDisc(board, false, 4)
				.size(), 0);
		board.withdrawMove(new BoardLocation(4, 5));
		board.updateBoard(new BoardLocation(8, 9), true);
		board.updateBoard(new BoardLocation(3, 4), false);
		assertEquals(BoardChecker.checkBoardClosedPatDisc(board, false, 4)
				.size(), 1);
		assertEquals(BoardChecker.checkBoardClosedPatDisc(board, false, 5)
				.size(), 0);
		Pattern pat = BoardChecker.checkBoardClosedPatDisc(board, false, 4)
				.get(0);
		assertEquals(pat.getLocations().get(0), new BoardLocation(3, 4));
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
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.get(0).getLocations().get(0), new BoardLocation(4, 4));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.get(0).getLocations().get(1), new BoardLocation(5, 5));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.get(0).getLocations().get(2), new BoardLocation(6, 6));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.get(0).getLocations().get(3), new BoardLocation(7, 7));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.get(0).getLocations().size(), 4);
		board.withdrawMove(new BoardLocation(5, 5));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.size(), 0);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 3)
				.size(), 0);
		board.updateBoard(new BoardLocation(3, 3), true);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4)
				.size(), 0);
		board.reset();
		board.updateBoard(new BoardLocation(4, 4), false);
		board.updateBoard(new BoardLocation(5, 5), false);
		board.updateBoard(new BoardLocation(6, 6), false);
		board.updateBoard(new BoardLocation(7, 7), false);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 0);
		board.updateBoard(new BoardLocation(8, 8), true);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 1);
		// test some composite ones.
		board.updateBoard(new BoardLocation(4, 5), false);
		board.updateBoard(new BoardLocation(4, 6), false);
		board.updateBoard(new BoardLocation(4, 7), false);
		board.updateBoard(new BoardLocation(4, 8), false);
		board.updateBoard(new BoardLocation(4, 9), true);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 1);
		board.withdrawMove(new BoardLocation(4, 8));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 1);
		board.withdrawMove(new BoardLocation(4, 4));
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 0);
		board.updateBoard(new BoardLocation(4, 8), true);
		board.updateBoard(new BoardLocation(4, 4), false);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, false, 4)
				.size(), 2);
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
		board.renderBoard(2);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 3);
		board.updateBoard(new BoardLocation(5, 4), false);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 4);
		board.updateBoard(new BoardLocation(5, 6), true);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 3);
		board.updateBoard(new BoardLocation(6, 4), false);

		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 5);
		board.updateBoard(new BoardLocation(7, 5), false);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 7);
		board.updateBoard(new BoardLocation(8, 7), false);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 7);
		board.updateBoard(new BoardLocation(5, 7), false);
		assertEquals(BoardChecker.checkAllPatterns(board, false).size(), 8);
		board.reset();
		board.updateBoard(new BoardLocation(12, 11), true);
		board.updateBoard(new BoardLocation(13, 10), true);
		board.updateBoard(new BoardLocation(14, 9), true);
		ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board, true);
		assertEquals(patterns.size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(2, 5), true);
		board.updateBoard(new BoardLocation(3, 6), true);
		board.updateBoard(new BoardLocation(5, 8), true);
		board.updateBoard(new BoardLocation(6, 9), true);
		board.updateBoard(new BoardLocation(1, 4), false);
		patterns = BoardChecker.checkAllPatterns(board, true);
		assertEquals(patterns.size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(4,4), true);
		board.updateBoard(new BoardLocation(5,5), true);
		board.updateBoard(new BoardLocation(7,7), true);
		board.updateBoard(new BoardLocation(8,8), false);
		patterns = BoardChecker.checkAllPatterns(board, true);
		assertEquals(patterns.size(), 0);
		board.updateBoard(new BoardLocation(6,6), true);
		patterns = BoardChecker.checkAllPatterns(board, true);
		assertEquals(patterns.size(), 1);
		board.updateBoard(new BoardLocation(3,3), false);
		patterns = BoardChecker.checkAllPatterns(board, true);
		assertEquals(patterns.size(), 0);
	}

	@Test
	public void testMakeContiguousPatterns() {
		BoardLocation testLocation = new BoardLocation(1, 2);
		Pattern newPat = BoardChecker.makeContiguousPattern(testLocation,
				Pattern.ON_ROW, 4, false, board);
		assertEquals(new BoardLocation(1, 2), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(1, 3), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(1, 4), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(1, 5), newPat.getLocations().get(3));
		newPat = BoardChecker.makeContiguousPattern(testLocation,
				Pattern.ON_COL, 4, false, board);
		assertEquals(new BoardLocation(1, 2), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(2, 2), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(3, 2), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(4, 2), newPat.getLocations().get(3));
		newPat = BoardChecker.makeContiguousPattern(testLocation,
				Pattern.ON_ULDIAG, 4, false, board);
		assertEquals(new BoardLocation(1, 2), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(2, 3), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(3, 4), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(4, 5), newPat.getLocations().get(3));
		testLocation = new BoardLocation(3, 3);
		newPat = BoardChecker.makeContiguousPattern(testLocation,
				Pattern.ON_URDIAG, 4, false, board);
		assertEquals(new BoardLocation(3, 3), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(4, 2), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(5, 1), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(6, 0), newPat.getLocations().get(3));
		newPat = BoardChecker.makeDiscPattern(testLocation, Pattern.ON_ROW, 2,
				4, false, board);
		assertEquals(new BoardLocation(3, 3), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(3, 4), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(3, 6), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(3, 7), newPat.getLocations().get(3));
		newPat = BoardChecker.makeDiscPattern(testLocation, Pattern.ON_COL, 2,
				4, false, board);
		assertEquals(new BoardLocation(3, 3), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(4, 3), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(6, 3), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(7, 3), newPat.getLocations().get(3));
		newPat = BoardChecker.makeDiscPattern(testLocation, Pattern.ON_ULDIAG,
				2, 4, false, board);
		assertEquals(new BoardLocation(3, 3), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(4, 4), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(6, 6), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(7, 7), newPat.getLocations().get(3));
		testLocation = new BoardLocation(4, 4);
		newPat = BoardChecker.makeDiscPattern(testLocation, Pattern.ON_URDIAG,
				2, 4, false, board);
		assertEquals(new BoardLocation(4, 4), newPat.getLocations().get(0));
		assertEquals(new BoardLocation(5, 3), newPat.getLocations().get(1));
		assertEquals(new BoardLocation(7, 1), newPat.getLocations().get(2));
		assertEquals(new BoardLocation(8, 0), newPat.getLocations().get(3));
	}

	@Test
	public void testPatternInControl() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(4, 5), true);
		board.updateBoard(new BoardLocation(4, 6), true);
		board.updateBoard(new BoardLocation(4, 7), true);
		board.updateBoard(new BoardLocation(4, 9), false);
		board.updateBoard(new BoardLocation(4, 3), false);
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		locations.add(new BoardLocation(4, 5));
		locations.add(new BoardLocation(4, 6));
		locations.add(new BoardLocation(4, 7));
		ContOpenPattern checker = new ContOpenPattern(locations,
				Pattern.ON_ROW, null);
		assertTrue(BoardChecker.isOpenPatInControl(board, checker, true));
		board.withdrawMove(new BoardLocation(4, 9));
		assertFalse(BoardChecker.isOpenPatInControl(board, checker, true));
	}
}
