package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import algorithm.Algorithm;
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
		assertEquals(locs.size(), 1);
		bd.updateBoard(new BoardLocation(2, 2), true);
		bd.updateBoard(new BoardLocation(2, 1), true);
		bd.renderBoard(2);
		locs = alg.calculateAttack();
		assertEquals(locs.size(), 1);
	}
}
