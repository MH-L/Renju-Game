package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;
import renju.com.lmh.model.VirtualBoard;

public class UltimateAlgorithm extends Algorithm {

	public UltimateAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<BoardLocation> findLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardLocation findBestLocWhenStuck() {
		// TODO Auto-generated method stub
		return null;
	}

	public VirtualBoard getVirtualBoard() {
		return this.vBoard;
	}

}
