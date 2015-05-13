package algorithm;

import java.util.ArrayList;
import java.util.Iterator;

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
		ArrayList<BoardLocation> bestRetVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> betterAlt = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> alternative = new ArrayList<BoardLocation>();
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
			ArrayList<BoardLocation> bestLocs = new ArrayList<BoardLocation>();

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
				CompositePattern.filterUrgentComposites(allComposites);
				if (!allComposites.isEmpty())
					bestLocs.add(test);

				try {
					vBoard.withdrawMove(test);
				} catch (InvalidIndexException e) {
					continue;
				}
			}

			if (bestLocs.size() >= 2)
				bestRetVal.add(loc);
			if (bestLocs.size() >= 1)
				betterAlt.add(loc);
			if (possibleLocs.size() >= 1)
				alternative.add(loc);
			if (possibleLocs.size() >= 2)
				retVal.add(loc);

			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		if (!bestRetVal.isEmpty())
			return bestRetVal;
		else if (!retVal.isEmpty())
			return retVal;
		else if (!betterAlt.isEmpty())
			return betterAlt;
		else
			return alternative;
	}

	@Override
	public ArrayList<BoardLocation> calculateAttack() {
		ArrayList<BoardLocation> composites = intermediateAttack();
		if (!composites.isEmpty())
			return composites;
		ArrayList<BoardLocation> previousStones = isFirst ? getBoard()
				.getPlayer1Stone() : getBoard().getPlayer2Stone();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation stone : previousStones) {
			ArrayList<BoardLocation> curCandidates = Board.findAdjacentLocs(stone);
			for (BoardLocation loc : curCandidates) {
				if (!candidates.contains(loc) && Board.isReachable(loc)
						&& !getBoard().isOccupied(loc))
					candidates.add(loc);
			}
			curCandidates.clear();
		}
		Board anotherBoard = (Board) DeepCopy.copy(getBoard());
		this.vBoard = VirtualBoard.getVBoard(anotherBoard);
		Iterator<BoardLocation> iter = candidates.iterator();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> candidateRetVal = new ArrayList<BoardLocation>();
		while (iter.hasNext()) {
			BoardLocation adjacentLoc = iter.next();
			try {
				vBoard.updateBoard(adjacentLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.checkAllContPatterns(vBoard, isFirst);
			ArrayList<Pattern> candidatePats = BoardChecker.checkAllPatterns(vBoard, isFirst);
			for (Pattern pat : patterns)
				if (getBoard().isPatternWinning(pat)) {
					retVal.clear();
					retVal.add(adjacentLoc);
					return retVal;
				}
			if (candidatePats.size() != 0)
				candidateRetVal.add(adjacentLoc);
			if (patterns.size() != 0)
				retVal.add(adjacentLoc);
			try {
				vBoard.withdrawMove(adjacentLoc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}

		if (retVal.isEmpty()) {
			return candidateRetVal;
		}
		return retVal;
	}

}
