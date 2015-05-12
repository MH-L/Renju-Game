package algorithm;

import java.util.ArrayList;

import exceptions.InvalidIndexException;
import utils.DeepCopy;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class IntermediateAlgorithm extends Algorithm {

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

	@Override
	public ArrayList<BoardLocation> blockPotentialCompositePat() {
		ArrayList<BoardLocation> otherPlayer = getOtherStone();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation loc : otherPlayer) {
			ArrayList<BoardLocation> adjLocs = Board.findAdjacentLocs(loc);
			adjLocs.addAll(Board.findJumpLocations(loc));
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
			if (BoardChecker.checkAllPatterns(vBoard, !isFirst).size() >= 2) {
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

	/**
	 * Method for developing composite patterns.
	 * @return
	 * 		ArrayList of BoardLocations where composite patterns come
	 * 		along after placing a stone.
	 */
	public ArrayList<BoardLocation> compositePatAtk() {
		ArrayList<BoardLocation> aiLoc = getSelfStone();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (BoardLocation loc : aiLoc) {
			ArrayList<BoardLocation> adjLocs = Board.findAdjacentLocs(loc);
			adjLocs.addAll(Board.findJumpLocations(loc));
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
			ArrayList<Pattern> patternsFound = BoardChecker.checkAllPatterns(vBoard, isFirst);
			if (patternsFound.size() >= 2) {
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

	public ArrayList<BoardLocation> intermediateAttack() {
		ArrayList<BoardLocation> composites = compositePatAtk();
		if (!composites.isEmpty())
			return composites;
		ArrayList<BoardLocation> relevantLocs = extractAllAdjacentLocs();
		for (BoardLocation loc : relevantLocs) {
			vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
			try {
				vBoard.updateBoard(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}

			for (BoardLocation test : relevantLocs) {
				if (test.equals(loc))
					continue;
				try {
					vBoard.updateBoard(test, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}
				try {
					vBoard.withdrawMove(test);
				} catch (InvalidIndexException e) {
					continue;
				}
			}

			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return null;
		//
	}

	@Override
	public ArrayList<BoardLocation> calculateAttack() {
		ArrayList<BoardLocation> composites = intermediateAttack();
		if (composites.isEmpty())
			return super.calculateAttack();
		return composites;
	}
}
