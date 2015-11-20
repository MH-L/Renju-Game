package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.BoardLocation;

/**
 * A closed contiguous pattern.
 *
 * @author Minghao Liu
 *
 */
public class ContClosedPattern extends ClosedPattern {

	/**
	 * Generated serialization ID.
	 */
	private static final long serialVersionUID = 7535573842113321369L;

	public ContClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones, ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockedStones, blockingLocs);
	}
}
