package Algorithm;

import java.util.ArrayList;
import java.util.Collections;

import Exceptions.InvalidPatternException;
import Model.Board;
import Model.BoardLocation;

/**
 * Checks works against players, including AI. Check for AI because this helps
 * making attacks.
 *
 * @author Minghao Liu
 *
 */
public class BoardChecker {

	/**
	 * Checks for all patterns on board for a given player.
	 *
	 * @param board
	 *            The board where patterns are on.
	 * @param first
	 *            Specifies if it is the first player.
	 * @return An ArrayList of all patterns on board.
	 */
	public static ArrayList<Pattern> checkAllPattern(Board board, boolean first) {
		ArrayList<Pattern> retVal = new ArrayList<Pattern>();
		retVal.addAll(checkBoardOpenPatCont(board, first, 3));
		retVal.addAll(checkBoardOpenPatCont(board, first, 4));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 3));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 4));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 5));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 6));
		return retVal;
	}

	public static ArrayList<Pattern> checkBoardClosedPat(Board board,
			boolean first, int numLocs) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();
		return patterns;
	}

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
			patterns.addAll(checkOpenPatCont(rows.get(i), i, Pattern.ON_ROW,
					first, numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkOpenPatCont(columns.get(i), i, Pattern.ON_COL,
					first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkOpenPatCont(ulDiags.get(i), i,
					Pattern.ON_ULDIAG, first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkOpenPatCont(urDiags.get(i), i,
					Pattern.ON_URDIAG, first, numLocs, board));
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
			patterns.addAll(checkOpenPatDisc(rows.get(i), i, Pattern.ON_ROW,
					first, numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkOpenPatDisc(columns.get(i), i, Pattern.ON_COL,
					first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkOpenPatDisc(ulDiags.get(i), i,
					Pattern.ON_ULDIAG, first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkOpenPatDisc(urDiags.get(i), i,
					Pattern.ON_URDIAG, first, numLocs, board));
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
				temp.add(array[i + j]);
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
					} else if (array[i + numLocs + 1] == Board.EMPTY_SPOT) {
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
			temp.clear();
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

	public static ArrayList<Pattern> checkClosedPatCont(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		int prev = Board.EMPTY_SPOT;
		int count = 0;
		int checker;
		int blocker;
		if (first) {
			checker = Board.FIRST_PLAYER;
			blocker = Board.SECOND_PLAYER;
		} else {
			checker = Board.SECOND_PLAYER;
			blocker = Board.FIRST_PLAYER;
		}
		boolean blocked = false;
		for (int i = 0; i < array.length; i++) {
			int cur = array[i];
			if (cur == blocker)
				blocked = true;
			else if (cur == Board.EMPTY_SPOT)
				blocked = false;
			if (cur == prev && cur == checker)
				count++;
			else if (cur != prev && cur == checker)
				count = 1;
			else
				count = 0;
			if (count == num) {
				BoardLocation firstStone;
				switch (type) {
				case Pattern.ON_ROW:
					firstStone = new BoardLocation(arrayIndex, i - num + 1);
					break;
				case Pattern.ON_COL:
					firstStone = new BoardLocation(i - num + 1, arrayIndex);
					break;
				case Pattern.ON_ULDIAG:
					firstStone = Board.convertDiagToXY(arrayIndex, i - num + 1,
							true);
					break;
				case Pattern.ON_URDIAG:
					firstStone = Board.convertDiagToXY(arrayIndex, i - num + 1,
							false);
					break;
				default:
					firstStone = Board.getInvalidBoardLocation();
					break;
				}
				if (blocked) {
					if (i == array.length - 1) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					} else if (array[i + 1] == Board.EMPTY_SPOT) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					}
				} else {
					if (i != array.length - 1 && array[i + 1] == blocker) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					}
				}
				count = 0;
			}
			prev = cur;
		}
		return patterns;
	}

	public static ArrayList<Pattern> checkClosedPatDisc(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		if (num < 4) {
			try {
				throw new InvalidPatternException(
						"The number of stones in a closed discrete pattern must be at least four!");
			} catch (InvalidPatternException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		int prev = Board.EMPTY_SPOT;
		int count = 0;
		int checker;
		int blocker;
		if (first) {
			checker = Board.FIRST_PLAYER;
			blocker = Board.SECOND_PLAYER;
		} else {
			checker = Board.SECOND_PLAYER;
			blocker = Board.FIRST_PLAYER;
		}
		boolean blocked = false;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j <= num; j++) {
				temp.add(array[i + j]);
			}
			int playerFreq = 1;
			if (count == num) {
				BoardLocation firstStone;
				switch (type) {
				case Pattern.ON_ROW:
					firstStone = new BoardLocation(arrayIndex, i - num + 1);
					break;
				case Pattern.ON_COL:
					firstStone = new BoardLocation(i - num + 1, arrayIndex);
					break;
				case Pattern.ON_ULDIAG:
					firstStone = Board.convertDiagToXY(arrayIndex, i - num + 1,
							true);
					break;
				case Pattern.ON_URDIAG:
					firstStone = Board.convertDiagToXY(arrayIndex, i - num + 1,
							false);
					break;
				default:
					firstStone = Board.getInvalidBoardLocation();
					break;
				}
				if (blocked) {
					if (i == array.length - 1) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					} else if (array[i + 1] == Board.EMPTY_SPOT) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					}
				} else {
					if (i != array.length - 1 && array[i + 1] == blocker) {
						Pattern candidate = makeContiguousPattern(firstStone,
								type, num, true, board);
						patterns.add(candidate);
					}
				}
				count = 0;
			}
		}
		return patterns;
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
	public static ArrayList<Pattern> checkOpenPatCont(int[] array,
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
					if (i == array.length - 1) {
						BoardLocation firstStone;
						Pattern candidate;
						switch (type) {
						case Pattern.ON_ROW:
							firstStone = new BoardLocation(arrayIndex, i - num
									+ 1);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ROW, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_COL:
							firstStone = new BoardLocation(i - num + 1,
									arrayIndex);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_COL, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_ULDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i
									- num + 1, true);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ULDIAG, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_URDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i
									- num + 1, true);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ULDIAG, num, false, board);
							patterns.add(candidate);
							break;
						default:
							break;
						}
					} else if (array[i + 1] == Board.EMPTY_SPOT) {
						BoardLocation firstStone;
						Pattern candidate;
						switch (type) {
						case Pattern.ON_ROW:
							firstStone = new BoardLocation(arrayIndex, i - num
									+ 1);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ROW, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_COL:
							firstStone = new BoardLocation(i - num + 1,
									arrayIndex);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_COL, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_ULDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i
									- num + 1, true);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ULDIAG, num, false, board);
							patterns.add(candidate);
							break;
						case Pattern.ON_URDIAG:
							firstStone = Board.convertDiagToXY(arrayIndex, i
									- num + 1, true);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_ULDIAG, num, false, board);
							patterns.add(candidate);
							break;
						default:
							break;
						}
					}
					count = 0;
				}
				prev = array[i];
			}
		}
		return patterns;
	}

	public static Pattern makeContiguousPattern(BoardLocation firstStone,
			int type, int num, boolean isClosed, Board board) {
		ArrayList<BoardLocation> locations = new ArrayList<BoardLocation>();
		if (num != 3 && num != 4)
			try {
				throw new InvalidPatternException(
						"The number of stones is invalid.");
			} catch (InvalidPatternException e) {
				e.printStackTrace();
			}
		switch (type) {
		case Pattern.ON_ULDIAG:
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos() + i,
						firstStone.getXPos() + i));
			}
			break;
		case Pattern.ON_URDIAG:
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos() + i,
						firstStone.getXPos() - i));
			}
			break;
		case Pattern.ON_ROW:
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos(),
						firstStone.getXPos() + i));
			}
			break;
		case Pattern.ON_COL:
			for (int i = 0; i < num; i++) {
				locations.add(new BoardLocation(firstStone.getYPos() + i,
						firstStone.getXPos()));
			}
			break;
		default:
			break;
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
