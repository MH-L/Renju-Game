package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public class BasicAlgorithm extends Algorithm {

	public BasicAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
	}

	@Override
	public ArrayList<BoardLocation> findLocation() {
		return null;
	}

	@Override
	public BoardLocation findBestLocWhenStuck() {
		// TODO Auto-generated method stub
		return null;
	}

}
