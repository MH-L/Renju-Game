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
		// Check this function since its logic is complicated
		ArrayList<BoardLocation> composites = compositePatAtk();
		// If it can form composite patterns, then return such locations.
		if (!composites.isEmpty())
			return composites;
		// Otherwise, try other methods.
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> relevantLocs = extractAllAdjacentLocs();
		for (BoardLocation loc : relevantLocs) {
			vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
			try {
				vBoard.updateBoard(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			// newRelLocs stores all relevant locations after updating loc
			// Duplicate removal is necessary since that will greatly
			// reduce runtime
			ArrayList<BoardLocation> newRelLocs = new ArrayList<BoardLocation>();
			newRelLocs.addAll(relevantLocs);
			ArrayList<BoardLocation> newCandidates = Board.findAdjacentLocs(loc);
			newCandidates.addAll(Board.findJumpLocations(loc));
			for (BoardLocation loc2 : newCandidates) {
				if (!newRelLocs.contains(loc2))
					newRelLocs.add(loc2);
			}
			newRelLocs.remove(loc); // needs to remove loc since it is still in there
			// this line of code may not be robust (but saves time)
			ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();

			for (BoardLocation test : newRelLocs) {
				try {
					vBoard.updateBoard(test, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}

				ArrayList<Pattern> allPatterns = BoardChecker.checkAllPatterns(vBoard, isFirst);
				ArrayList<CompositePattern> allComposites = CompositePattern.makeCompositePats(allPatterns);
				if (!allComposites.isEmpty())
					possibleLocs.add(test);

				try {
					vBoard.withdrawMove(test);
				} catch (InvalidIndexException e) {
					continue;
				}
			}

			if (possibleLocs.size() >= 2)
				retVal.add(loc);

			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
		// Something needs to be done here!
	}

	@Override
	public ArrayList<BoardLocation> calculateAttack() {
		ArrayList<BoardLocation> composites = intermediateAttack();
		if (composites.isEmpty())
			return super.calculateAttack();
		return composites;
	}
}
