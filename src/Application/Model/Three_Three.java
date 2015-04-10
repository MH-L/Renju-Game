package Model;

import java.util.ArrayList;

public class Three_Three extends WinningPattern {

	public Three_Three(ArrayList<BoardLocation> locs, boolean isPlayer) {
		super(locs, isPlayer);
		// TODO Auto-generated constructor stub
	}
	
	public BoardLocation findFour() {
		return new BoardLocation(0,0);
	}

}
