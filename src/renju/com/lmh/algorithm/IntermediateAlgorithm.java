package renju.com.lmh.algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;
import renju.com.lmh.model.VirtualBoard;
import renju.com.lmh.utils.DeepCopy;

public class IntermediateAlgorithm extends Algorithm {
	private boolean isIntermediateAvailable = false;
	public IntermediateAlgorithm(Board board, boolean isFirst) {
		super(board, isFirst);
	}

	@Override
	public ArrayList<BoardLocation> findLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardLocation findBestLocWhenStuck() {
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		ArrayList<BoardLocation> applicableLocs = Algorithm.findFlexibleLocs(getSelfStone(), getBoard());
		if (applicableLocs.isEmpty())
			return getBoard().findEmptyLocSpiral();
		return findLocWithMostConnection(applicableLocs);
	}

	public VirtualBoard getVirtualBoard() {
		return this.vBoard;
	}

	@Override
	public ArrayList<BoardLocation> blockPotentialCompositePat() {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> filteredRetVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> intermediateLocations = new ArrayList<BoardLocation>();
		candidates = Algorithm.findFlexibleLocs(getOpponentStone(), getBoard());
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.getBoard()));
		if (!checkOtherCompositeAtk(vBoard)) {
			return new ArrayList<BoardLocation>();
		}
		for (BoardLocation loc : candidates) {
			try {
				vBoard.updateBoard(loc, !isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			if (!BoardChecker.checkAllCompositePatternsArd(vBoard, !isFirst, loc).isEmpty()) {
				retVal.add(loc);
			}
			try {
				vBoard.withdrawMove(loc);
			} catch (InvalidIndexException i) {
				continue;
			}
		}

		for (BoardLocation loc : candidates) {
			boolean applicable = true;
			try {
				vBoard.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			intermediateLocations.addAll(candidates);
			intermediateLocations.remove(loc);
			for (BoardLocation loc2 : intermediateLocations) {
				try {
					vBoard.updateBoardLite(loc2, !isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}
				ArrayList<CompositePattern> otherComposites =
						BoardChecker.checkAllCompositePatternsArd(vBoard, !isFirst, loc2);
				if (otherComposites.size() > 0) {
					try {
						applicable = false;
						vBoard.withdrawMoveLite(loc2);
					} catch (InvalidIndexException e) {
						break;
					}
					break;
				}

				try {
					vBoard.withdrawMoveLite(loc2);
				} catch (InvalidIndexException e) {
					continue;
				}
			}
			if (applicable) {
				filteredRetVal.add(loc);
			}
			try {
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException i) {
				continue;
			}
			intermediateLocations.clear();
		}

		retVal.retainAll(filteredRetVal);
		if (!retVal.isEmpty())
			return retVal;
		return filteredRetVal;
	}

	public boolean checkOtherCompositeAtk(VirtualBoard vBoard) {
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		candidates = Algorithm.findFlexibleLocs(getOpponentStone(), getBoard());
		for (BoardLocation loc : candidates) {
			try {
				vBoard.updateBoardLite(loc, !isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
//			ArrayList<Pattern> allPatterns = BoardChecker.checkAllPatterns(vBoard, !isFirst);
			ArrayList<CompositePattern> composites =
					BoardChecker.checkAllCompositePatternsArd(vBoard, !isFirst, loc);
			if (composites.size() > 0) {
				try {
					vBoard.withdrawMoveLite(loc);
				} catch (InvalidIndexException e) {
					return true;
				}
				return true;
			}
			try {
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return false;
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
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.getBoard()));
		candidates = Algorithm.findFlexibleLocs(aiLoc, getBoard());
		for (BoardLocation loc : candidates) {
			try {
				vBoard.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
//			ArrayList<Pattern> patternsFound = BoardChecker.checkAllPatterns(vBoard, isFirst);
			ArrayList<CompositePattern> composites =
					BoardChecker.checkAllCompositePatternsArd(vBoard, isFirst, loc);
			if (!composites.isEmpty()) {
				retVal.add(loc);
			}
			CompositePattern.filterUrgentComposites(composites);
			if (composites.size() != 0) {
				try {
					vBoard.withdrawMoveLite(loc);
				} catch (InvalidIndexException e) {

				}
				retVal.clear();
				retVal.add(loc);
				return retVal;
			}
			try {
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	public ArrayList<BoardLocation> intermediateAttack() {
		// Check this function since its logic is complicated
		// Otherwise, try other methods.
		ArrayList<BoardLocation> bestRetVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> betterAlt = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> alternative = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> relevantLocs = extractAllAdjacentLocs();
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		for (BoardLocation loc : relevantLocs) {
			try {
				vBoard.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
//			if (!BoardChecker.checkAllPatternsAroundLoc(loc, vBoard, isFirst).isEmpty()) {
//				try {
//					vBoard.withdrawMoveLite(loc);
//				} catch (InvalidIndexException e) {
//					continue;
//				}
//				continue;
//			}
			// newRelLocs stores all relevant locations after updating loc
			// Duplicate removal is necessary since that will greatly
			// reduce runtime
			ArrayList<BoardLocation> newRelLocs = new ArrayList<BoardLocation>();
			newRelLocs.addAll(relevantLocs);
			ArrayList<BoardLocation> newCandidates = Board.findAdjacentLocs(loc);
			newCandidates.addAll(Board.findJumpLocations(loc));
			for (BoardLocation loc2 : newCandidates) {
				if (!newRelLocs.contains(loc2) && !vBoard.isOccupied(loc2))
					newRelLocs.add(loc2);
			}
			newRelLocs.remove(loc); // needs to remove loc since it is still in there
			// this line of code may not be robust (but saves time)
			ArrayList<BoardLocation> possibleLocs = new ArrayList<BoardLocation>();
			ArrayList<BoardLocation> bestLocs = new ArrayList<BoardLocation>();

			for (BoardLocation test : newRelLocs) {
				try {
					vBoard.updateBoardLite(test, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}

				ArrayList<CompositePattern> allComposites =
						BoardChecker.checkAllCompositePatternsArd(vBoard, isFirst, test);

				if (!allComposites.isEmpty())
					possibleLocs.add(test);
				CompositePattern.filterUrgentComposites(allComposites);
				if (!allComposites.isEmpty())
					bestLocs.add(test);

				try {
					vBoard.withdrawMoveLite(test);
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
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		if (!bestRetVal.isEmpty()) {
			isIntermediateAvailable = true;
			return bestRetVal;
		}
		else if (!retVal.isEmpty()) {
			isIntermediateAvailable = false;
			return retVal;
		}
		else if (!betterAlt.isEmpty()) {
			isIntermediateAvailable = false;
			return betterAlt;
		}
		else {
			isIntermediateAvailable = false;
			return alternative;
		}
	}

	public ArrayList<BoardLocation> doOtherCheck(ArrayList<Pattern> otherPatterns) {
		return null;
	}

	@Override
	public ArrayList<BoardLocation> calculateAttack() {
		ArrayList<BoardLocation> goodList = compositePatAtk();
		if (!goodList.isEmpty())
			return goodList;
		ArrayList<BoardLocation> composites = intermediateAttack();
		if (!composites.isEmpty() && isIntermediateAvailable) {
			isIntermediateAvailable = false;
			return composites;
		}
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
				vBoard.updateBoardLite(adjacentLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.
					checkAllContPatternsArd(adjacentLoc, vBoard, isFirst);
			ArrayList<Pattern> candidatePats = BoardChecker.
					checkAllContPatternsArd(adjacentLoc, vBoard, isFirst);
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
				vBoard.withdrawMoveLite(adjacentLoc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}

		if (retVal.isEmpty()) {
			if (candidateRetVal.isEmpty())
				return composites;
			else
				return candidateRetVal;
		}
		return retVal;
	}

	@Override
	public BoardLocation makeMoveEnd() {
		// TODO create cache for the continuous attack to win.
		isIntermediateAvailable = false;
		BoardLocation result2 = doFundamentalCheck();
		if (result2 != null)
			return result2;

		ArrayList<BoardLocation> criticalLocations = isFirst ?
				getBoard().getFirstCriticalLocs() : getBoard().getSecondCriticalLocs();
		// Already optimized version.
		ArrayList<Pattern> patterns = isFirst ?
				getBoard().getSecondPattern() : getBoard().getFirstPattern();
		ArrayList<CompositePattern> opponentComposites =
				CompositePattern.makeCompositePats(patterns);
		ArrayList<BoardLocation> tofilter = extractBlockingLocs(patterns);
		if (!criticalLocations.isEmpty())
			return criticalLocations.get(0);
		ArrayList<BoardLocation> flexibles = findFlexibleLocs(getSelfStone(), getBoard());
		ArrayList<BoardLocation> urgentLocs = attackOnlyUrgent(flexibles);
//		BoardLocation strategicLoc = attackContinuously(flexibles, 5);
//		if (strategicLoc != null)
//			return strategicLoc;
		if (!opponentComposites.isEmpty()) {
			ArrayList<BoardLocation> bestDefence = attackOnlyUrgent(tofilter);
			if (bestDefence.size() > 0)
				return (findLocWithMostConnection(bestDefence));

			if (!flexibles.isEmpty()) {
				BoardLocation defenceUsingUrgent = findLocWithMostConnection(urgentLocs);
				if (defenceUsingUrgent != null)
					return defenceUsingUrgent;
			}
		}


		if (patterns.size() != 0) {
			ArrayList<BoardLocation> result = filterBlockingLocsAtk(tofilter);
			if (result.size() != 0) {
				BoardLocation blockAttack = result.get(getRandNum(result.size()) - 1);
				return blockAttack;
			}
			ArrayList<BoardLocation> filtered = keepOnlyBubble(patterns);
			return filtered.get(0);
		}

		ArrayList<BoardLocation> opponentCriticals = isFirst ? getBoard().getSecondCriticalLocs()
				: getBoard().getFirstCriticalLocs();
		if (!opponentCriticals.isEmpty()) {
			ArrayList<BoardLocation> tackleLocs = blockCriticals();
			if (tackleLocs.isEmpty()) {
				if (!urgentLocs.isEmpty())
					return findLocWithMostConnection(urgentLocs);
				// Basically gives up.
				return getBoard().findEmptyLocSpiral();
			}
			ArrayList<BoardLocation> attackingLocs = filterBlockingLocsAtk(tackleLocs);
			if (attackingLocs.isEmpty())
				return findLocWithMostConnection(tackleLocs);
			else
				return findLocWithMostConnection(attackingLocs);
		}

		ArrayList<BoardLocation> locations = calculateAttack();
		if (locations.size() > 0)
			return locations.get(0);
		ArrayList<BoardLocation> blockingComps = blockPotentialCompositePat();
		ArrayList<BoardLocation> filtered = filterBlockingLocsAtk(blockingComps);
		if (filtered.size() != 0)
			return filtered.get(getRandNum(filtered.size()) - 1);
		else if (blockingComps.size() != 0)
			return blockingComps.get(getRandNum(blockingComps.size()) - 1);
		return processLocs(locations);
	}

	public ArrayList<BoardLocation> blockCriticals() {
		// TODO Auto-generated method stub
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		ArrayList<BoardLocation> otherStones = getOpponentStone();
		ArrayList<BoardLocation> flexibles = findFlexibleLocs(otherStones, getBoard());
		for (BoardLocation possibleLoc : flexibles) {
			try {
				vBoard.updateBoard(possibleLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}

			ArrayList<BoardLocation> opponentCriticals = isFirst ? vBoard.getSecondCriticalLocs()
					: vBoard.getFirstCriticalLocs();
			if (opponentCriticals.isEmpty())
				retVal.add(possibleLoc);
			try {
				vBoard.withdrawMove(possibleLoc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	@Override
	public BoardLocation processLocs(ArrayList<BoardLocation> locations) {
		if (locations.isEmpty())
			return findBestLocWhenStuck();
		this.vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		int maxIndex = 0;
		int maxSize = -1;
		for (int i = 0; i < locations.size(); i++) {
			BoardLocation location = locations.get(i);
			try {
				vBoard.updateBoardLite(location, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.
					checkAllPatternsAroundLoc(location, vBoard, isFirst);
			ArrayList<Pattern> subPatterns = BoardChecker.
					checkAllSubPatternsArd(location, vBoard, isFirst);
			if (subPatterns.size() > maxSize) {
				maxIndex = i;
				maxSize = subPatterns.size();
			}
			for (Pattern pat : patterns) {
				if (vBoard.isPatternWinning(pat))
					return location;
			}
			try {
				vBoard.withdrawMoveLite(location);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
//		BoardLocation retVal = locations.get(getRandNum(locations.size()) - 1);
		return locations.get(maxIndex);
	}

	@Override
	public BoardLocation doFundamentalCheck() {
		ArrayList<Pattern> selfPatterns = isFirst ?
				getBoard().getFirstPattern() : getBoard().getSecondPattern();
		ArrayList<Pattern> excellents = filterUrgentPats(selfPatterns, true);
		if (excellents.size() != 0)
			return findWinningLoc(excellents.get(0));
		for (Pattern pat : selfPatterns) {
			if (getBoard().isPatternWinning(pat))
				return findWinningLoc(pat);
		}
		ArrayList<Pattern> patterns = isFirst ?
				getBoard().getSecondPattern() : getBoard().getFirstPattern();
		ArrayList<Pattern> urgents = filterUrgentPats(patterns, false);
		if (urgents.size() != 0) {
			ArrayList<BoardLocation> result = extractBlockingLocs(urgents);
			if (result.size() != 0)
				return result.get(0);
		}
		// No urgent patterns.
		if (selfPatterns.size() != 0) {
			for (Pattern pat : selfPatterns) {
				BoardLocation retLoc = extendToWinning(pat);
				if (retLoc != null)
					return retLoc;
			}
		}

		return null;
	}

	@Override
	public BoardLocation makeSecondMoveFirst() {
		ArrayList<BoardLocation> opponent = getOpponentStone();
		ArrayList<BoardLocation> self = getSelfStone();
		BoardLocation selfOnlyStone = self.get(0);
		BoardLocation opponentOnlyStone = opponent.get(0);
		int xIncrement = selfOnlyStone.getXPos() - opponentOnlyStone.getXPos();
		int yIncrement = selfOnlyStone.getYPos() - opponentOnlyStone.getYPos();
		int distance = Board.findDistance(selfOnlyStone, opponentOnlyStone);
		if (distance == 1) {
			int firstRnd = getRandNum(2);
			int secondRnd = getRandNum(2);
			int thirdRnd = getRandNum(2);
			if (firstRnd == 2 && secondRnd == 2 && thirdRnd == 2) {
				BoardLocation candidateLoc = new BoardLocation(opponentOnlyStone.getYPos() - yIncrement,
						opponentOnlyStone.getXPos() - xIncrement);
				if (Board.isReachable(candidateLoc))
					return candidateLoc;
			} else {
				ArrayList<BoardLocation> applicable = Board.findAdjacentLocs(selfOnlyStone);
				ArrayList<BoardLocation> candidate = new ArrayList<BoardLocation>();
				for (BoardLocation loc : applicable) {
					if (getBoard().isOccupied(loc))
						continue;
					if (Board.findDistance(loc, selfOnlyStone) == firstRnd)
						candidate.add(loc);
				}

				Iterator<BoardLocation> iter = candidate.iterator();
				while (iter.hasNext()) {
					BoardLocation curLoc = iter.next();
					if (BoardChecker.checkAllSubPatternsArd(curLoc, getBoard(), true).isEmpty())
						iter.remove();
				}
				if (candidate.isEmpty())
					return getBoard().findEmptyLocSpiral();
				return candidate.get(getRandNum(candidate.size()) - 1);
			}
		} else if (distance == 2 && Math.abs(xIncrement) == 1 && Math.abs(yIncrement) == 1) {
			// Diagonally (1,1)
			int randSeed = getRandNum(3);
			ArrayList<BoardLocation> applicable = Board.findAdjacentLocs(opponentOnlyStone);
			applicable = filterWithDesiredDist(selfOnlyStone, 2, applicable);
			ArrayList<BoardLocation> aroundSelfLocs = Board.findAdjacentLocs(selfOnlyStone);
			ArrayList<BoardLocation> oneStep = filterWithDesiredDist(selfOnlyStone, 1, aroundSelfLocs);
			ArrayList<BoardLocation> twoStep = filterWithDesiredDist(selfOnlyStone, 2, aroundSelfLocs);
			if (randSeed == 3) {
				if (!twoStep.isEmpty()) {
					// the opponent only stone is two steps away.
					twoStep.remove(opponentOnlyStone);
					return twoStep.get(getRandNum(twoStep.size()) - 1);
				}
			} else if (randSeed == 2) {
				if (!oneStep.isEmpty())
					return oneStep.get(getRandNum(oneStep.size()) - 1);
			} else {
				if (!applicable.isEmpty())
					return applicable.get(getRandNum(applicable.size()) - 1);
			}
			return getBoard().findEmptyLocSpiral();
		} else {
			ArrayList<BoardLocation> applicable = Board.findAdjacentLocs(opponentOnlyStone);
			Iterator<BoardLocation> iter = applicable.iterator();
			vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
			while (iter.hasNext()) {
				BoardLocation loc = iter.next();
				try {
					vBoard.updateBoard(loc, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}
				if (BoardChecker.checkAllSubPatternsArd(loc, vBoard, isFirst).isEmpty())
					iter.remove();
				try {
					vBoard.withdrawMove(loc);
				} catch (InvalidIndexException e) {
					continue;
				}
			}
			if (applicable.isEmpty())
				return getBoard().findEmptyLocSpiral();
			return applicable.get(getRandNum(applicable.size()) - 1);
		}
		return getBoard().findEmptyLocSpiral();
	}

	@Override
	public BoardLocation makeSecondMoveSecond() {
		ArrayList<BoardLocation> opponent = getOpponentStone();
		ArrayList<BoardLocation> self = getSelfStone();
		BoardLocation selfOnlyStone = self.get(0);
		ArrayList<Pattern> opSubPatterns =
				BoardChecker.checkAllSubPatternsArd(opponent.get(1), getBoard(), true);
		if (opSubPatterns.size() > 0) {
			ArrayList<BoardLocation> blockingLocs = opSubPatterns.get(0).getBlockingLocs();
			return findLocWithMostConnection(blockingLocs);
		}
		int randNum = getRandNum(2);
		ArrayList<BoardLocation> locs = Board.findAdjacentLocs(selfOnlyStone);
		ArrayList<BoardLocation> candidate = new ArrayList<BoardLocation>();
		for (BoardLocation location : locs) {
			if (getBoard().isOccupied(location))
				continue;
			if (Board.findDistance(selfOnlyStone, location) == randNum)
				candidate.add(location);
		}
		if (candidate.isEmpty())
			return getBoard().findEmptyLocSpiral();
		int randSelection = getRandNum(candidate.size()) - 1;
		return candidate.get(randSelection);
	}

}
