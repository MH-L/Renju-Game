package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

/**
 * Defines a single pattern on the board. Example: an open three or a closed
 * four
 *
 * @author Minghao Liu
 *
 */
public abstract class Pattern {
	/**
	 * List of stones forming the pattern.
	 */
	private ArrayList<BoardLocation> constituent;
	/**
	 * List of BoardLocations that can block the pattern and are unoccupied.
	 */
	private ArrayList<BoardLocation> blockingLocs;
	private int type;

	/**
	 * The pattern is on a row.
	 */
	public static final int ON_ROW = 1;
	/**
	 * The pattern is on a column.
	 */
	public static final int ON_COL = 2;
	/**
	 * The pattern is on an upper-left diagonal.
	 */
	public static final int ON_ULDIAG = 3;
	/**
	 * The pattern is on an upper-right diagonal.
	 */
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

	/**
	 * Check if the two patterns are intersecting (i.e. shares the same board
	 * locations)
	 *
	 * @param pat
	 *            The pattern to be compared with the first pattern which is the
	 *            object the method is operating on.
	 * @return True if the two patterns intersect and false otherwise.
	 */
	public boolean isIntersecting(Pattern pat) {
		if (pat.getNumLocs() == 0)
			return false;
		for (BoardLocation loc1 : pat.getLocations())
			for (BoardLocation loc2 : this.constituent)
				if (loc1.compare(loc2))
					return true;
		return false;
	}

	/**
	 * Check if the two patterns are different
	 *
	 * @param pat
	 * @return True if the tow are different, false otherwise.
	 */
	public boolean isDifferent(Pattern pat) {
		for (BoardLocation loc1 : pat.getLocations())
			for (BoardLocation loc2 : this.constituent)
				if (!loc1.compare(loc2))
					return true;
		return false;
	}

	/**
	 * Finds the index where there is a bubble (i.e. an empty spot) inside a
	 * discrete pattern.
	 *
	 * @param locs
	 *            BoardLocations forming the pattern.
	 * @param type
	 *            Type of the pattern.
	 * @return Index of the empty spot.
	 */
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
			break;
		case Pattern.ON_COL:
		case Pattern.ON_ULDIAG:
		case Pattern.ON_URDIAG:
			for (int i = 0; i < locs.size(); i++) {
				if (prev != null) {
					if (locs.get(i).getYPos() - prev.getYPos() > 1)
						return i;
				}
				prev = locs.get(i);
			}
			break;
		default:
			break;
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
