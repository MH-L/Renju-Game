package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import algorithm.Algorithm;
import algorithm.ContOpenPattern;
import algorithm.Pattern;
import algorithm.UltimateAlgorithm;

public class AlgorithmTest {
	Algorithm alg;

	@Before
	public void initialize() {
		alg = new UltimateAlgorithm(new Board(), true);
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
		bd.renderBoard(2);
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
		test = new ContOpenPattern(locations, Pattern.ON_ULDIAG,
				board.findBlockingLocs(locations, Pattern.ON_ULDIAG));
		testVal = alg.extendToWinning(test);
		assertTrue(testVal != null);
		assertTrue(testVal.equals(new BoardLocation(4,4)));
	}
}
