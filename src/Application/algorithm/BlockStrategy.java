package algorithm;

import java.util.ArrayList;

import model.BoardLocation;

public abstract class BlockStrategy {
	// Java Design Pattern Strategy
	private ArrayList<BoardLocation> applicableLocs;
	
	public BlockStrategy() {
		this.applicableLocs = new ArrayList<BoardLocation>();
	}
	
	public BlockStrategy(ArrayList<BoardLocation> applicableLocs) {
		this.applicableLocs = applicableLocs;
	}
}
