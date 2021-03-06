package renju.com.lmh.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import renju.com.lmh.algorithm.Algorithm;
import renju.com.lmh.algorithm.BoardChecker;
import renju.com.lmh.algorithm.ContOpenPattern;
import renju.com.lmh.algorithm.DiscOpenPattern;
import renju.com.lmh.algorithm.Pattern;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

public class BoardCheckerTest {
	private Board board;

	@Before
	public void initBoard() {
		this.board = new Board(16);
	}

	@Test
	public void testCheckDiscOpenPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		assertEquals(BoardChecker.checkBoardOpenPatDisc(board, true, 3)
						.size(), 1);
		assertEquals(BoardChecker.checkOpenPatDisc(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(3,3), true);
		board.updateBoard(new BoardLocation(4,4), true);
		board.updateBoard(new BoardLocation(6,6), true);
		board.updateBoard(new BoardLocation(7,7), true);
		assertEquals(BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 1);
		assertEquals(BoardChecker.checkBoardOpenPatDisc(board, true, 3)
				.size(), 0);
		board.updateBoard(new BoardLocation(8,8), false);
		assertEquals(BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 0);
		board.withdrawMove(new BoardLocation(8,8));
		board.updateBoard(new BoardLocation(9,9), false);
		assertEquals(BoardChecker.checkBoardOpenPatDisc(board, true, 4)
				.size(), 1);
	}

