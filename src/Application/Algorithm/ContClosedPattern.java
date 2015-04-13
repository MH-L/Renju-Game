package Algorithm;

import java.util.ArrayList;

import Model.Board;
import Model.BoardLocation;

public class ContClosedPattern extends ClosedPattern {

	public ContClosedPattern(ArrayList<BoardLocation> locations, int type, ArrayList<BoardLocation> blockedStones) {
		super(locations, type, blockedStones);
	}
}
