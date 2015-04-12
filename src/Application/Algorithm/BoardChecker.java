package Algorithm;

import java.util.ArrayList;

import Model.Board;
import Model.BoardLocation;
/**
 * Checks works against player, not AI.
 * @author Minghao Liu
 *
 */
public class BoardChecker {

	public static ArrayList<Pattern> checkBoardOpenThree(Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();
		for (int i = 0; i < rows.size(); i++) {
			patterns.addAll(checkLineOpenThree(rows.get(i), i, true));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkLineOpenThree(columns.get(i), i, false));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkDiagOpenThree(ulDiags.get(i), i, true));
		}
		return null;
	}

	/**
	 * The helper for checking open three. Note that it only checks
	 * for rows and columns, not diagonals.
	 * @param array the row/col to check
	 * @param arrayIndex the index of the row/col on board
	 * @param isRow indicates whether the candidate is a row
	 * @return arraylist of patterns found on that line/column
	 */
	public static ArrayList<Pattern> checkLineOpenThree(int[] array, int arrayIndex, boolean isRow) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (array.length < 2)
			return patterns;
		else {
			int prev = Board.EMPTY_SPOT;
			int count = 0;
			for (int i = 0; i < array.length; i++) {
				if (count == 0) {
					if (prev == Board.EMPTY_SPOT && array[i] == Board.FIRST_PLAYER) {
						count ++;
					}
				} else if (array[i] == Board.FIRST_PLAYER) {
					count ++;
				}

				if (count == 3) {
					if (i == array.length - 1) {
						// TODO add that pattern to the output
						// since this is already the last stone
						// on that row/column
						if (isRow) {
							// this is on a row. y first then x. meaning that
							// the x-coordinates are varying.
							BoardLocation firstStone = new BoardLocation(arrayIndex, i-2);
							BoardLocation secondStone = new BoardLocation(arrayIndex, i-1);
							BoardLocation thirdStone = new BoardLocation(arrayIndex, i);
							ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
							candidates.add(0,firstStone);
							candidates.add(1,secondStone);
							candidates.add(2,thirdStone);
						} else {
							// this is a column. y first then x. meaning that
							// the y coordinates are varying.
							BoardLocation firstStone = new BoardLocation(i-2, arrayIndex);
							BoardLocation secondStone = new BoardLocation(i-1, arrayIndex);
							BoardLocation thirdStone = new BoardLocation(i, arrayIndex);
							ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
							candidates.add(0, firstStone);
							candidates.add(1, secondStone);
							candidates.add(2, thirdStone);
						}
					} else if (array[i + 1] == Board.EMPTY_SPOT){
						// TODO add that pattern to the output
						if (isRow) {
							// this is on a row. y first then x. meaning that
							// the x-coordinates are varying.
							BoardLocation firstStone = new BoardLocation(arrayIndex, i-2);
							BoardLocation secondStone = new BoardLocation(arrayIndex, i-1);
							BoardLocation thirdStone = new BoardLocation(arrayIndex, i);
							ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
							candidates.add(0,firstStone);
							candidates.add(1,secondStone);
							candidates.add(2,thirdStone);
						} else {
							// this is a column. y first then x. meaning that
							// the y coordinates are varying.
							BoardLocation firstStone = new BoardLocation(i-2, arrayIndex);
							BoardLocation secondStone = new BoardLocation(i-1, arrayIndex);
							BoardLocation thirdStone = new BoardLocation(i, arrayIndex);
							ArrayList<BoardLocation> candidates = new ArrayList<BoardLocation>();
							candidates.add(0, firstStone);
							candidates.add(1, secondStone);
							candidates.add(2, thirdStone);
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

	public static ArrayList<Pattern> checkDiagOpenThree(int[] diag, int diagIndex, boolean isUL) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (diag.length < 2)
			return patterns;
		int count = 0;
		int prev = Board.EMPTY_SPOT;
		for (int i = 0; i < diag.length; i++) {
			if (count == 0) {
				if (prev == Board.EMPTY_SPOT && diag[i] == Board.FIRST_PLAYER) {
					count ++;
				}
			} else if (diag[i] == Board.FIRST_PLAYER) {
				count ++;
			}

			if (count == 3) {
				if (i == diag.length - 1) {
					// TODO add
					if (isUL) {

					} else {

					}
				} else if (diag[i+1] == Board.EMPTY_SPOT) {
					// TODO add
					if (isUL) {

					} else {

					}
				}
			}
		}
		return null;
	}

	public static ArrayList<Pattern> checkFour(int[] array) {
		return null;
	}

}
