package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

/**
 * A closed contiguous pattern.
 *
 * @author Minghao Liu
 *
 */
public class ContClosedPattern extends ClosedPattern {

	public ContClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones, ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockedStones, blockingLocs);
	}
}
