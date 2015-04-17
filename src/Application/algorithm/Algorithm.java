package algorithm;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.BoardLocation;

public abstract class Algorithm {
	private static Board board;
	private static final Random rand = new Random();

	public Algorithm(Board board) {
		this.board = board;
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
		return null;
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
			ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board,
					true);
			if (patterns.size() != 0) {
				return patterns.get(0).getBlockingLocs().get(0);
			}
		}
		ArrayList<BoardLocation> result = board.filterOccupied(Board
				.findAdjacentLocs(board.getPlayer2Stone().get(
						board.getPlayer2Stone().size() - 1)));
		if (result.size() == 0)
			return board.findEmptyLocSpiral();
		else {
			int randSeed = getRandNum(result.size()) - 1;
			return result.get(randSeed);
		}
	}

	public BoardLocation makeSecondMoveFirst() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		ArrayList<Pattern> patterns = BoardChecker
				.checkAllPatterns(board, true);
		if (patterns.size() != 0) {
			return patterns.get(0).getBlockingLocs().get(0);
		}
		return board.findEmptyLocSpiral();
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
}
