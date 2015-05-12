package test.algorithmTest;

import java.util.ArrayList;

import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

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
	}

	@Test
	public void testFindFirstStone() {
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();

	}
}
