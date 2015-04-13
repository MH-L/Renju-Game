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
public abstract class Pattern {
	private ArrayList<BoardLocation> constituent;
	private ArrayList<BoardLocation> blockingLocs;
	private int type;

	public static final int ON_ROW = 1;
	public static final int ON_COL = 2;
	public static final int ON_ULDIAG = 3;
	public static final int ON_URDIAG = 4;

	public Pattern(ArrayList<BoardLocation> locations, int type) {
		this.constituent = locations;
		this.blockingLocs = Board.findBlockingLocs(locations, type);
	}

	public ArrayList<BoardLocation> getBlockingLocs() {
		return blockingLocs;
	}

	public int getType() {
		return type;
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

	public static int findBubbleIndex(ArrayList<BoardLocation> locs, int type) {
		BoardLocation prev = null;
		switch (type) {
		case Pattern.ON_ROW:
			for (int i = 0; i < locs.size(); i++) {
				if (prev != null) {
					if (locs.get(i).getXPos() - prev.getXPos() > 1)
						return i;
				}
				prev = locs.get(i);
			}
		}
		return -1;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blockingLocs == null) ? 0 : blockingLocs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pattern other = (Pattern) obj;
		if (blockingLocs == null) {
			if (other.blockingLocs != null)
				return false;
		} else if (!blockingLocs.equals(other.blockingLocs))
			return false;
		return true;
	}
}
