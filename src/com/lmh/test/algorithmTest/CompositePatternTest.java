package com.lmh.test.algorithmTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import renju.com.lmh.algorithm.CompositePattern;
import renju.com.lmh.algorithm.ContOpenPattern;
import renju.com.lmh.algorithm.Pattern;
import renju.com.lmh.model.BoardLocation;

public class CompositePatternTest {
	@Test
	public void testMakeCompositePatterns() {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		BoardLocation _1 = new BoardLocation(7,8);
		BoardLocation _2 = new BoardLocation(8,8);
		BoardLocation _3 = new BoardLocation(9,8);
		BoardLocation _4 = new BoardLocation(8,7);
		BoardLocation _5 = new BoardLocation(8,6);
		BoardLocation _6 = new BoardLocation(10,11);
		BoardLocation _7 = new BoardLocation(11,12);
		BoardLocation _8 = new BoardLocation(12,13);
		ArrayList<BoardLocation> locs1 = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> locs2 = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> locs3 = new ArrayList<BoardLocation>();
		locs1.add(_1);
		locs1.add(_2);
		locs1.add(_3);
		locs2.add(_2);
		locs2.add(_4);
		locs2.add(_5);
		locs3.add(_6);
		locs3.add(_7);
		locs3.add(_8);
		patterns.add(new ContOpenPattern(locs1, 1, null));
		patterns.add(new ContOpenPattern(locs2, 2, null));
		ArrayList<CompositePattern> result = CompositePattern.makeCompositePats(patterns);
		assertFalse(result.isEmpty());
		patterns.clear();
		patterns.add(new ContOpenPattern(locs1, 2, null));
	}

}
