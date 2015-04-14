package algorithm;

import java.util.ArrayList;

import model.BoardLocation;

public class DiscOpenPattern extends Pattern {
	private int bubbleIndex;

	public DiscOpenPattern(ArrayList<BoardLocation> locations, int type) {
		super(locations, type);
		this.bubbleIndex = Pattern.findBubbleIndex(locations, type);
	}


}
