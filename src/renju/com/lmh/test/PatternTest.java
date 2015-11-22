package renju.com.lmh.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import renju.com.lmh.algorithm.ContOpenPattern;
import renju.com.lmh.algorithm.Pattern;
import renju.com.lmh.model.BoardLocation;

public class PatternTest {
	ArrayList<BoardLocation> inOrder = new ArrayList<BoardLocation>();
	ArrayList<BoardLocation> outOfOrder = new ArrayList<BoardLocation>();
	ArrayList<BoardLocation> totallyShuffled = new ArrayList<BoardLocation>();
	ArrayList<BoardLocation> reversed = new ArrayList<BoardLocation>();

	@Before
	public void init() {
		BoardLocation _0 = new BoardLocation(1,1);
		BoardLocation _1 = new BoardLocation(2,2);
		BoardLocation _2 = new BoardLocation(3,3);
		BoardLocation _3 = new BoardLocation(4,4);
		BoardLocation _4 = new BoardLocation(5,5);
		inOrder.add(_0);
		inOrder.add(_1);
		inOrder.add(_2);
		inOrder.add(_3);
		inOrder.add(_4);
		reversed.add(_0);
		reversed.add(_1);
		reversed.add(_2);
		reversed.add(_3);
		reversed.add(_4);
		totallyShuffled.add(_3);
		totallyShuffled.add(_1);
		totallyShuffled.add(_2);
		totallyShuffled.add(_4);
		totallyShuffled.add(_0);
		outOfOrder.add(_2);
		outOfOrder.add(_1);
		outOfOrder.add(_3);
		outOfOrder.add(_0);
		outOfOrder.add(_4);
	}

	@Test
	public void testFindFirstStone() {
		BoardLocation _0 = new BoardLocation(0,0);
		BoardLocation _1 = new BoardLocation(5,5);
		ArrayList<BoardLocation> blockLocations = new ArrayList<BoardLocation>();
		blockLocations.add(_0);
		blockLocations.add(_1);
		Pattern firstPat = new ContOpenPattern(inOrder, 3, blockLocations);
		Pattern secondPat = new ContOpenPattern(outOfOrder, 3, blockLocations);
		Pattern thirdPat = new ContOpenPattern(totallyShuffled, 3, blockLocations);
		Pattern fourthPat = new ContOpenPattern(reversed, 3, blockLocations);
		BoardLocation firstStone1 = firstPat.findFirstStone();
		BoardLocation firstStone2 = secondPat.findFirstStone();
		BoardLocation firstStone3 = thirdPat.findFirstStone();
		BoardLocation firstStone4 = fourthPat.findFirstStone();
		assertEquals(firstStone1, firstStone2);
		assertEquals(firstStone3, firstStone2);
		assertEquals(firstStone3, firstStone4);
		assertEquals(firstStone1.getXPos(), 1);
		assertEquals(firstStone1.getYPos(), 1);
	}
}
