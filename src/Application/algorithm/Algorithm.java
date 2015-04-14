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
		return rand.nextInt(modulo);
	}

	public abstract ArrayList<BoardLocation> findLocation();
	public abstract BoardLocation findBestLocation();
	public BoardLocation makeFirstMoveFirst() {
		if (Board.getWidth() % 2 == 0) {
			ArrayList<BoardLocation> candidateLocations = new ArrayList<BoardLocation>();
			int height = Board.getHeight();
			int width = Board.getWidth();
			candidateLocations.add(new BoardLocation(height / 2, width / 2));
			candidateLocations.add(new BoardLocation(height / 2 - 1, width / 2 - 1));
			candidateLocations.add(new BoardLocation(height / 2 - 1, width / 2));
			candidateLocations.add(new BoardLocation(height / 2 - 1, width / 2 - 1));
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
			return new BoardLocation(Board.getHeight() / 2, Board.getWidth() / 2);
		}
		return null;
	}
	public BoardLocation makeFirstMoveSecond() {
		assert(!board.isEmpty());
		return null;

	}
}
