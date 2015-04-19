package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import application.Game;
import utils.DeepCopy;
import exceptions.InvalidIndexException;
import exceptions.InvalidPatternException;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public abstract class Algorithm {
	private static Board board;
	private static final Random rand = new Random();
	protected VirtualBoard vBoard;
	boolean isFirst;

	public Algorithm(Board board, boolean isFirst) {
		Algorithm.board = board;
		this.isFirst = isFirst;
	}

	public ArrayList<BoardLocation> calculateAttack() {
		ArrayList<BoardLocation> previousStones = isFirst ? board
				.getPlayer1Stone() : board.getPlayer2Stone();
		ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
		for (BoardLocation stone : previousStones) {
			ArrayList<BoardLocation> curCandidates = Board
					.findAdjacentLocs(stone);
			curCandidates.addAll(Board.getJumpLocations(stone));
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
				vBoard.updateBoard(adjacentLoc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(vBoard,
					isFirst);
			for (Pattern pat : patterns)
				if (board.isPatternWinning(pat)) {
					retVal.clear();
					retVal.add(adjacentLoc);
					return retVal;
				}
			if (BoardChecker.checkAllPatterns(vBoard, isFirst).size() != 0)
				retVal.add(adjacentLoc);
			try {
				vBoard.withdrawMove(adjacentLoc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

	public BoardLocation processLocs(ArrayList<BoardLocation> locations) {
		if (locations.isEmpty())
			return board.findEmptyLocSpiral();
		this.vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		for (BoardLocation location : locations) {
			try {
				vBoard.updateBoard(location, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(vBoard,
					!isFirst);
			for (Pattern pat : patterns) {
				if (board.isPatternWinning(pat))
					return location;
			}
			try {
				vBoard.withdrawMove(location);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		BoardLocation retVal = locations.get(getRandNum(locations.size()) - 1);
		// System.out.format("The return value I gave is (%d, %d).\n",
		// retVal.getXPos(), retVal.getYPos());
		return retVal;
	}

	public static int getRandNum(int modulo) {
		if (modulo <= 0)
			return -1;
		return rand.nextInt(modulo) + 1;
	}

	public abstract ArrayList<BoardLocation> findLocation();

	public abstract BoardLocation findBestLocation();

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

		return Board.getLocationWithLargestDist(candidates);

	}

	public BoardLocation makeMoveBeginning() {
		if (board.getTotalStones() == 1)
			return makeFirstMoveSecond();
		else if (board.getTotalStones() == 0)
			return makeFirstMoveFirst();
		else if (board.getTotalStones() == 2)
			return makeSecondMoveFirst();
		else if (board.getTotalStones() == 3)
			return makeSecondMoveSecond();
		else {
			ArrayList<Pattern> selfPatterns = BoardChecker.checkAllPatterns(
					board, isFirst);
			ArrayList<Pattern> excellents = filterUrgentPats(selfPatterns);
			if (excellents.size() != 0)
				return findWinningLoc(excellents.get(0));
			for (Pattern pat : selfPatterns) {
				if (board.isPatternWinning(pat))
					return findWinningLoc(pat);
			}
			ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board,
					!isFirst);
			ArrayList<Pattern> urgents = filterUrgentPats(patterns);
			if (urgents.size() != 0) {
				ArrayList<BoardLocation> tofilter = extractBlockingLocs(patterns);
				ArrayList<BoardLocation> result = filterBlockingLocsAtk(tofilter);
				if (result.size() != 0) {
					return result.get(getRandNum(result.size()) - 1);
				} else
					return tofilter.get(0);
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
					BoardLocation blockAttack = result.get(getRandNum(result
							.size() - 1));
					// System.out
					// .format("The value I gave (for blocking attack) is (%d, %d).\n",
					// blockAttack.getXPos(),
					// blockAttack.getYPos());
					return blockAttack;
				}
				// System.out
				// .format("Special case: The value I gave (for blocking attack) is (%d, %d).\n",
				// tofilter.get(0).getXPos(), tofilter.get(0)
				// .getYPos());
				return tofilter.get(0);
			}
			ArrayList<BoardLocation> locations = calculateAttack();
			return processLocs(locations);
		}
	}

	private BoardLocation extendToWinning(Pattern pat) {
		ArrayList<BoardLocation> boardLocs = pat.getLocations();
		int checker;
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
		if (pat.getClass() == ContOpenPattern.class)
			for (BoardLocation loc : boardLocs) {
				BoardLocation first = new BoardLocation(loc.getYPos()
						+ firstInc, loc.getXPos() + secondInc);
				BoardLocation second = new BoardLocation(loc.getYPos()
						- firstInc, loc.getXPos() - secondInc);
				if (Board.isReachable(first) && !board.isOccupied(first)
						&& !boardLocs.contains(first)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(first);
					Pattern newPat = new ContOpenPattern(newLocs,
							pat.getType(), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return first;
				}
				if (Board.isReachable(second) && !board.isOccupied(second)
						&& !boardLocs.contains(second)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(second);
					Pattern newPat = new ContOpenPattern(newLocs,
							pat.getType(), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return second;
				}
			}
		else if (pat.getClass() == ContClosedPattern.class)
			for (BoardLocation loc : boardLocs) {
				BoardLocation first = new BoardLocation(loc.getYPos()
						+ firstInc, loc.getXPos() + secondInc);
				BoardLocation second = new BoardLocation(loc.getYPos()
						- firstInc, loc.getXPos() - secondInc);
				if (Board.isReachable(first) && !board.isOccupied(first)
						&& !boardLocs.contains(first)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(first);
					Pattern newPat = new ContClosedPattern(newLocs,
							pat.getType(), board.getBlockedStones(newLocs,
									pat.getType()), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return first;
				}
				if (Board.isReachable(second) && !board.isOccupied(second)
						&& !boardLocs.contains(second)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(second);
					Pattern newPat = new ContClosedPattern(newLocs,
							pat.getType(), board.getBlockedStones(newLocs,
									pat.getType()), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return second;
				}
			}
		else if (pat.getClass() == DiscOpenPattern.class)
			for (BoardLocation loc : boardLocs) {
				BoardLocation first = new BoardLocation(loc.getYPos()
						+ firstInc, loc.getXPos() + secondInc);
				BoardLocation second = new BoardLocation(loc.getYPos()
						- firstInc, loc.getXPos() - secondInc);
				if (Board.isReachable(first) && !board.isOccupied(first)
						&& !boardLocs.contains(first)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(first);
					Pattern newPat = new DiscOpenPattern(newLocs,
							pat.getType(), Pattern.findBubbleIndex(newLocs,
									pat.getType()), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return first;
				}
				if (Board.isReachable(second) && !board.isOccupied(second)
						&& !boardLocs.contains(second)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(second);
					Pattern newPat = new DiscOpenPattern(newLocs,
							pat.getType(), Pattern.findBubbleIndex(newLocs,
									pat.getType()), pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return second;
				}
			}
		else
			for (BoardLocation loc : boardLocs) {
				BoardLocation first = new BoardLocation(loc.getYPos()
						+ firstInc, loc.getXPos() + secondInc);
				BoardLocation second = new BoardLocation(loc.getYPos()
						- firstInc, loc.getXPos() - secondInc);
				if (Board.isReachable(first) && !board.isOccupied(first)
						&& !boardLocs.contains(first)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(first);
					Pattern newPat = new DiscClosedPattern(newLocs,
							pat.getType(), board.getBlockedStones(newLocs,
									pat.getType()), Pattern.findBubbleIndex(
									newLocs, pat.getType()),
							pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return first;
				}
				if (Board.isReachable(second) && !board.isOccupied(second)
						&& !boardLocs.contains(second)) {
					ArrayList<BoardLocation> newLocs = pat.getLocations();
					newLocs.add(second);
					Pattern newPat = new DiscClosedPattern(newLocs,
							pat.getType(), board.getBlockedStones(newLocs,
									pat.getType()), Pattern.findBubbleIndex(
									newLocs, pat.getType()),
							pat.getBlockingLocs());
					if (board.isPatternWinning(newPat))
						return second;
				}
			}
		return null;
	}

	private BoardLocation findWinningLoc(Pattern pat) {
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
					vBoard.updateBoard(firstCandidate, isFirst);
					if (vBoard.checkcol() || vBoard.checkrow()
							|| vBoard.checkdiag())
						return firstCandidate;
				} catch (InvalidIndexException e) {
					continue;
				}
				try {
					vBoard.withdrawMove(firstCandidate);
				} catch (InvalidIndexException e) {
					continue;
				}
			}

			if (Board.isReachable(secondCandidate)
					&& !board.isOccupied(secondCandidate)
					&& !locations.contains(secondCandidate)) {
				try {
					vBoard.updateBoard(secondCandidate, isFirst);
					if (vBoard.checkcol() || vBoard.checkrow()
							|| vBoard.checkdiag())
						return secondCandidate;
				} catch (InvalidIndexException e) {
					continue;
				}
				try {
					vBoard.withdrawMove(secondCandidate);
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
			ArrayList<BoardLocation> result = board.filterOccupied(Board
					.findAdjacentLocs(firstMove));
			int randSeed = getRandNum(result.size());
			return result.get(randSeed - 1);
		}
	}

	public BoardLocation makeMoveEnd() {
		ArrayList<Pattern> selfPatterns = BoardChecker.checkAllPatterns(
				board, isFirst);
		ArrayList<Pattern> excellents = filterUrgentPats(selfPatterns);
		if (excellents.size() != 0)
			return findWinningLoc(excellents.get(0));
		for (Pattern pat : selfPatterns) {
			if (board.isPatternWinning(pat))
				return findWinningLoc(pat);
		}
		ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board,
				!isFirst);
		ArrayList<Pattern> urgents = filterUrgentPats(patterns);
		if (urgents.size() != 0) {
			ArrayList<BoardLocation> tofilter = extractBlockingLocs(patterns);
			ArrayList<BoardLocation> result = filterBlockingLocsAtk(tofilter);
			if (result.size() != 0) {
				return result.get(getRandNum(result.size()) - 1);
			} else
				return tofilter.get(0);
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
				BoardLocation blockAttack = result.get(getRandNum(result
						.size() - 1));
				// System.out
				// .format("The value I gave (for blocking attack) is (%d, %d).\n",
				// blockAttack.getXPos(),
				// blockAttack.getYPos());
				return blockAttack;
			}
			// System.out
			// .format("Special case: The value I gave (for blocking attack) is (%d, %d).\n",
			// tofilter.get(0).getXPos(), tofilter.get(0)
			// .getYPos());
			return tofilter.get(0);
		}
		ArrayList<BoardLocation> locations = calculateAttack();
		return processLocs(locations);
	}

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

	public Board getBoard() {
		return board;
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
		int prevSize = BoardChecker.checkAllPatterns(board, isFirst).size();
		vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(board));
		for (BoardLocation blockingloc : blockingLocs) {
			if (retVal.contains(blockingloc))
				continue;
			try {
				vBoard.updateBoard(blockingloc, isFirst);
			} catch (InvalidIndexException e) {
				continue;
			}
			ArrayList<Pattern> pats = BoardChecker.checkAllPatterns(vBoard,
					isFirst);
			if (pats.size() > prevSize) {
				retVal.add(blockingloc);
				continue;
			}
			for (Pattern patt : pats) {
				if (board.isPatternWinning(patt)) {
					retVal.clear();
					retVal.add(blockingloc);
				}
			}
			try {
				vBoard.withdrawMove(blockingloc);
			} catch (InvalidIndexException e) {
				continue;
			}
		}
		return retVal;
	}

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

	public static ArrayList<Pattern> filterUrgentPats(
			ArrayList<Pattern> patterns) {
		ArrayList<Pattern> retVal = new ArrayList<Pattern>();
		for (Pattern pat : patterns) {
			if (pat.getLocations().size() == 4)
				retVal.add(pat);
		}
		return retVal;
	}
}
