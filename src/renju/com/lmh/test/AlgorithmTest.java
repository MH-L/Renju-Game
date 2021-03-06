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
import renju.com.lmh.algorithm.UltimateAlgorithm;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

public class AlgorithmTest {
	Algorithm alg;

	@Before
	public void initialize() {
		alg = new UltimateAlgorithm(new Board(16), true);
	}

	@Test
	public void testRandomSeed() {
		for (int i = 0; i < 1000; i++) {
			int randNum = Algorithm.getRandNum(2);
			assertTrue(randNum == 1 || randNum == 2);
		}
	}

	@Test
	public void testAttack() throws InvalidIndexException {
		Board bd = alg.getBoard();
		bd.updateBoard(new BoardLocation(2, 3), true);
		bd.updateBoard(new BoardLocation(3, 4), false);
		ArrayList<BoardLocation> locs = alg.calculateAttack();
		assertEquals(alg.getBoard().getPlayer1Stone().size(), 1);
		assertEquals(alg.getBoard().getPlayer2Stone().size(), 1);
		assertEquals(locs.size(), 0);
		bd.updateBoard(new BoardLocation(2, 2), true);
		bd.updateBoard(new BoardLocation(2, 1), true);
		locs = alg.calculateAttack();
		assertEquals(locs.size(), 1);
	}

