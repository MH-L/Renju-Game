package Algorithm;

import java.util.ArrayList;

import Model.Board;
import Model.BoardLocation;

public abstract class Algorithm {
	private static Board board;

	public Algorithm(Board board) {
		this.board = board;
	}

	public abstract ArrayList<BoardLocation> findLocation();
	public abstract BoardLocation findBestLocation();
}
