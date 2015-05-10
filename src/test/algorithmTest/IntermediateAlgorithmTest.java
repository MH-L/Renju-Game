package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import algorithm.BoardChecker;
import algorithm.IntermediateAlgorithm;

public class IntermediateAlgorithmTest {
	private IntermediateAlgorithm ai;
	private IntermediateAlgorithm firstAi;
	private Board firstGameBoard;
	private Board secondGameBoard;

	@Before
	public void init() {
		firstGameBoard = new Board();
		secondGameBoard = new Board();
		ai = new IntermediateAlgorithm(secondGameBoard, false);
		firstAi = new IntermediateAlgorithm(firstGameBoard, true);
	}

	@Test
	public void testIntermediateAttack() throws InvalidIndexException {
		firstGameBoard.updateBoard(new BoardLocation(5,8), true);
		firstGameBoard.updateBoard(new BoardLocation(6,7), true);
		firstGameBoard.updateBoard(new BoardLocation(7,6), true);
		firstGameBoard.updateBoard(new BoardLocation(8,5), false);
		firstGameBoard.updateBoard(new BoardLocation(5,9), true);
		firstGameBoard.updateBoard(new BoardLocation(6,9), true);
		assertEquals(firstAi.getSelfStone().size(), 5);
		assertEquals(firstAi.getOtherStone().size(), 1);
		ArrayList<BoardLocation> retVal = firstAi.intermediateAttack();
		assertFalse(retVal.isEmpty());
		firstGameBoard.reset();
	}

	@Test
	public void testBlockCompositePat() {

	}

}
