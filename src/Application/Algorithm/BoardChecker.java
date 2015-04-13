package Algorithm;

import java.util.ArrayList;

import Exceptions.InvalidIndexException;
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
			patterns.addAll(checkLineOpenPatCont(rows.get(i), i, TYPE_ROW, first,
					numLocs));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkLineOpenPatCont(columns.get(i), i, TYPE_COL,
					first, numLocs));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkDiagOpenPatCont(ulDiags.get(i), i, TYPE_ULDIAG,
					first, numLocs));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkDiagOpenPatCont(urDiags.get(i), i, TYPE_URDIAG,
					first, numLocs));
		}
		return null;
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
			int arrayIndex, int type, boolean first, int num) {
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
					if (i == array.length - 1 || array[i + 1] == Board.EMPTY_SPOT) {
						// TODO add that pattern to the output
						// since this is already the last stone
						// on that row/column
						if (type == TYPE_ROW) {
							BoardLocation firstStone = new BoardLocation(
									arrayIndex, i - 2);
							Pattern candidate = makeContiguousPattern(firstStone, false, TYPE_ROW, num);
							patterns.add(candidate);
						} else {
							// this is a column. y first then x. meaning that
							// the y coordinates are varying.
							BoardLocation firstStone = new BoardLocation(i - 2,
									arrayIndex);
							Pattern candidate = makeContiguousPattern(firstStone, false, TYPE_COL, num);
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
			int diagIndex, int type, boolean first, int num) {
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
						BoardLocation firstStone = Board.convertDiagToXY(diagIndex, i - 2, true);
						Pattern candidate = makeContiguousPattern(firstStone, true, TYPE_ULDIAG, num);
						patterns.add(candidate);
					} else {
						BoardLocation firstStone = Board.convertDiagToXY(diagIndex, i - 2, true);
						Pattern candidate = makeContiguousPattern(firstStone, true, TYPE_URDIAG, num);
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
			boolean isDiag, int type, int num) {
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
		Pattern pat = new Pattern(locations, true, type);
		return pat;

	}

}
