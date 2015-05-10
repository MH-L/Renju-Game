package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public abstract class RandomizedSelect extends Algorithm {

	public RandomizedSelect(Board board, boolean isFirst) {
		super(board, isFirst);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract ArrayList<BoardLocation> findLocation();
}
