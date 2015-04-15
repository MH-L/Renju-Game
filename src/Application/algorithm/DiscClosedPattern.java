package algorithm;

import java.util.ArrayList;

import model.BoardLocation;

public class DiscClosedPattern extends ClosedPattern {
	private int bubbleIndex;

	public DiscClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones, int bubbleIndex,
			ArrayList<BoardLocation> blockingLocs) {
		super(locations, type, blockedStones, blockingLocs);
		this.bubbleIndex = bubbleIndex;
	}

}
