package algorithm;

import java.util.ArrayList;

import exceptions.InvalidIndexException;
import utils.DeepCopy;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class IntermediateAlgorithm extends Algorithm {
	public static final int calculationSteps = 2;

	public IntermediateAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
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

	public ArrayList<BoardLocation> blockPotentialCompositePat() {
		ArrayList<BoardLocation> otherPlayer = getOtherStone();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation loc : otherPlayer) {
			ArrayList<BoardLocation> adjLocs = Board.findAdjacentLocs(loc);
			adjLocs.addAll(Board.getJumpLocations(loc));
			for (BoardLocation loc2 : adjLocs)
				if (!candidates.contains(loc2))
					candidates.add(loc2);
		}
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.getBoard()));
		for (BoardLocation loc : candidates) {
			try {
				vBoard.updateBoard(loc, !isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			if (BoardChecker.checkAllPatterns(vBoard, isFirst).size() >= 2) {
				retVal.add(loc);
			}
			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException i) {
				continue;
			}
		}
		return retVal;
	}

	public ArrayList<BoardLocation> intermediateAttack() {
		ArrayList<BoardLocation> aiLoc = getSelfStone();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (BoardLocation loc : aiLoc) {
			ArrayList<BoardLocation> adjLocs = Board.findAdjacentLocs(loc);
			adjLocs.addAll(Board.getJumpLocations(loc));
			for (BoardLocation loc2 : adjLocs)
				if (!candidates.contains(loc2))
					candidates.add(loc2);
		}
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.getBoard()));
		for (BoardLocation loc : candidates) {
			try {
				vBoard.updateBoard(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			if (BoardChecker.checkAllPatterns(vBoard, isFirst).size() >= 2) {
				retVal.add(loc);
			}
			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}
}
