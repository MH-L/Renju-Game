package model;

import java.util.ArrayList;

public abstract class WinningPattern {
	private boolean isPlayer;
	private ArrayList<BoardLocation> locations;
	
	public WinningPattern(ArrayList<BoardLocation> locs, boolean isPlayer) {
		this.isPlayer = isPlayer;
		this.locations = locs;
	}
}
