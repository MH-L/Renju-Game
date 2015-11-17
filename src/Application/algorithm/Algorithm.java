package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import utils.DeepCopy;
import exceptions.InvalidIndexException;
import exceptions.InvalidPatternException;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public abstract class Algorithm {
	private Board board;
	private static final Random rand = new Random();
	protected VirtualBoard vBoard;
	protected boolean isFirst;

	public Algorithm(Board board, boolean isFirst) {
		this.board = board;
		this.isFirst = isFirst;
	}

	public ArrayList<BoardLocation> calculateAttack() {
		// get a list of all self stones
		ArrayList<BoardLocation> previousStones = isFirst ? board
				.getPlayer1Stone() : board.getPlayer2Stone();
		// calculate ALL feasible positions to move to
		// (maybe some of them are meaningless)
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation stone : previousStones) {
			ArrayList<BoardLocation> curCandidates = Board.findAdjacentLocs(stone);
			for (BoardLocation loc : curCandidates) {
				if (!candidates.contains(loc) && Board.isReachable(loc)
						&& !board.isOccupied(loc))
					candidates.add(loc);
			}
			curCandidates.clear();
		}
		Board anotherBoard = (Board) DeepCopy.copy(board);
		this.vBoard = VirtualBoard.getVBoard(anotherBoard);
		Iterator<BoardLocation> iter = candidates.iterator();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		while (iter.hasNext()) {
			BoardLocation adjacentLoc = iter.next();
			try {
				vBoard.updateBoardLite(adjacentLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.
					checkAllPatternsAroundLoc(adjacentLoc, vBoard, isFirst);
			for (Pattern pat : patterns)
				if (board.isPatternWinning(pat)) {
					retVal.clear();
					retVal.add(adjacentLoc);
					return retVal;
				}
			if (patterns.size() != 0)
				retVal.add(adjacentLoc);
			try {
				vBoard.withdrawMoveLite(adjacentLoc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	/**
	 *
	 * @param locations Candidate locations passed to the function.
	 * @return
	 */
	public BoardLocation processLocs(ArrayList<BoardLocation> locations) {
		if (locations.isEmpty())
			return board.findEmptyLocSpiral();
		this.vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		for (BoardLocation location : locations) {
			try {
				vBoard.updateBoardLite(location, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.
					checkAllPatternsAroundLoc(location, vBoard, isFirst);
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
		BoardLocation retVal = locations.get(getRandNum(locations.size()) - 1);
		return retVal;
	}

	public static int getRandNum(int modulo) {
		if (modulo <= 0)
			return -1;
		return rand.nextInt(modulo) + 1;
	}

	public Board getBoard() {
		return board;
	}

	public ArrayList<BoardLocation> getSelfStone() {
		return isFirst ? this.getBoard().getPlayer1Stone() : this.getBoard().getPlayer2Stone();
	}

	public ArrayList<BoardLocation> getOpponentStone() {
		return isFirst ? this.getBoard().getPlayer2Stone() : this.getBoard().getPlayer1Stone();
	}

	public ArrayList<BoardLocation> getOpponentCriticals() {
		return isFirst ? this.getBoard().getSecondCriticalLocs() :
			this.getBoard().getFirstCriticalLocs();
	}

	public ArrayList<BoardLocation> getSelfCriticals() {
		return isFirst ? this.getBoard().getFirstCriticalLocs() :
			this.getBoard().getSecondCriticalLocs();
	}

	public abstract ArrayList<BoardLocation> findLocation();

	public abstract BoardLocation findBestLocWhenStuck();

	/**
	 * Get all locations that are of distance 2 or less to some of the self
	 * stones.
	 * @param stones
	 * @param board
	 * @return
	 */
	public static ArrayList<BoardLocation> findFlexibleLocs(ArrayList<BoardLocation> stones, Board board) {
		ArrayList<BoardLocation> adjacentLocs = new ArrayList<BoardLocation>();
		for (BoardLocation location : stones) {
			ArrayList<BoardLocation> adjLocs = Board.findAdjacentLocs(location);
			for (BoardLocation adjLoc : adjLocs)
				if (!adjacentLocs.contains(adjLoc) && Board.isReachable(adjLoc)
						&& !board.isOccupied(adjLoc))
					adjacentLocs.add(adjLoc);
			adjLocs = Board.findJumpLocations(location);
			for (BoardLocation adj : adjLocs)
				if (!adjacentLocs.contains(adj) && Board.isReachable(adj)
						&& !board.isOccupied(adj))
					adjacentLocs.add(adj);
		}
		return adjacentLocs;
	}

	public BoardLocation makeFirstMoveFirst() {
		if (Board.getWidth() % 2 == 0) {
			ArrayList<BoardLocation> candidateLocations = new ArrayList<BoardLocation>();
			int height = Board.getHeight();
			int width = Board.getWidth();
			candidateLocations.add(new BoardLocation(height / 2, width / 2));
			candidateLocations.add(new BoardLocation(height / 2 - 1,
					width / 2 - 1));
			candidateLocations
					.add(new BoardLocation(height / 2 - 1, width / 2));
			candidateLocations.add(new BoardLocation(height / 2 - 1,
					width / 2 - 1));
			int randSeed = getRandNum(candidateLocations.size());
			switch (randSeed) {
			case 0:
				return candidateLocations.get(0);
			case 1:
				return candidateLocations.get(1);
			case 2:
				return candidateLocations.get(2);
			case 3:
				return candidateLocations.get(3);
			}
		} else {
			return new BoardLocation(Board.getHeight() / 2,
					Board.getWidth() / 2);
		}
		return new BoardLocation(8, 8);
	}

	public BoardLocation makeFirstMoveSecond() {
		assert (!board.isEmpty());
		BoardLocation firstStone = board.getPlayer1Stone().get(0);
		if (Board.isInCorner(firstStone) || Board.isOnSide(firstStone))
			return makeFirstMoveFirst();
		int randSeed = getRandNum(2);
		int desiredDist = 0;
		if (randSeed == 1) {
			desiredDist = 1;
		} else if (randSeed == 2) {
			desiredDist = 2;
		}
		ArrayList<BoardLocation> adjacents = Board.findAdjacentLocs(firstStone);
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation loc : adjacents) {
			if (Board.findDistance(loc, firstStone) == desiredDist)
				candidates.add(loc);
		}

		return Board.findLocationWithLargestDist(candidates);

	}

	public BoardLocation makeSecondMoveFirst() {
		BoardLocation firstMove = board.getPlayer1Stone().get(0);
		BoardLocation otherPlayerFirstMove = board.getPlayer2Stone().get(0);
		if (Board.findDistance(firstMove, otherPlayerFirstMove) >= 4) {
			int randSeed = getRandNum(8);
			return Board.findAdjacentLocs(firstMove).get(randSeed - 1);
		}
		int randSeed = getRandNum(2);
		int desiredDist = randSeed == 1 ? 2 : 3;
		ArrayList<BoardLocation> result = filterWithDesiredDist(
				otherPlayerFirstMove, desiredDist,
				Board.findAdjacentLocs(firstMove));
		randSeed = getRandNum(result.size());
		return result.get(randSeed - 1);
	}

	public BoardLocation makeSecondMoveSecond() {
		BoardLocation firstMove = board.getPlayer2Stone().get(0);
		BoardLocation firstMoveOther = board.getPlayer1Stone().get(0);
		BoardLocation secondMoveOther = board.getPlayer1Stone().get(1);
		if (Board.findDistance(firstMoveOther, secondMoveOther) >= 4) {
			int randSeed = getRandNum(2);
			ArrayList<BoardLocation> result = filterWithDesiredDist(firstMove,
					randSeed, Board.findAdjacentLocs(firstMove));
			BoardLocation retVal;
			do {
				randSeed = getRandNum(result.size());
				retVal = result.get(randSeed - 1);
			} while (board.isOccupied(retVal));
			return retVal;
		} else {
			ArrayList<BoardLocation> result = board.filterOccupied(Board.findAdjacentLocs(firstMove));
			int randSeed = getRandNum(result.size());
			return result.get(randSeed - 1);
		}
	}

	/**
	 * Makes move at the beginning.
	 * @return
	 */
	public BoardLocation makeMoveBeginning() {
		if (board.getTotalStones() == 1)
			return makeFirstMoveSecond();
		else if (board.getTotalStones() == 0)
			return makeFirstMoveFirst();
		else if (board.getTotalStones() == 2)
			return makeSecondMoveFirst();
		else if (board.getTotalStones() == 3)
			return makeSecondMoveSecond();
		else
			return makeMoveEnd();
	}

	/**
	 * Kind of ambiguous. TODO finish the doc.
	 * @return
	 */
	public BoardLocation doFundamentalCheck() {
		// TODO optimize this!
		// Get all patterns of the player.
		ArrayList<Pattern> selfPatterns = isFirst ?
				board.getFirstPattern() : board.getSecondPattern();
		ArrayList<Pattern> excellents = filterUrgentPats(selfPatterns, true);
		if (excellents.size() != 0)
			return findWinningLoc(excellents.get(0));
		// TODO optimize this!
		// Get all patterns of the other player.
		ArrayList<Pattern> patterns = isFirst ?
				board.getSecondPattern() : board.getFirstPattern();
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
		if (patterns.size() != 0) {
			ArrayList<BoardLocation> tofilter = extractBlockingLocs(patterns);
			ArrayList<BoardLocation> result = filterBlockingLocsAtk(tofilter);
			if (result.size() != 0) {
				BoardLocation blockAttack = result.get(getRandNum(result.size()) - 1);
				return blockAttack;
			}
			ArrayList<BoardLocation> filtered = keepOnlyBubble(patterns);
			return filtered.get(0);
		}
		return null;
	}

	private ArrayList<BoardLocation> getAllOpponentBlockingLocations() {
		ArrayList<Pattern> opponentPatterns = isFirst ? board.getSecondPattern()
				: board.getFirstPattern();
		return extractBlockingLocs(opponentPatterns);
	}

	/**
	 * Make move in the end phase.
	 * @return
	 */
	public BoardLocation makeMoveEnd() {
		BoardLocation result = doFundamentalCheck();
		if (result != null)
			return result;
		ArrayList<BoardLocation> blockingComps = blockPotentialCompositePat();
		if (blockingComps.size() != 0)
			return blockingComps.get(getRandNum(blockingComps.size()) - 1);
		ArrayList<BoardLocation> locations = calculateAttack();
		return processLocs(locations);
	}

	/**
	 * Keeps only those jump patterns.
	 * @param patterns
	 * @return
	 */
	public static ArrayList<BoardLocation> keepOnlyBubble(ArrayList<Pattern> patterns) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (Pattern pat : patterns) {
			ArrayList<BoardLocation> blockingLocs = pat.getBlockingLocs();
			if (pat.getClass() != DiscOpenPattern.class) {
				for (BoardLocation loc : blockingLocs) {
					if (!retVal.contains(loc))
						retVal.add(loc);
				}
			} else {
				int firstIncrement = 0;
				int secondIncrement = 0;
				switch (pat.getType()) {
				case Pattern.ON_COL:
					firstIncrement = 1;
					secondIncrement = 0;
					break;
				case Pattern.ON_ROW:
					firstIncrement = 0;
					secondIncrement = 1;
					break;
				case Pattern.ON_ULDIAG:
					firstIncrement = 1;
					secondIncrement = 1;
					break;
				case Pattern.ON_URDIAG:
					firstIncrement = 1;
					secondIncrement = -1;
					break;
				}
				BoardLocation firstStone = pat.findFirstStone();
				for (BoardLocation location : blockingLocs) {
					int xInc = location.getXPos() - firstStone.getXPos();
					int yInc = location.getYPos() - firstStone.getYPos();
					if (xInc == ((DiscOpenPattern) pat).getBubbleIndex() * secondIncrement
							&& yInc == ((DiscOpenPattern) pat).getBubbleIndex() * firstIncrement)
						retVal.add(location);
				}
			}
		}
		return retVal;
	}

	/**
	 * Finds the winning location. A winning locations is a
	 * location that extends an existing pattern to form five-
	 * in-a-row.
	 * @param pat
	 * @return
	 */
	public BoardLocation findWinningLoc(Pattern pat) {
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		ArrayList<BoardLocation> locations = pat.getLocations();
		int firstInc = 0;
		int secondInc = 0;
		switch (pat.getType()) {
		case Pattern.ON_ROW:
			firstInc = 0;
			secondInc = 1;
			break;
		case Pattern.ON_COL:
			firstInc = 1;
			secondInc = 0;
			break;
		case Pattern.ON_ULDIAG:
			firstInc = 1;
			secondInc = 1;
			break;
		case Pattern.ON_URDIAG:
			firstInc = 1;
			secondInc = -1;
			break;
		default:
			break;
		}
		for (BoardLocation loc : locations) {
			BoardLocation firstCandidate = new BoardLocation(loc.getYPos()
					+ firstInc, loc.getXPos() + secondInc);
			BoardLocation secondCandidate = new BoardLocation(loc.getYPos()
					- firstInc, loc.getXPos() - secondInc);
			if (Board.isReachable(firstCandidate)
					&& !board.isOccupied(firstCandidate)
					&& !locations.contains(firstCandidate)) {
				try {
					vBoard.updateBoardLite(firstCandidate, isFirst);
					if (vBoard.checkcol() || vBoard.checkrow() || vBoard.checkdiag())
						return firstCandidate;
				} catch (InvalidIndexException e) {
					continue;
				}
				try {
					vBoard.withdrawMoveLite(firstCandidate);
				} catch (InvalidIndexException e) {
					continue;
				}
			}

			if (Board.isReachable(secondCandidate)
					&& !board.isOccupied(secondCandidate)
					&& !locations.contains(secondCandidate)) {
				try {
					vBoard.updateBoardLite(secondCandidate, isFirst);
					if (vBoard.checkcol() || vBoard.checkrow()
							|| vBoard.checkdiag())
						return secondCandidate;
				} catch (InvalidIndexException e) {
					continue;
				}
				try {
					vBoard.withdrawMoveLite(secondCandidate);
				} catch (InvalidIndexException e) {
					continue;
				}
			}
		}
		try {
			throw new InvalidPatternException("No way to win!");
		} catch (InvalidPatternException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Keep only all board locations that are desiredDist from comparer.
	 * @param comparer
	 * @param desiredDist
	 * @param candidate
	 * @return
	 */
	public static ArrayList<BoardLocation> filterWithDesiredDist(
			BoardLocation comparer, int desiredDist,
			ArrayList<BoardLocation> candidate) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (BoardLocation loc : candidate) {
			if (Board.findDistance(loc, comparer) == desiredDist)
				retVal.add(loc);
		}
		return retVal;
	}

	/**
	 * Filter blocking locations in terms of being able to attack.
	 *
	 * @param blockingLocs
	 *            The list of blocking locations to filter.
	 * @return An arrayList of board locations which can form a pattern with
	 *         previous stones.
	 */
	public ArrayList<BoardLocation> filterBlockingLocsAtk(
			ArrayList<BoardLocation> blockingLocs) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		for (BoardLocation blockingloc : blockingLocs) {
			if (retVal.contains(blockingloc))
				continue;
			try {
				vBoard.updateBoardLite(blockingloc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> pats = BoardChecker.checkAllPatternsAroundLoc(blockingloc, vBoard,
					isFirst);
			if (pats.size() > 0) {
				try {
					vBoard.withdrawMoveLite(blockingloc);
					retVal.add(blockingloc);
				} catch (InvalidIndexException e) {
					continue;
				}
				continue;
			}
			for (Pattern patt : pats) {
				if (board.isPatternWinning(patt)) {
					retVal.clear();
					retVal.add(blockingloc);
				}
			}
			try {
				vBoard.withdrawMoveLite(blockingloc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	/**
	 * Filter out all patterns that are urgent (i.e. takes only
	 * one move to victory).
	 * @param patterns
	 * @param isSelf
	 * @return
	 */
	public ArrayList<Pattern> filterUrgentPats(
			ArrayList<Pattern> patterns, boolean isSelf) {
		ArrayList<Pattern> retVal = new ArrayList<Pattern>();
		boolean checker = isSelf ? isFirst : !isFirst;
		for (Pattern pat : patterns) {
			if (pat.getLocations().size() >= 4 && !board.isPatternDead(pat, checker))
				retVal.add(pat);
		}
		return retVal;
	}

	/**
	 * Filters out all patterns that are dead. By dead it means "unable to
	 * extend to five stones."
	 * @param toFilter
	 * @param isFirst
	 * @param board
	 */
	public static void filterOutDeadPats(ArrayList<Pattern> toFilter, boolean isFirst, Board board) {
		Iterator<Pattern> iter = toFilter.iterator();
		while (iter.hasNext()) {
			if (board.isPatternDead(iter.next(), isFirst))
				iter.remove();
		}
	}

	/**
	 * Filter out all patterns that are in control.
	 * @param patterns Patterns to be filtered.
	 * @param isFirst Indicating which player the method applies to.
	 * @param board The game board.
	 */
	public static void filterOutInControl(ArrayList<Pattern> patterns, boolean isFirst, Board board) {
		Iterator<Pattern> iter = patterns.iterator();
		while (iter.hasNext()) {
			Pattern candidate = iter.next();
			if (candidate.getClass() == ContOpenPattern.class)
				if (BoardChecker.isOpenPatInControl(board, (ContOpenPattern) candidate, isFirst))
					iter.remove();
		}
	}

	/**
	 * Find the boardLocation which can form the most
	 * sub-patterns if updated.
	 * @param locations
	 * 		Locations available.
	 * @return The location which can form the most sub-patterns if updated.
	 */
	public BoardLocation findLocWithMostConnection(ArrayList<BoardLocation> locations) {
		if (locations.isEmpty())
			return null;
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		BoardLocation maxLocation = null;
		int maxConnection = -1;
		for (BoardLocation loc : locations) {
			try {
				vBoard.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}

			ArrayList<Pattern> allSubPatterns =
					BoardChecker.checkAllSubPatternsArd(loc, vBoard, isFirst);
			if (allSubPatterns.size() > maxConnection) {
				maxConnection = allSubPatterns.size();
				maxLocation = loc;
			}
			try {
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return maxLocation;
	}

	/**
	 * Find location which extends the existing pattern and
	 * win the game. (i.e. make the other side unable to save.)
	 * @param pat
	 * @return
	 */
	public BoardLocation extendToWinning(Pattern pat) {
		// TODO if the pattern is blocked on one side, then
		// this method might not work.
		ArrayList<BoardLocation> boardLocs = pat.getLocations();
		int firstInc = 0;
		int secondInc = 0;
		switch (pat.getType()) {
		case Pattern.ON_ROW:
			firstInc = 0;
			secondInc = 1;
			break;
		case Pattern.ON_COL:
			firstInc = 1;
			secondInc = 0;
			break;
		case Pattern.ON_ULDIAG:
			firstInc = 1;
			secondInc = 1;
			break;
		case Pattern.ON_URDIAG:
			firstInc = 1;
			secondInc = -1;
			break;
		default:
			break;
		}
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		for (int j = 0; j < boardLocs.size(); j++) {
			BoardLocation loc = boardLocs.get(j);
			BoardLocation first = new BoardLocation(loc.getYPos() + firstInc,
					loc.getXPos() + secondInc);
			BoardLocation second = new BoardLocation(loc.getYPos() - firstInc,
					loc.getXPos() - secondInc);
			if (Board.isReachable(first) && !board.isOccupied(first)
					&& !boardLocs.contains(first)) {
				try {
					vBoard.updateBoardLite(first, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}
				ArrayList<Pattern> patterns = BoardChecker.checkAllPatternsAroundLoc(
						first, vBoard, isFirst);
				for (Pattern pat1 : patterns) {
					if (vBoard.isPatternWinning(pat1))
						return first;
				}
				try {
					vBoard.withdrawMoveLite(first);
				} catch (InvalidIndexException e) {
					continue;
				}
			}
			if (Board.isReachable(second) && !board.isOccupied(second)
					&& !boardLocs.contains(second)) {
				try {
					vBoard.updateBoardLite(second, isFirst);
				} catch (InvalidIndexException e) {
					continue;
				}
				ArrayList<Pattern> patterns = BoardChecker.checkAllPatternsAroundLoc(
						second, vBoard, isFirst);
				for (Pattern pat1 : patterns) {
					if (vBoard.isPatternWinning(pat1))
						return second;
				}
				try {
					vBoard.withdrawMoveLite(second);
				} catch (InvalidIndexException e) {
					continue;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the list of all blocking locations for the given patterns.
	 * @param patterns
	 * @return
	 */
	public static ArrayList<BoardLocation> extractBlockingLocs(
			ArrayList<Pattern> patterns) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (Pattern pat : patterns) {
			ArrayList<BoardLocation> candidates = pat.getBlockingLocs();
			for (BoardLocation loc : candidates) {
				if (!retVal.contains(loc))
					retVal.add(loc);
			}
		}
		return retVal;
	}

	/**
	 * Gets the list of all adjacent locations (both locations next
	 * to player's stones and jump locations). This method serves as a
	 * helper.
	 * @return An arraylist of all adjacent locations.
	 */
	public ArrayList<BoardLocation> extractAllAdjacentLocs() {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> selfStones = getSelfStone();
		ArrayList<BoardLocation> otherStones = getOpponentStone();
		for (BoardLocation self : selfStones) {
			ArrayList<BoardLocation> candidates = Board.findAdjacentLocs(self);
			candidates.addAll(Board.findJumpLocations(self));
			for (int i = 0; i < candidates.size(); i++) {
				BoardLocation cur = candidates.get(i);
				if (!retVal.contains(cur) && !selfStones.contains(cur) &&
						!otherStones.contains(cur) && Board.isReachable(cur))
					retVal.add(candidates.get(i));
			}
		}
		return retVal;
	}

	/**
	 * return the blocking location of potential composite patterns.
	 * The method is just a stub in the base class because only
	 * intermediate algorithm implements this.
	 * @return A list of board locations player can move to in order
	 * to block opponent's potential composite patterns.
	 */
	public ArrayList<BoardLocation> blockPotentialCompositePat() {
		return new ArrayList<BoardLocation>();
	}

	/**
	 * Find locations where urgent patterns can be formed.
	 * @param locationsAvailable
	 * 		A list of all locations available, often obtained by
	 * 		findFlexibleLocs().
	 * @return An arraylist of locations where urgent patterns can be formed.
	 */
	public ArrayList<BoardLocation> attackOnlyUrgent(ArrayList<BoardLocation> locationsAvailable) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		for (BoardLocation loc : locationsAvailable) {
			try {
				vBoard.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}

			ArrayList<Pattern> foundPatterns =
					BoardChecker.checkAllPatternsAroundLoc(loc, vBoard, isFirst);
			foundPatterns = filterUrgentPats(foundPatterns, true);
			if (foundPatterns.size() > 0)
				retVal.add(loc);
			try {
				vBoard.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	/**
	 * Check whether AI can attack continuously and win.
	 * @param availableLocs Locations available for the current attack.
	 * @param maxDepth Maximum depth of the board tree.
	 * @return A BoardLocation where AI can make move to win.
	 */
	public BoardLocation attackContinuously
			(ArrayList<BoardLocation> availableLocs, int maxDepth) {
		if (maxDepth <= 0)
			return null;
		ArrayList<BoardLocation> attackingLocs = attackOnlyUrgent(availableLocs);
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(getBoard()));
		for (BoardLocation attackingLoc : attackingLocs) {
			try {
				vBoard.updateBoard(attackingLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			Algorithm solver = new BasicAlgorithm(vBoard, !isFirst);
			BoardLocation tackleLocation = solver.makeMoveEnd();
			try {
				vBoard.updateBoard(tackleLocation, !isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> opponentPatterns = BoardChecker.
					checkAllPatternsAroundLoc(tackleLocation, vBoard, !isFirst);
			opponentPatterns = filterUrgentPats(opponentPatterns, false);
			if (!opponentPatterns.isEmpty()) {
				try {
					vBoard.withdrawMove(tackleLocation);
					vBoard.withdrawMove(attackingLoc);
				} catch (InvalidIndexException e) {
					continue;
				}
				continue;
			}
			ArrayList<BoardLocation> criticalLocs = isFirst ? vBoard.getFirstCriticalLocs() :
				vBoard.getSecondCriticalLocs();
			if (!criticalLocs.isEmpty())
				return attackingLoc;
			Algorithm helperAlg = new IntermediateAlgorithm(vBoard, isFirst);
			ArrayList<BoardLocation> selfStonesForHelper = isFirst ?
					vBoard.getPlayer1Stone() : vBoard.getPlayer2Stone();
			ArrayList<BoardLocation> relevantLocs =
					Algorithm.findFlexibleLocs(selfStonesForHelper, board);
			BoardLocation deepCalc = helperAlg.attackContinuously(relevantLocs, maxDepth - 1);
			if (deepCalc != null)
				return attackingLoc;
			else {
				try {
					vBoard.withdrawMove(tackleLocation);
					vBoard.withdrawMove(attackingLoc);
				} catch (InvalidIndexException e) {
					continue;
				}
				continue;
			}
		}
		return null;
	}

	private ArrayList<BoardLocation> extractAttackingLocsOnlyUrgent(ArrayList<BoardLocation> input) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (BoardLocation loc : input) {
			try {
				board.updateBoardLite(loc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> pats = BoardChecker.
					checkAllPatternsAroundLoc(loc, board, isFirst);
			ArrayList<Pattern> urgents = filterUrgentPats(pats, isFirst);
			if (!urgents.isEmpty())
				retVal.add(loc);
			try {
				board.withdrawMoveLite(loc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}

		return retVal;
	}

	public ArrayList<BoardLocation> generateFeasibleMoves() {
		ArrayList<BoardLocation> feasible = new ArrayList<BoardLocation>();

		// if the player can win, then return the first winning loc (for
		// reduced complexity).
		ArrayList<Pattern> selfPtns = isFirst ? board.getFirstPattern() :
			board.getSecondPattern();
		ArrayList<Pattern> excellents = filterUrgentPats(selfPtns, true);
		if (excellents.size() != 0) {
			BoardLocation loc = findWinningLoc(excellents.get(0));
			feasible.add(loc);
			return feasible;
		}
		ArrayList<Pattern> otherPattern = isFirst ? board.getSecondPattern() :
			board.getFirstPattern();
		ArrayList<Pattern> urgents = filterUrgentPats(otherPattern, false);
		if (urgents.isEmpty()) {
			if (!selfPtns.isEmpty()) {
				// opponent does not have any patterns that are urgent so
				// just extend the current pattern and win the game.
				BoardLocation locToWin = extendToWinning(selfPtns.get(0));
				feasible.add(locToWin);
				return feasible;
			} else {
				ArrayList<BoardLocation> locs = findFlexibleLocs(getSelfStone(), board);
				// both party do not have previous patterns.
				// 1.block 2.suppress
				if (!otherPattern.isEmpty()) {
					feasible.addAll(getAllOpponentBlockingLocations());
					ArrayList<BoardLocation> urgentLocs = extractAttackingLocsOnlyUrgent(locs);
					for (BoardLocation urgentLoc : urgentLocs) {
						if (!feasible.contains(urgentLoc))
							feasible.add(urgentLoc);
					}
					return feasible;
				}

				if (locs.isEmpty()) {
					feasible.add(makeMoveBeginning());
					return feasible;
				}
				return locs;
			}
		} else {
			// if opponent has urgent patterns then blocking is a must.
			return extractBlockingLocs(urgents);
		}
	}

	/**
	 * NOTICE: Method stub.
	 * Advanced algorithm and ultimate algorithm only.
	 * @return
	 */
	public BoardLocation makeMoveUsingGameTree() { return null; }
}
