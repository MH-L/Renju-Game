package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public abstract class ClosedPattern extends Pattern {
	private ArrayList<BoardLocation> blockedStones;

	public ClosedPattern(ArrayList<BoardLocation> locations, int type,
			ArrayList<BoardLocation> blockedStones) {
		super(locations, type);
		this.blockedStones = blockedStones;
	}

}