	@Test
	public void testCheckContOpenPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		board.updateBoard(new BoardLocation(2, 4), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		assertEquals(
				BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 3, board).size(), 0);
		assertEquals(
				BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_ROW, true, 4, board).size(), 1);
		assertEquals(
				BoardChecker.checkOpenPatCont(board.getRowByIndex(3),
						3, Pattern.ON_ROW, true, 4, board).size(), 0);
		assertEquals(
				BoardChecker.checkOpenPatCont(board.getRowByIndex(2),
						2, Pattern.ON_COL, true, 4, board).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(4, 4), true);
		board.updateBoard(new BoardLocation(5, 5), true);
		board.updateBoard(new BoardLocation(6, 6), true);
		board.updateBoard(new BoardLocation(8, 8), true);
		board.updateBoard(new BoardLocation(9, 9), false);
		assertEquals(
				BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(15), 15, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(14), 16, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(16), 16, Pattern.ON_ULDIAG,
						true, 3, board).size(), 0);
		assertEquals(
				BoardChecker.checkOpenPatCont(
						board.getULDiagByIndex(15), 15, Pattern.ON_ULDIAG,
						true, 4, board).size(), 0);
		board.reset();
		board.updateBoard(new BoardLocation(3,10), true);
		board.updateBoard(new BoardLocation(2,10), true);
		board.updateBoard(new BoardLocation(1,10), true);
		board.updateBoard(new BoardLocation(0,10), true);
		board.updateBoard(new BoardLocation(5,10), false);
		assertEquals(BoardChecker.checkBoardOpenPatCont(board, true, 4).size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(14,0), true);
		board.updateBoard(new BoardLocation(13,1), true);
		board.updateBoard(new BoardLocation(12,2), true);
		board.updateBoard(new BoardLocation(11,3), true);
		board.updateBoard(new BoardLocation(9,5), false);
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
		board.reset();
		board.updateBoard(new BoardLocation(10,5), true);
		board.updateBoard(new BoardLocation(10,6), true);
		board.updateBoard(new BoardLocation(10,7), true);
		board.updateBoard(new BoardLocation(10,9), true);
		board.updateBoard(new BoardLocation(10,4), false);
		board.updateBoard(new BoardLocation(10,10), false);
		// TODO Remove Duplicates in the original method!
		ArrayList<Pattern> results = BoardChecker.checkBoardClosedPatDisc(board, true, 4);
		assertEquals(results.size(), 1);
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
		board.reset();
		board.updateBoard(new BoardLocation(11,2), true);
		board.updateBoard(new BoardLocation(11,3), true);
		board.updateBoard(new BoardLocation(11,4), true);
		board.updateBoard(new BoardLocation(11,5), true);
		board.updateBoard(new BoardLocation(11,6), false);
		board.updateBoard(new BoardLocation(11,1), false);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4).size(), 0);
		board.reset();
		board.updateBoard(new BoardLocation(10,0), true);
		board.updateBoard(new BoardLocation(10,1), true);
		board.updateBoard(new BoardLocation(10,2), true);
		board.updateBoard(new BoardLocation(10,3), true);
		board.updateBoard(new BoardLocation(10,4), false);
		assertEquals(BoardChecker.checkBoardClosedPatCont(board, true, 4).size(), 1);
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

	@Test
	public void testKeepOnlyBubble() {
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		locations.add(new BoardLocation(2,3));
		locations.add(new BoardLocation(3,4));
		locations.add(new BoardLocation(5,6));
		DiscOpenPattern testPattern = new DiscOpenPattern(locations, Pattern.ON_ULDIAG,
				2, board.findBlockingLocs(locations, Pattern.ON_ULDIAG));
		ArrayList<Pattern> inputs = new ArrayList<Pattern>();
		inputs.add(testPattern);
		ArrayList<BoardLocation> retVal = Algorithm.keepOnlyBubble(inputs);
		assertEquals(retVal.size(), 1);
		assertEquals(retVal.get(0), new BoardLocation(4,5));
		ArrayList<BoardLocation> locations2 = new ArrayList<BoardLocation>();
		locations2.add(new BoardLocation(7,8));
		locations2.add(new BoardLocation(8,9));
		locations2.add(new BoardLocation(9,10));
		ContOpenPattern test2 = new ContOpenPattern(locations, Pattern.ON_ULDIAG,
				board.findBlockingLocs(locations, Pattern.ON_ULDIAG));
		inputs.add(test2);
		retVal = Algorithm.keepOnlyBubble(inputs);
		assertEquals(retVal.size(), 3);
		assertTrue(retVal.contains(new BoardLocation(6,7)));
	}

	@Test
	public void testCheckAllSubPatterns() throws InvalidIndexException {
		board.reset();
		board.updateBoard(new BoardLocation(2,2), true);
		board.updateBoard(new BoardLocation(3,3), true);
		board.updateBoard(new BoardLocation(4,2), true);
		ArrayList<Pattern> allSubPatterns = BoardChecker.checkAllSubPatterns(board, true);
		assertTrue(allSubPatterns.size() == 3);
		board.updateBoard(new BoardLocation(5,1), false);
		allSubPatterns = BoardChecker.checkAllSubPatterns(board, true);
		assertTrue(allSubPatterns.size() == 2);
		board.withdrawMove(new BoardLocation(5,1));
		board.updateBoard(new BoardLocation(5,1), true);
		allSubPatterns = BoardChecker.checkAllSubPatterns(board, true);
		assertTrue(allSubPatterns.size() == 2);
		board.withdrawMove(new BoardLocation(5,1));
		board.updateBoard(new BoardLocation(1,5), false);
		board.updateBoard(new BoardLocation(6,0), false);
		allSubPatterns = BoardChecker.checkAllSubPatterns(board, true);
		assertEquals(allSubPatterns.size(), 2);
		board.renderBoard(2);
	}

	@Test
	public void testCheckAllPatternsArd() throws InvalidIndexException {
		board.reset();
		board.updateBoard(new BoardLocation(10,11), false);
		board.updateBoard(new BoardLocation(10,12), false);
		board.updateBoard(new BoardLocation(10,13), false);
		board.updateBoard(new BoardLocation(10,15), false);
		board.updateBoard(new BoardLocation(2,3), false);
		board.updateBoard(new BoardLocation(2,4), false);
		board.updateBoard(new BoardLocation(2,5), false);
		board.updateBoard(new BoardLocation(9,12), false);
		board.updateBoard(new BoardLocation(8,12), false);
		ArrayList<Pattern> result = BoardChecker.checkAllPatternsAroundLoc
				(new BoardLocation(10,12), board, false);
		assertEquals(result.size(), 2);
		ArrayList<Pattern> result2 = BoardChecker.checkAllPatternsAroundLoc
				(new BoardLocation(10,11), board, false);
		assertEquals(result2.size(), 1);
		board.updateBoard(new BoardLocation(10,1), false);
		board.updateBoard(new BoardLocation(10,2), false);
		board.updateBoard(new BoardLocation(10,3), false);
		result2 = BoardChecker.checkAllPatternsAroundLoc
				(new BoardLocation(10,15), board, false);
		assertEquals(result2.size(), 1);
	}

	@Test
	public void testUpdatePatternsForBoard() throws InvalidIndexException {
		board.reset();
		board.updateBoard(new BoardLocation(1,1), false);
		board.updateBoard(new BoardLocation(1,2), false);
		board.updateBoard(new BoardLocation(1,3), false);
		board.updateBoard(new BoardLocation(7,8), true);
		board.updateBoard(new BoardLocation(7,9), true);
		board.updateBoard(new BoardLocation(7,10), true);
		assertEquals(board.getSecondPattern().size(), 1);
		assertEquals(board.getFirstPattern().size(), 1);
		board.updateBoard(new BoardLocation(1,0), true);
		assertEquals(board.getSecondPattern().size(), 0);
		board.updateBoard(new BoardLocation(1,4), false);
		assertEquals(board.getSecondPattern().size(), 1);
		board.withdrawMove(new BoardLocation(1,4));
		assertEquals(board.getSecondPattern().size(), 0);
		board.withdrawMove(new BoardLocation(1,0));
		board.renderBoard(2);
		assertEquals(board.getSecondPattern().size(), 1);
	}

	@Test
	public void testUpdateCriticalLocationsOnChange() throws InvalidIndexException {
	    board.reset();
	    board.updateBoard(new BoardLocation(1,1), false);
	    board.updateBoard(new BoardLocation(2,2), false);
	    board.updateBoard(new BoardLocation(3,3), false);
	    board.updateBoard(new BoardLocation(0,0), true);
	    board.updateBoard(new BoardLocation(4,5), false);
	    board.updateBoard(new BoardLocation(4,6), false);
	    assertEquals(board.getFirstCriticalLocs().size(), 0);
	    assertEquals(board.getSecondCriticalLocs().size(), 1);
	    board.updateBoard(new BoardLocation(4,7), false);
	    ArrayList<BoardLocation> locs = board.getSecondCriticalLocs();
	    assertEquals(board.getSecondCriticalLocs().size(), 1);
	    board.updateBoard(new BoardLocation(4,4), true);
	    assertEquals(board.getSecondCriticalLocs().size(), 0);
	    board.withdrawMove(new BoardLocation(4,4));
	    board.withdrawMove(new BoardLocation(4,7));
	    assertEquals(board.getSecondCriticalLocs().size(), 1);
	}

	@Test
	public void testCheckAllCompositePatterns() throws InvalidIndexException {
		board.reset();
	    board.updateBoard(new BoardLocation(1,1), false);
	    board.updateBoard(new BoardLocation(2,2), false);
	    board.updateBoard(new BoardLocation(3,3), false);
	    board.updateBoard(new BoardLocation(0,0), true);
	    board.updateBoard(new BoardLocation(4,5), false);
	    board.updateBoard(new BoardLocation(4,6), false);
	    assertEquals(BoardChecker.checkAllCompositePatternsArd
	    		(board, false, new BoardLocation(4,5)).size(), 0);
	    board.updateBoard(new BoardLocation(4,4), false);
	    assertEquals(BoardChecker.checkAllCompositePatternsArd
	    		(board, false, new BoardLocation(4,4)).size(), 1);
	}
}
