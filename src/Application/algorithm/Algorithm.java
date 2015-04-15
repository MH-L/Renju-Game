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
		int maxIndex = 0;
		for (int i = 0; i < candidates.size(); i++) {
			if (Board.findTotalDistToSides(candidates.get(maxIndex)) < Board
					.findTotalDistToSides(candidates.get(i)))
				maxIndex = i;
		}

		return candidates.get(maxIndex);

	}

	public BoardLocation makeMoveBeginning() {
		if (board.getTotalStones() == 1)
			return makeFirstMoveSecond();
		else if (board.getTotalStones() == 0)
			return makeFirstMoveFirst();
		else if (board.getTotalStones() == 2)
			return makeSecondMoveFirst();
		else {
			ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board,
					true);
			if (patterns.size() != 0) {
				return patterns.get(0).getBlockingLocs().get(0);
			}
		}
		return board.findEmptyLocSpiral();
	}

	private BoardLocation makeSecondMoveFirst() {
		// TODO Auto-generated method stub
		return board.findEmptyLocSpiral();
	}

	public BoardLocation makeMoveEnd() {
		// TODO Auto-generated method stub
		ArrayList<Pattern> patterns = BoardChecker.checkAllPatterns(board, true);
		if (patterns.size() != 0) {
			return patterns.get(0).getBlockingLocs().get(0);
		}
		return board.findEmptyLocSpiral();
	}
}
