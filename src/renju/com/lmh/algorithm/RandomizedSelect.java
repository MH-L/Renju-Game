package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

public abstract class RandomizedSelect extends Algorithm {

	public RandomizedSelect(Board board, boolean isFirst) {
		super(board, isFirst);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract ArrayList<BoardLocation> findLocation();
}
