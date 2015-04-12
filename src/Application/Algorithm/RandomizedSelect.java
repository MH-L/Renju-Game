package Algorithm;

import java.util.ArrayList;

import Model.Board;
import Model.BoardLocation;

public abstract class RandomizedSelect extends Algorithm {

	public RandomizedSelect(Board board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract ArrayList<BoardLocation> findLocation();
}
