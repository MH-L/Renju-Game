package algorithmTest;

import static org.junit.Assert.*;
import model.Board;

import org.junit.Before;
import org.junit.Test;

import algorithm.Algorithm;
import algorithm.UltimateAlgorithm;

public class AlgorithmTest {
	Algorithm alg;

	@Before
	public void initialize() {
		alg = new UltimateAlgorithm(new Board());
	}

	@Test
	public void testRandomSeed() {
		for (int i = 0; i < 1000; i++) {
			int randNum = Algorithm.getRandNum(2);
			assertTrue(randNum == 1 || randNum == 2);
		}
	}
}
