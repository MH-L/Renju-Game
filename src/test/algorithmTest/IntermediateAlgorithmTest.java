package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
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
	public void testConstructor() {
		assertEquals(firstGameBoard, firstAi.getBoard());
		assertEquals(secondGameBoard, ai.getBoard());
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
		firstGameBoard.updateBoard(new BoardLocation(3,3), true);
		firstGameBoard.updateBoard(new BoardLocation(4,4), true);
		firstGameBoard.updateBoard(new BoardLocation(6,4), true);
		firstGameBoard.renderBoard(2);
		assertEquals(firstAi.getSelfStone().size(), 3);
		retVal = firstAi.intermediateAttack();
		assertFalse(retVal.isEmpty());
		assertEquals(retVal.size(), 8);
		assertTrue(retVal.contains(new BoardLocation(7,3)));
		assertTrue(retVal.contains(new BoardLocation(3,2)));
		assertTrue(retVal.contains(new BoardLocation(3,1)));
		assertTrue(retVal.contains(new BoardLocation(3,5)));
		assertTrue(retVal.contains(new BoardLocation(3,6)));
	}

	@Test
	public void testBlockCompositePat() throws InvalidIndexException {
		secondGameBoard.updateBoard(new BoardLocation(11,11), true);
		secondGameBoard.updateBoard(new BoardLocation(10,10), true);
		secondGameBoard.updateBoard(new BoardLocation(9,9), true);
		secondGameBoard.updateBoard(new BoardLocation(8,8), false);
		secondGameBoard.updateBoard(new BoardLocation(12,11), true);
		secondGameBoard.updateBoard(new BoardLocation(12,10), true);
		assertEquals(ai.getBoard().getTotalStones(), 6);
		assertEquals(ai.getSelfStone().size(), 1);
		assertEquals(ai.getOtherStone().size(), 5);
		ArrayList<BoardLocation> retVal = ai.blockPotentialCompositePat();
		assertFalse(retVal.isEmpty());
		BoardLocation toTest = retVal.get(0);
		assertEquals(toTest.getXPos(), 12);
		assertEquals(toTest.getYPos(), 12);
		secondGameBoard.reset();
	}

}
