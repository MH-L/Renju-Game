package Algorithm;

import java.util.ArrayList;
import java.util.Collections;

import Exceptions.InvalidPatternException;
import Model.Board;
import Model.BoardLocation;

/**
 * Checks works against player, not AI.
 *
 * @author Minghao Liu
 *
 */
public class BoardChecker {
	private static final int TYPE_ROW = 1;
	private static final int TYPE_COL = 2;
	private static final int TYPE_ULDIAG = 1;
	private static final int TYPE_URDIAG = 2;

	/**
	 * Checks for contiguous open patterns. By pattern, it means there are no
	 * blocking stones on either end.
	 *
	 * @param board
	 *            The current game board
	 * @param first
	 *            Indicates whether it is first player or the second.
	 * @param numLocs
	 *            Indicates the number of stones the pattern consists of.
	 * @return An arrayList of all open patterns on the board, consisting the
	 *         specified number of stones.
	 */
	public static ArrayList<Pattern> checkBoardOpenPatCont(Board board,
			boolean first, int numLocs) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();
		for (int i = 0; i < rows.size(); i++) {
			patterns.addAll(checkLineOpenPatCont(rows.get(i), i, TYPE_ROW,
					first, numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkLineOpenPatCont(columns.get(i), i, TYPE_COL,
					first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkDiagOpenPatCont(ulDiags.get(i), i,
					TYPE_ULDIAG, first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkDiagOpenPatCont(urDiags.get(i), i,
					TYPE_URDIAG, first, numLocs, board));
		}
		return patterns;
	}

	public static ArrayList<Pattern> checkBoardOpenPatDisc(Board board,
			boolean first, int numLocs) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();

		for (int i = 0; i < rows.size(); i++) {
			patterns.addAll(checkOpenPatDisc(rows.get(i), i, TYPE_ROW, first,
					numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkOpenPatDisc(columns.get(i), i, TYPE_COL,
					first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkOpenPatDisc(ulDiags.get(i), i, TYPE_ULDIAG,
					first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkOpenPatDisc(urDiags.get(i), i, TYPE_URDIAG,
					first, numLocs, board));
		}
		return patterns;
	}

	public static ArrayList<Pattern> checkOpenPatDisc(int[] array,
			int arrayIndex, int type, boolean first, int numLocs, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if (array.length <= numLocs)
			return patterns;
		int prev = Board.EMPTY_SPOT;
		int checker;
		if (first)
			checker = Board.FIRST_PLAYER;
		else
			checker = Board.SECOND_PLAYER;
		for (int i = 0; i < array.length - numLocs; i++) {
			for (int j = 0; j <= numLocs; j++)
				temp.add(array[j]);
			if (prev != Board.EMPTY_SPOT)
				continue;
			int playerFreq = Collections.frequency(temp, checker);
			int emptyFreq = Collections.frequency(temp, Board.EMPTY_SPOT);
			if (playerFreq == numLocs && emptyFreq == 1) {
				if (temp.get(0) != Board.EMPTY_SPOT
						&& temp.get(numLocs) != Board.EMPTY_SPOT) {
					if (i == array.length - numLocs - 1) {
						int bubbleIndex = temp.indexOf(Board.EMPTY_SPOT);
						BoardLocation firstStone;
						switch (type) {
						case Pattern.ON_ROW:
							firstStone = new BoardLocation(arrayIndex, i);
							break;
						case Pattern.ON_COL:
							firstStone = new BoardLocation(i, arrayIndex);
							break;
						case Pattern.ON_ULDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i,
									true);
							break;
						case Pattern.ON_URDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i,
									false);
							break;
						default:
							continue;
						}
						Pattern candidate = makeDiscPattern(firstStone, type,
								bubbleIndex, numLocs, false, board);
						patterns.add(candidate);
					}
				}
			}
		}

		return patterns;
	}

	private static Pattern makeDiscPattern(BoardLocation firstStone, int type,
			int bubbleIndex, int num, boolean isClosed, Board board) {
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		int startXCoord = firstStone.getXPos();
		int startYCoord = firstStone.getYPos();
		switch (type) {
		case Pattern.ON_ROW:
			for (int i = 0; i <= num; i++) {
				if (i != bubbleIndex) {
					locations.add(new BoardLocation(startYCoord, startXCoord
							+ i));
				}
			}
			break;
		case Pattern.ON_COL:
			for (int i = 0; i <= num; i++) {
				if (i != bubbleIndex) {
					locations.add(new BoardLocation(startYCoord + i,
							startXCoord));
				}
			}
			break;
		case Pattern.ON_ULDIAG:
			for (int i = 0; i <= num; i++) {
				if (i != bubbleIndex) {
					locations.add(new BoardLocation(startYCoord + i,
							startXCoord - i));
				}
			}
			break;
		case Pattern.ON_URDIAG:
			for (int i = 0; i <= num; i++) {
				if (i != bubbleIndex) {
					locations.add(new BoardLocation(startYCoord + i,
							startXCoord + i));
				}
			}
			break;
		default:
			return null;
		}
		if (isClosed) {
			ArrayList<BoardLocation> stones = board.getBlockedStones(locations,
					type);
			return new DiscClosedPattern(locations, type, stones);
		} else
			return new DiscOpenPattern(locations, type);

	}

	/**
	 * The helper for checking open three. Note that it only checks for rows and
	 * columns, not diagonals.
	 *
	 * @param array
	 *            the row/col to check
	 * @param arrayIndex
	 *            the index of the row/col on board
	 * @param isRow
	 *            indicates whether the candidate is a row
	 * @return arraylist of patterns found on that line/column
	 */
	public static ArrayList<Pattern> checkLineOpenPatCont(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		int checker;
		if (first)
			checker = Board.FIRST_PLAYER;
		else
			checker = Board.SECOND_PLAYER;
		if (array.length < 2)
			return patterns;
		else {
			int prev = Board.EMPTY_SPOT;
			int count = 0;
			for (int i = 0; i < array.length; i++) {
				if (count == 0) {
					if (prev == Board.EMPTY_SPOT && array[i] == checker) {
						count++;
					}
				} else if (array[i] == checker) {
					count++;
				}

				if (count == num) {
					if (i == array.length - 1
							|| array[i + 1] == Board.EMPTY_SPOT) {
						if (type == TYPE_ROW) {
							BoardLocation firstStone = new BoardLocation(
									arrayIndex, i - 2);
							Pattern candidate = makeContiguousPattern(
									firstStone, false, TYPE_ROW, num, false,
									board);
							patterns.add(candidate);
						} else {
							BoardLocation firstStone = new BoardLocation(i - 2,
									arrayIndex);
							Pattern candidate = makeContiguousPattern(
									firstStone, false, TYPE_COL, num, false,
									board);
							patterns.add(candidate);
						}
					} else {
						// Don't add that pattern to the output.
					}
				}
				prev = array[i];
			}
		}
		return patterns;
	}

	public static ArrayList<Pattern> checkDiagOpenPatCont(int[] diag,
			int diagIndex, int type, boolean first, int num, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (diag.length < 2)
			return patterns;
		int checker;
		if (first)
			checker = Board.FIRST_PLAYER;
		else
			checker = Board.SECOND_PLAYER;
		int count = 0;
		int prev = Board.EMPTY_SPOT;
		for (int i = 0; i < diag.length; i++) {
			if (count == 0) {
				if (prev == Board.EMPTY_SPOT && diag[i] == checker) {
					count++;
				}
			} else if (diag[i] == checker) {
				count++;
			}

			if (count == num) {
				if (i == diag.length - 1 || diag[i + 1] == Board.EMPTY_SPOT) {
					if (type == TYPE_ULDIAG) {
						BoardLocation firstStone = Board.convertDiagToXY(
								diagIndex, i - 2, true);
						Pattern candidate = makeContiguousPattern(firstStone,
								true, TYPE_ULDIAG, num, false, board);
						patterns.add(candidate);
					} else {
						BoardLocation firstStone = Board.convertDiagToXY(
								diagIndex, i - 2, true);
						Pattern candidate = makeContiguousPattern(firstStone,
								true, TYPE_URDIAG, num, false, board);
						patterns.add(candidate);
					}
				} else {
					// Don't add that pattern.
				}
			}
		}
		return patterns;
	}

	public static Pattern makeContiguousPattern(BoardLocation firstStone,
			boolean isDiag, int type, int num, boolean isClosed, Board board) {
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		if (num != 3 && num != 4)
			try {
				throw new InvalidPatternException(
						"The number of stones is invalid.");
			} catch (InvalidPatternException e) {
				e.printStackTrace();
			}
		if (isDiag)
			if (type == TYPE_ULDIAG) {
				for (int i = 0; i < num; i++) {
					locations.add(new BoardLocation(firstStone.getYPos() + i,
							firstStone.getXPos() + i));
				}
			} else {
				for (int i = 0; i < num; i++) {
					locations.add(new BoardLocation(firstStone.getYPos() + i,
							firstStone.getXPos() - i));
				}
			}
		else if (type == TYPE_ROW) {
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos(),
						firstStone.getXPos() + i));
			}
		} else {
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos() + i,
						firstStone.getXPos()));
			}
		}
		Pattern pat;
		if (isClosed) {
			ArrayList<BoardLocation> blockedStones = board.getBlockedStones(
					locations, type);
			pat = new ContClosedPattern(locations, type, blockedStones);
		} else
			pat = new ContOpenPattern(locations, type);
		return pat;

	}

}
