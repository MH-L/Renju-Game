package com.lmh.algorithm;

import java.util.ArrayList;

import com.lmh.model.BoardLocation;

public class DiscOpenPattern extends Pattern {
	/**
	 * Generated serialization ID.
	 */
	private static final long serialVersionUID = -7566839196905222038L;

	private int bubbleIndex;

	public DiscOpenPattern(ArrayList<BoardLocation> locations, int type,
			int bubbleIndex, ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockingLocs);
		this.bubbleIndex = bubbleIndex;
	}

	public int getBubbleIndex() {
		return this.bubbleIndex;
	}

}
