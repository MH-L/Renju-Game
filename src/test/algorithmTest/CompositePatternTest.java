package test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.BoardLocation;

import org.junit.Test;

import algorithm.CompositePattern;
import algorithm.ContOpenPattern;
import algorithm.Pattern;

public class CompositePatternTest {
	@Test
	public void testMakeCompositePatterns() {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		BoardLocation _1 = new BoardLocation(7,8);
		BoardLocation _2 = new BoardLocation(8,8);
		BoardLocation _3 = new BoardLocation(9,8);
		BoardLocation _4 = new BoardLocation(8,7);
		BoardLocation _5 = new BoardLocation(8,6);
		ArrayList<BoardLocation> locs1 = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> locs2 = new ArrayList<BoardLocation>();
		locs1.add(_1);
		locs1.add(_2);
		locs1.add(_3);
		locs2.add(_2);
		locs2.add(_4);
		locs2.add(_5);
		patterns.add(new ContOpenPattern(locs1, 2, null));
		patterns.add(new ContOpenPattern(locs2, 1, null));
		ArrayList<CompositePattern> result = CompositePattern.makeCompositePats(patterns);
		assertFalse(result.isEmpty());
	}

}
