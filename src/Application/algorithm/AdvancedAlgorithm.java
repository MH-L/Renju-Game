package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class AdvancedAlgorithm extends Algorithm {
	private VirtualBoard vBoard;
	public static final int calculationSteps = 5;

	public AdvancedAlgorithm(Board board) {
		super(board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<BoardLocation> findLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardLocation findBestLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public VirtualBoard getVirtualBoard() {
		return this.vBoard;
	}

}
