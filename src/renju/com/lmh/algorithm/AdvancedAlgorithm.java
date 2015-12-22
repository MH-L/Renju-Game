package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;
import renju.com.lmh.model.VirtualBoard;

public class AdvancedAlgorithm extends Algorithm {
	public static final int calculationSteps = 3;
	BoardTree tree;

	public AdvancedAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
		int turn = isFirst ? Board.TURN_SENTE : Board.TURN_GOTE;
		tree = new BoardTree(getBoard(), turn);
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

	@Override
	public BoardLocation makeMoveUsingGameTree() {
		return tree.getBestMove(calculationSteps);
	}
}
