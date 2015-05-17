package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

import org.junit.Before;
import org.junit.Test;

import utils.DeepCopy;
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
		secondGameBoard.updateBoard(new BoardLocation(1,1), true);
		secondGameBoard.updateBoard(new BoardLocation(2,2), true);
		secondGameBoard.updateBoard(new BoardLocation(4,5), true);
		secondGameBoard.updateBoard(new BoardLocation(0,0), false);
		secondGameBoard.updateBoard(new BoardLocation(5,6), false);
		ArrayList<BoardLocation> retVal2 = ai.blockPotentialCompositePat();
		assertTrue(retVal2.isEmpty());
		// Use real situation encountered when playing
		// AI versus AI.
		firstGameBoard.reset();
		firstGameBoard.updateBoard(new BoardLocation(3,9), false);
		firstGameBoard.updateBoard(new BoardLocation(4,8), false);
		firstGameBoard.updateBoard(new BoardLocation(5,7), true);
		firstGameBoard.updateBoard(new BoardLocation(5,8), true);
		firstGameBoard.updateBoard(new BoardLocation(5,9), false);
		firstGameBoard.updateBoard(new BoardLocation(6,8), true);
		firstGameBoard.updateBoard(new BoardLocation(7,8), true);
		firstGameBoard.updateBoard(new BoardLocation(7,7), true);
		firstGameBoard.updateBoard(new BoardLocation(7,9), false);
		firstGameBoard.updateBoard(new BoardLocation(7,5), false);
		firstGameBoard.updateBoard(new BoardLocation(8,4), false);
		firstGameBoard.updateBoard(new BoardLocation(8,6), true);
		firstGameBoard.updateBoard(new BoardLocation(9,3), true);
		firstGameBoard.updateBoard(new BoardLocation(8,7), false);
		firstGameBoard.updateBoard(new BoardLocation(8,8), false);
		firstGameBoard.updateBoard(new BoardLocation(9,5), true);
		firstGameBoard.updateBoard(new BoardLocation(9,6), false);
		firstGameBoard.updateBoard(new BoardLocation(10,2), true);
		firstGameBoard.updateBoard(new BoardLocation(10,3), false);
		firstGameBoard.updateBoard(new BoardLocation(10,4), false);
		firstGameBoard.updateBoard(new BoardLocation(10,5), false);
		firstGameBoard.updateBoard(new BoardLocation(10,8), true);
		firstGameBoard.updateBoard(new BoardLocation(11,1), true);
		firstGameBoard.updateBoard(new BoardLocation(11,4), true);
		firstGameBoard.renderBoard(2);
		retVal = firstAi.blockPotentialCompositePat();
		assertFalse(retVal.isEmpty());
		assertEquals(retVal.size(), 2);
	}

	@Test
	public void testCheckOtherPossibleCompositeAtk() throws InvalidIndexException {
		firstGameBoard.reset();
		firstGameBoard.updateBoard(new BoardLocation(3,9), false);
		firstGameBoard.updateBoard(new BoardLocation(4,8), false);
		firstGameBoard.updateBoard(new BoardLocation(5,7), true);
		firstGameBoard.updateBoard(new BoardLocation(5,8), true);
		firstGameBoard.updateBoard(new BoardLocation(5,9), false);
		firstGameBoard.updateBoard(new BoardLocation(6,8), true);
		firstGameBoard.updateBoard(new BoardLocation(7,8), true);
		firstGameBoard.updateBoard(new BoardLocation(7,7), true);
		firstGameBoard.updateBoard(new BoardLocation(7,9), false);
		firstGameBoard.updateBoard(new BoardLocation(7,5), false);
		firstGameBoard.updateBoard(new BoardLocation(8,4), false);
		firstGameBoard.updateBoard(new BoardLocation(8,6), true);
		firstGameBoard.updateBoard(new BoardLocation(9,3), true);
		firstGameBoard.updateBoard(new BoardLocation(8,7), false);
		firstGameBoard.updateBoard(new BoardLocation(8,8), false);
		firstGameBoard.updateBoard(new BoardLocation(9,5), true);
		firstGameBoard.updateBoard(new BoardLocation(9,6), false);
		firstGameBoard.updateBoard(new BoardLocation(10,2), true);
		firstGameBoard.updateBoard(new BoardLocation(10,3), false);
		firstGameBoard.updateBoard(new BoardLocation(10,4), false);
		firstGameBoard.updateBoard(new BoardLocation(10,5), false);
		firstGameBoard.updateBoard(new BoardLocation(10,8), true);
		firstGameBoard.updateBoard(new BoardLocation(11,1), true);
		firstGameBoard.updateBoard(new BoardLocation(11,4), true);
		assertTrue(firstAi.checkOtherCompositeAtk(VirtualBoard.getVBoard((Board) DeepCopy.copy(firstGameBoard))));
	}

	@Test
	public void testFindBestLocWhenStuck() throws InvalidIndexException {
		firstGameBoard.reset();
		firstGameBoard.updateBoard(new BoardLocation(4,4), true);
		firstGameBoard.updateBoard(new BoardLocation(3,5), true);
		firstGameBoard.updateBoard(new BoardLocation(2,6), true);
		firstGameBoard.updateBoard(new BoardLocation(1,7), false);
		firstGameBoard.updateBoard(new BoardLocation(5,3), false);
		firstGameBoard.updateBoard(new BoardLocation(3,4), false);
//		firstGameBoard.renderBoard(2);
		BoardLocation result = firstAi.findBestLocWhenStuck();
		assertTrue(result != null);
		assertEquals(result, new BoardLocation(4,6));
	}

	@Test
	public void testMakeMoveEnd() throws InvalidIndexException {
		// Test failures.
		secondGameBoard.reset();
		secondGameBoard.updateBoard(new BoardLocation(4,11), false);
		secondGameBoard.updateBoard(new BoardLocation(6,11), false);
		secondGameBoard.updateBoard(new BoardLocation(7,11), false);
		secondGameBoard.updateBoard(new BoardLocation(9,11), false);
		secondGameBoard.updateBoard(new BoardLocation(7,10), false);
		secondGameBoard.updateBoard(new BoardLocation(8,9), false);
		secondGameBoard.updateBoard(new BoardLocation(9,9), false);
		secondGameBoard.updateBoard(new BoardLocation(8,8), false);
		secondGameBoard.updateBoard(new BoardLocation(9,8), false);
		secondGameBoard.updateBoard(new BoardLocation(9,7), false);
		secondGameBoard.updateBoard(new BoardLocation(9,6), false);
		secondGameBoard.updateBoard(new BoardLocation(8,6), false);
		secondGameBoard.updateBoard(new BoardLocation(7,5), false);
		secondGameBoard.updateBoard(new BoardLocation(6,4), false);
		secondGameBoard.updateBoard(new BoardLocation(5,12), true);
		secondGameBoard.updateBoard(new BoardLocation(5,10), true);
		secondGameBoard.updateBoard(new BoardLocation(5,3), true);
		secondGameBoard.updateBoard(new BoardLocation(6,9), true);
		secondGameBoard.updateBoard(new BoardLocation(6,8), true);
		secondGameBoard.updateBoard(new BoardLocation(6,5), true);
		secondGameBoard.updateBoard(new BoardLocation(7,9), true);
		secondGameBoard.updateBoard(new BoardLocation(7,8), true);
		secondGameBoard.updateBoard(new BoardLocation(7,7), true);
		secondGameBoard.updateBoard(new BoardLocation(7,6), true);
		secondGameBoard.updateBoard(new BoardLocation(8,7), true);
		secondGameBoard.updateBoard(new BoardLocation(10,7), true);
		secondGameBoard.updateBoard(new BoardLocation(9,10), true);
		secondGameBoard.updateBoard(new BoardLocation(10,8), true);
		secondGameBoard.updateBoard(new BoardLocation(9,5), true);
//		secondGameBoard.renderBoard(2);
		BoardLocation loc = ai.makeMoveEnd();
		assertTrue(loc != null);
		assertTrue(loc.equals(new BoardLocation(5,11))
				|| loc.equals(new BoardLocation(8,11)));
		secondGameBoard.reset();
		secondGameBoard.updateBoard(new BoardLocation(5,7), false);
		secondGameBoard.updateBoard(new BoardLocation(7,7), false);
		secondGameBoard.updateBoard(new BoardLocation(7,8), false);
		secondGameBoard.updateBoard(new BoardLocation(7,9), false);
		secondGameBoard.updateBoard(new BoardLocation(7,10), false);
		secondGameBoard.updateBoard(new BoardLocation(8,6), false);
		secondGameBoard.updateBoard(new BoardLocation(9,5), false);
		secondGameBoard.updateBoard(new BoardLocation(8,9), false);
		secondGameBoard.updateBoard(new BoardLocation(7,6), true);
		secondGameBoard.updateBoard(new BoardLocation(7,11), true);
		secondGameBoard.updateBoard(new BoardLocation(8,8), true);
		secondGameBoard.updateBoard(new BoardLocation(9,9), true);
		secondGameBoard.updateBoard(new BoardLocation(9,7), true);
		secondGameBoard.updateBoard(new BoardLocation(9,6), true);
		secondGameBoard.updateBoard(new BoardLocation(10,6), true);
		secondGameBoard.updateBoard(new BoardLocation(10,4), true);
		secondGameBoard.updateBoard(new BoardLocation(10,8), true);
//		secondGameBoard.renderBoard(2);
		BoardLocation retVal2 = ai.makeMoveEnd();
		assertEquals(retVal2, new BoardLocation(6,8));
		secondGameBoard.reset();
		secondGameBoard.updateBoard(new BoardLocation(2,8), true);
		secondGameBoard.updateBoard(new BoardLocation(3,12), true);
		secondGameBoard.updateBoard(new BoardLocation(3,7), false);
		secondGameBoard.updateBoard(new BoardLocation(4,7), false);
		secondGameBoard.updateBoard(new BoardLocation(4,6), false);
		secondGameBoard.updateBoard(new BoardLocation(4,8), true);
		secondGameBoard.updateBoard(new BoardLocation(4,11), false);
		secondGameBoard.updateBoard(new BoardLocation(5,5), false);
		secondGameBoard.updateBoard(new BoardLocation(5,6), true);
		secondGameBoard.updateBoard(new BoardLocation(5,7), true);
		secondGameBoard.updateBoard(new BoardLocation(5,8), true);
		secondGameBoard.updateBoard(new BoardLocation(5,9), true);
		secondGameBoard.updateBoard(new BoardLocation(5,10), false);
		secondGameBoard.updateBoard(new BoardLocation(6,5), true);
		secondGameBoard.updateBoard(new BoardLocation(6,6), false);
		secondGameBoard.updateBoard(new BoardLocation(6,7), false);
		secondGameBoard.updateBoard(new BoardLocation(6,8), false);
		secondGameBoard.updateBoard(new BoardLocation(6,9), false);
		secondGameBoard.updateBoard(new BoardLocation(6,10), true);
		secondGameBoard.updateBoard(new BoardLocation(7,3), true);
		secondGameBoard.updateBoard(new BoardLocation(7,4), true);
		secondGameBoard.updateBoard(new BoardLocation(7,7), true);
		secondGameBoard.updateBoard(new BoardLocation(8,3), true);
		secondGameBoard.updateBoard(new BoardLocation(7,6), false);
		secondGameBoard.updateBoard(new BoardLocation(7,8), false);
		secondGameBoard.updateBoard(new BoardLocation(7,9), false);
		secondGameBoard.updateBoard(new BoardLocation(8,5), false);
		secondGameBoard.updateBoard(new BoardLocation(8,6), true);
		secondGameBoard.updateBoard(new BoardLocation(8,7), true);
		secondGameBoard.updateBoard(new BoardLocation(8,8), true);
		secondGameBoard.updateBoard(new BoardLocation(8,9), true);
		secondGameBoard.updateBoard(new BoardLocation(8,11), true);
		secondGameBoard.updateBoard(new BoardLocation(8,10), false);
		secondGameBoard.updateBoard(new BoardLocation(9,0), true);
		secondGameBoard.updateBoard(new BoardLocation(9,5), true);
		secondGameBoard.updateBoard(new BoardLocation(9,9), true);
		secondGameBoard.updateBoard(new BoardLocation(10,3), true);
		secondGameBoard.updateBoard(new BoardLocation(10,10), true);
		secondGameBoard.updateBoard(new BoardLocation(10,12), true);
		secondGameBoard.updateBoard(new BoardLocation(13,11), true);
		secondGameBoard.updateBoard(new BoardLocation(9,2), false);
		secondGameBoard.updateBoard(new BoardLocation(9,3), false);
		secondGameBoard.updateBoard(new BoardLocation(9,4), false);
		secondGameBoard.updateBoard(new BoardLocation(9,1), false);
		secondGameBoard.updateBoard(new BoardLocation(9,6), false);
		secondGameBoard.updateBoard(new BoardLocation(9,11), false);
		secondGameBoard.updateBoard(new BoardLocation(10,11), false);
		secondGameBoard.updateBoard(new BoardLocation(11,11), false);
		secondGameBoard.updateBoard(new BoardLocation(12,11), false);
		secondGameBoard.renderBoard(2);
		BoardLocation controversial = ai.makeMoveEnd();
		secondGameBoard.reset();
		secondGameBoard.updateBoard(new BoardLocation(7,7), true);
		secondGameBoard.updateBoard(new BoardLocation(8,7), false);
		secondGameBoard.updateBoard(new BoardLocation(6,6), true);
		secondGameBoard.updateBoard(new BoardLocation(7,8), false);
		secondGameBoard.updateBoard(new BoardLocation(8,8), true);
		secondGameBoard.updateBoard(new BoardLocation(5,5), false);
		secondGameBoard.updateBoard(new BoardLocation(9,9), true);
		secondGameBoard.updateBoard(new BoardLocation(10,10), false);
		secondGameBoard.updateBoard(new BoardLocation(8,6), true);
		secondGameBoard.updateBoard(new BoardLocation(9,6), false);
		secondGameBoard.updateBoard(new BoardLocation(6,9), true);
		secondGameBoard.updateBoard(new BoardLocation(5,9), false);
		secondGameBoard.updateBoard(new BoardLocation(6,8), true);
		secondGameBoard.updateBoard(new BoardLocation(6,7), false);
		secondGameBoard.updateBoard(new BoardLocation(9,5), true);
		secondGameBoard.updateBoard(new BoardLocation(10,4), false);
		secondGameBoard.updateBoard(new BoardLocation(7,6), true);
		secondGameBoard.updateBoard(new BoardLocation(5,6), false);
		secondGameBoard.updateBoard(new BoardLocation(4,5), true);
		secondGameBoard.updateBoard(new BoardLocation(10,11), false);
		secondGameBoard.updateBoard(new BoardLocation(7,9), true);
		secondGameBoard.updateBoard(new BoardLocation(3,7), false);
		secondGameBoard.updateBoard(new BoardLocation(4,6), true);
		secondGameBoard.updateBoard(new BoardLocation(5,7), false);
		secondGameBoard.updateBoard(new BoardLocation(5,8), true);
		secondGameBoard.updateBoard(new BoardLocation(4,8), false);
		secondGameBoard.updateBoard(new BoardLocation(6,10), true);
		secondGameBoard.updateBoard(new BoardLocation(5,11), false);
		secondGameBoard.updateBoard(new BoardLocation(8,9), true);
		secondGameBoard.updateBoard(new BoardLocation(10,9), false);
		secondGameBoard.updateBoard(new BoardLocation(10,8), true);
		secondGameBoard.updateBoard(new BoardLocation(7,5), false);
		secondGameBoard.updateBoard(new BoardLocation(4,4), true);
		secondGameBoard.updateBoard(new BoardLocation(4,7), false);
		secondGameBoard.updateBoard(new BoardLocation(2,7), true);
		secondGameBoard.updateBoard(new BoardLocation(6,5), false);
		secondGameBoard.updateBoard(new BoardLocation(3,8), true);
		secondGameBoard.updateBoard(new BoardLocation(5,4), false);
		secondGameBoard.updateBoard(new BoardLocation(5,3), true);
		secondGameBoard.updateBoard(new BoardLocation(10,5), false);
		secondGameBoard.updateBoard(new BoardLocation(11,4), true);
		secondGameBoard.updateBoard(new BoardLocation(10,6), false);
		secondGameBoard.updateBoard(new BoardLocation(10,3), true);
		secondGameBoard.updateBoard(new BoardLocation(10,12), false);
		secondGameBoard.updateBoard(new BoardLocation(10,13), true);
		secondGameBoard.updateBoard(new BoardLocation(2,6), false);
		secondGameBoard.updateBoard(new BoardLocation(1,5), true);
		secondGameBoard.updateBoard(new BoardLocation(7,4), false);
		secondGameBoard.updateBoard(new BoardLocation(8,3), true);
		secondGameBoard.updateBoard(new BoardLocation(8,5), false);
		secondGameBoard.updateBoard(new BoardLocation(6,3), true);
		secondGameBoard.updateBoard(new BoardLocation(10,7), false);
		secondGameBoard.updateBoard(new BoardLocation(11,8), true);
		secondGameBoard.renderBoard(2);
		BoardLocation questionning = ai.makeMoveEnd();
		System.out.println(questionning.getXPos());
		System.out.print(questionning.getYPos() + "\n");
	}

}
