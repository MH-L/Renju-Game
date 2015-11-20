package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.BoardLocation;

public class ContOpenPattern extends Pattern {

	/**
	 * Generated serialization ID.
	 */
	private static final long serialVersionUID = -1581740245406389772L;

	public ContOpenPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockingLocs);
	}

}