	@Test
	public void testExtendToWinning() throws InvalidIndexException {
		Board board = alg.getBoard();
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		locations.add(new BoardLocation(5, 5));
		locations.add(new BoardLocation(5, 6));
		locations.add(new BoardLocation(5, 7));
		Pattern test = new ContOpenPattern(locations, Pattern.ON_ROW,
				board.findBlockingLocs(locations, Pattern.ON_ROW));
		board.updateBoard(new BoardLocation(5, 5), true);
		board.updateBoard(new BoardLocation(5, 6), true);
		board.updateBoard(new BoardLocation(5, 7), true);
		BoardLocation testVal = alg.extendToWinning(test);
		assertTrue(testVal != null);
		assertTrue(testVal.equals(new BoardLocation(5, 4))
				|| testVal.equals(new BoardLocation(5, 8)));
		board.reset();
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(3, 3), true);
		board.updateBoard(new BoardLocation(5, 5), true);
		locations.clear();
		locations.add(new BoardLocation(2, 2));
		locations.add(new BoardLocation(3, 3));
		locations.add(new BoardLocation(5, 5));
		test = new DiscOpenPattern(locations, Pattern.ON_ULDIAG,
				2, board.findBlockingLocs(locations, Pattern.ON_ULDIAG));
		testVal = alg.extendToWinning(test);
		assertTrue(testVal != null);
		assertTrue(testVal.equals(new BoardLocation(4, 4)));
	}

	@Test
	public void testExtractingAdjacentLocs() throws InvalidIndexException {
		Board bd = alg.getBoard();
		bd.reset();
		bd.updateBoard(new BoardLocation(0,0), true);
		assertEquals(alg.extractAllAdjacentLocs().size(), 6);
		bd.updateBoard(new BoardLocation(1,1), true);
		assertEquals(alg.extractAllAdjacentLocs().size(), 10);
		bd.updateBoard(new BoardLocation(2,2), false);
		assertEquals(alg.extractAllAdjacentLocs().size(), 9);
		bd.updateBoard(new BoardLocation(7,7), true);
		assertEquals(alg.extractAllAdjacentLocs().size(), 25);
	}

	@Test
	public void testFilterDeadPats() throws InvalidIndexException {
		Board bd = alg.getBoard();
		bd.reset();
		bd.updateBoard(new BoardLocation(15,12), true);
		bd.updateBoard(new BoardLocation(14,12), true);
		bd.updateBoard(new BoardLocation(13,12), true);
		bd.updateBoard(new BoardLocation(12,12), true);
		bd.updateBoard(new BoardLocation(11,12), false);
		ArrayList<Pattern> toFilter = BoardChecker.
				checkAllPatternsAroundLoc(new BoardLocation(13,12), bd, true);
		assertEquals(toFilter.size(), 0);
	}

	@Test
	public void testFilterBlockingLocsAtk() throws InvalidIndexException {
		Board bd = alg.getBoard();
		bd.reset();
		bd.updateBoard(new BoardLocation(12,12), true);
		bd.updateBoard(new BoardLocation(11,12), true);
		bd.updateBoard(new BoardLocation(10,12), true);
		bd.updateBoard(new BoardLocation(13,12), false);
		bd.updateBoard(new BoardLocation(9,11), false);
		bd.updateBoard(new BoardLocation(9,10), false);
		bd.updateBoard(new BoardLocation(9,9), false);
		ArrayList<BoardLocation> blockingLocs = new ArrayList<BoardLocation>();
		blockingLocs.add(new BoardLocation(9,8));
		blockingLocs.add(new BoardLocation(9,12));
		ArrayList<BoardLocation> result = alg.filterBlockingLocsAtk(blockingLocs);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0), new BoardLocation(9,12));
		bd.reset();
		bd.updateBoard(new BoardLocation(12,9), true);
		bd.updateBoard(new BoardLocation(12,8), true);
		blockingLocs.add(new BoardLocation(12,10));
		blockingLocs.add(new BoardLocation(9,10));
		blockingLocs.add(new BoardLocation(14,10));
		result = alg.filterBlockingLocsAtk(blockingLocs);
		assertEquals(result.size(), 1);
		assertTrue(result.contains(new BoardLocation(12,10)));
	}

	@Test
	public void testMakeMoveEnd() {
		Board board = alg.getBoard();
		board.reset();
	}

	@Test
	public void testFindFlexibleLocs() throws InvalidIndexException {
		BoardLocation _1 = new BoardLocation(0,0);
		BoardLocation _2 = new BoardLocation(9,9);
		BoardLocation _3 = new BoardLocation(2,2);
		ArrayList<BoardLocation> inputVal = new ArrayList<BoardLocation>();
		inputVal.add(_1);
		ArrayList<BoardLocation> retVal = Algorithm.findFlexibleLocs(inputVal, new Board(16));
		assertEquals(retVal.size(), 6);
		inputVal.add(_2);
		retVal = Algorithm.findFlexibleLocs(inputVal, new Board(16));
		assertEquals(retVal.size(), 22);
		inputVal.add(_3);
		Board board = new Board(16);
		board.updateBoard(new BoardLocation(0, 0), false);
		board.updateBoard(new BoardLocation(9, 9), false);
		board.updateBoard(new BoardLocation(2, 2), false);
		retVal = Algorithm.findFlexibleLocs(inputVal, board);
		assertEquals(retVal.size(), 33);
	}

	@Test
	public void testAttackContinuously() throws InvalidIndexException {
		Board board = alg.getBoard();
		board.reset();
		board.updateBoard(new BoardLocation(8,8), true);
		board.updateBoard(new BoardLocation(8,7), false);
		board.updateBoard(new BoardLocation(9,9), true);
		board.updateBoard(new BoardLocation(7,8), false);
		board.updateBoard(new BoardLocation(8,10), true);
		board.updateBoard(new BoardLocation(10,9), false);
		board.updateBoard(new BoardLocation(10,8), true);
		board.updateBoard(new BoardLocation(11,7), false);
		board.updateBoard(new BoardLocation(7,9), true);
		board.updateBoard(new BoardLocation(7,10), false);
		board.updateBoard(new BoardLocation(6,8), true);
		board.updateBoard(new BoardLocation(5,7), false);
		board.updateBoard(new BoardLocation(8,9), true);
		board.updateBoard(new BoardLocation(6,9), false);
		board.renderBoard(2);
		BoardLocation strategyLoc = alg.attackContinuously
				(Algorithm.findFlexibleLocs(board.getPlayer1Stone(), board), 5);
		assertTrue(strategyLoc != null);
		board.updateBoard(new BoardLocation(7,11), true);
		board.updateBoard(new BoardLocation(6,12), false);
		strategyLoc = alg.attackContinuously
				(Algorithm.findFlexibleLocs(board.getPlayer1Stone(), board), 4);
		assertTrue(strategyLoc != null);
		board.updateBoard(new BoardLocation(9,11), true);
		board.updateBoard(new BoardLocation(10,12), false);
		assertEquals(board.getFirstCriticalLocs().size(), 1);
		board.reset();
		board.updateBoard(new BoardLocation(7,8), true);
		board.updateBoard(new BoardLocation(6,9), false);
		board.updateBoard(new BoardLocation(8,8), true);
		board.updateBoard(new BoardLocation(6,8), false);
		board.updateBoard(new BoardLocation(8,9), true);
		board.updateBoard(new BoardLocation(9,8), false);
		board.updateBoard(new BoardLocation(8,7), true);
		board.updateBoard(new BoardLocation(8,6), false);
		board.updateBoard(new BoardLocation(6,7), true);
		board.updateBoard(new BoardLocation(5,6), false);
		board.updateBoard(new BoardLocation(7,9), true);
		board.updateBoard(new BoardLocation(7,7), false);
		board.updateBoard(new BoardLocation(5,9), true);
		board.updateBoard(new BoardLocation(9,5), false);
		board.updateBoard(new BoardLocation(10,4), true);
		board.updateBoard(new BoardLocation(7,5), false);
		board.updateBoard(new BoardLocation(9,7), true);
		board.updateBoard(new BoardLocation(6,9), false);
		board.renderBoard(2);
		strategyLoc = alg.makeMoveEnd();
	}
}
