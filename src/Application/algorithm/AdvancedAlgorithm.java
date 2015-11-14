package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class AdvancedAlgorithm extends Algorithm {
	public static final int calculationSteps = 5;
	BoardTree tree;

	public AdvancedAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
		int turn = isFirst ? Board.TURN_SENTE : Board.TURN_GOTE;
		tree = new BoardTree(getBoard(), turn);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<BoardLocation> findLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardLocation findBestLocWhenStuck() {
		// TODO Auto-generated method stub
		return null;
	}

	public VirtualBoard getVirtualBoard() {
		return this.vBoard;
	}

}
