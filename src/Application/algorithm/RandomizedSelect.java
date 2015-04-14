package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public abstract class RandomizedSelect extends Algorithm {

	public RandomizedSelect(Board board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract ArrayList<BoardLocation> findLocation();
}
