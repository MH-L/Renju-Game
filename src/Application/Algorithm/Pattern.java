package Algorithm;

import java.util.ArrayList;

import Model.BoardLocation;

/**
 * Defines a single pattern on the board.
 * Example: an open three or a closed four
 * @author Minghao Liu
 *
 */
public class Pattern {
	private ArrayList<BoardLocation> constituent;

	public Pattern(ArrayList<BoardLocation> locations) {
		this.constituent = locations;
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
