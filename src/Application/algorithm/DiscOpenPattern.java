package algorithm;

import java.util.ArrayList;

import model.BoardLocation;

public class DiscOpenPattern extends Pattern {
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
