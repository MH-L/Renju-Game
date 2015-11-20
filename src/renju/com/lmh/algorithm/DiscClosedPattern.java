package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.BoardLocation;

public class DiscClosedPattern extends ClosedPattern {
	/**
	 * Generated serialization ID.
	 */
	private static final long serialVersionUID = 1449116682870625854L;

	private int bubbleIndex;

	public DiscClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones, int bubbleIndex,
			ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockedStones, blockingLocs);
		this.bubbleIndex = bubbleIndex;
	}

	public int getBubbleIndex() {
		return bubbleIndex;
	}

}
