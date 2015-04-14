package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public abstract class Algorithm {
	private static Board board;

	public Algorithm(Board board) {
		this.board = board;
	}

	public abstract ArrayList<BoardLocation> findLocation();
	public abstract BoardLocation findBestLocation();
}
