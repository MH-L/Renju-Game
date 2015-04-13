package Algorithm;

import java.util.ArrayList;

import Model.BoardLocation;

public class DiscClosedPattern extends ClosedPattern {
	private int bubbleIndex;

	public DiscClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones) {
		super(locations, type, blockedStones);
		this.bubbleIndex = Pattern.findBubbleIndex(locations, type);
	}

}
