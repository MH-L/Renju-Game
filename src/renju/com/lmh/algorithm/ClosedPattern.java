package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.BoardLocation;

public abstract class ClosedPattern extends Pattern {
	/**
	 * Generated serialization ID.
	 */
	private static final long serialVersionUID = -7611508486614137583L;

	public ClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones, ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockingLocs);
	}

}
