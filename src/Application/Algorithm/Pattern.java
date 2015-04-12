package Algorithm;

import java.util.ArrayList;

import Model.Board;
import Model.BoardLocation;

/**
 * Defines a single pattern on the board.
 * Example: an open three or a closed four
 * @author Minghao Liu
 *
 */
public class Pattern {
	private ArrayList<BoardLocation> constituent;
	private boolean isContiguous;
	private ArrayList<BoardLocation> blockingLocs;
	private int type;
	public static final int ON_ROW = 1;
	public static final int ON_COL = 2;
	public static final int ON_ULDIAG = 3;
	public static final int ON_URDIAG = 4;

	public Pattern(ArrayList<BoardLocation> locations, boolean isContiguous, int type) {
		this.constituent = locations;
		this.isContiguous = isContiguous;
		this.blockingLocs = Board.findBlockingLocs(locations, isContiguous, type);
	}

	public int getNumLocs() {
		return this.constituent.size();
	}

	public ArrayList<BoardLocation> getLocations() {
		return this.constituent;
	}

	public boolean isIntersecting(Pattern pat) {
		if (pat.getNumLocs() == 0)
			return false;
		for (BoardLocation loc1 : pat.getLocations())
			for (BoardLocation loc2 : this.constituent)
				if (loc1.compare(loc2))
					return true;
		return false;
	}

	public boolean isDifferent(Pattern pat) {
		for (BoardLocation loc1 : pat.getLocations())
			for (BoardLocation loc2 : this.constituent)
				if (!loc1.compare(loc2))
					return true;
		return false;
	}
}
