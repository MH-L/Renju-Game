package algorithm;

import java.util.ArrayList;
import java.util.Collections;

import model.Board;
import model.BoardLocation;
import exceptions.InvalidPatternException;

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
	 * @return An ArrayList of all patterns found on board.
	 */
	public static ArrayList<Pattern> checkAllPatterns(Board board, boolean first) {
		ArrayList<Pattern> retVal = new ArrayList<Pattern>();
		retVal.addAll(checkBoardOpenPatCont(board, first, 3));
		retVal.addAll(checkBoardOpenPatCont(board, first, 4));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 3));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 4));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 5));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 6));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 7));
		retVal.addAll(checkBoardOpenPatDisc(board, first, 8));
		retVal.addAll(checkBoardClosedPatCont(board, first, 4));
		retVal.addAll(checkBoardClosedPatDisc(board, first, 4));
		retVal.addAll(checkBoardClosedPatDisc(board, first, 5));
		retVal.addAll(checkBoardClosedPatDisc(board, first, 6));
		retVal.addAll(checkBoardClosedPatDisc(board, first, 7));
		retVal.addAll(checkBoardClosedPatDisc(board, first, 8));
		return retVal;
	}

	/**
	 * Checks for all discrete closed patterns on board.
	 *
	 * @param board
	 *            the board where the patterns are on
	 * @param first
	 *            whether the patterns belong to the first player
	 * @param numLocs
	 *            the number of stones forming the pattern
	 * @return an ArrayList of all patterns found on board.
	 */
	public static ArrayList<Pattern> checkBoardClosedPatDisc(Board board,
			boolean first, int numLocs) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();
		for (int i = 0; i < rows.size(); i++) {
			patterns.addAll(checkClosedPatDisc(rows.get(i), i, Pattern.ON_ROW,
					first, numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkClosedPatDisc(columns.get(i), i,
					Pattern.ON_COL, first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkClosedPatDisc(ulDiags.get(i), i,
					Pattern.ON_ULDIAG, first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkClosedPatDisc(urDiags.get(i), i,
					Pattern.ON_URDIAG, first, numLocs, board));
		}
		return patterns;
	}

	/**
	 * Checks for all contiguous closed patterns on board.
	 *
	 * @param board
	 *            the board where the patterns are on.
	 * @param first
	 *            whether the patterns belong to the first player
	 * @param numLocs
	 *            the number of stones in the pattern
	 * @return an ArrayList of all closed contiguous patterns found on board.
	 */
	public static ArrayList<Pattern> checkBoardClosedPatCont(Board board,
			boolean first, int numLocs) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<int[]> rows = board.getRows();
		ArrayList<int[]> columns = board.getColumns();
		ArrayList<int[]> ulDiags = board.getULDiags();
		ArrayList<int[]> urDiags = board.getURDiags();
		for (int i = 0; i < rows.size(); i++) {
			patterns.addAll(checkClosedPatCont(rows.get(i), i, Pattern.ON_ROW,
					first, numLocs, board));
		}

		for (int i = 0; i < columns.size(); i++) {
			patterns.addAll(checkClosedPatCont(columns.get(i), i,
					Pattern.ON_COL, first, numLocs, board));
		}

		for (int i = 0; i < ulDiags.size(); i++) {
			patterns.addAll(checkClosedPatCont(ulDiags.get(i), i,
					Pattern.ON_ULDIAG, first, numLocs, board));
		}

		for (int i = 0; i < urDiags.size(); i++) {
			patterns.addAll(checkClosedPatCont(urDiags.get(i), i,
					Pattern.ON_URDIAG, first, numLocs, board));
		}
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
		int checker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;
		for (int i = 0; i < array.length - numLocs; i++) {
			for (int j = 0; j <= numLocs; j++)
				temp.add(array[i + j]);
			if (prev != Board.EMPTY_SPOT) {
				prev = array[i];
				continue;
			}
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
						for (int j = 0; j < temp.size(); j++) {
							if (temp.get(j) == Board.EMPTY_SPOT)
								bubbleIndex = j;
						}
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
			prev = array[i];
			temp.clear();
		}

		return patterns;
	}

	/**
	 * Makes a discrete pattern with the given first stone and the type.
	 *
	 * @param firstStone
	 *            The first BoardLocation of the pattern
	 * @param type
	 *            Indicates whether the pattern is on row/ on column/ on
	 *            upper-left diagonal/ on upper-right diagonal
	 * @param bubbleIndex
	 *            The place where there is an empty spot. E.g. if the empty spot
	 *            is after the first stone then bubbleIndex is 1.
	 * @param num
	 *            Number of stones forming the pattern
	 * @param isClosed
	 *            Whether or not the pattern is closed. (i.e. there is a stone
	 *            of the other party blocking the pattern)
	 * @param board
	 *            The board where the patterns are on.
	 * @return A discrete pattern with the given stone as its first board
	 *         location and the given type as its type.
	 */
	public static Pattern makeDiscPattern(BoardLocation firstStone, int type,
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
							startXCoord + i));
				}
			}
			break;
		case Pattern.ON_URDIAG:
			for (int i = 0; i <= num; i++) {
				if (i != bubbleIndex) {
					locations.add(new BoardLocation(startYCoord + i,
							startXCoord - i));
				}
			}
			break;
		default:
			return null;
		}
		if (isClosed) {
			ArrayList<BoardLocation> stones = board.getBlockedStones(locations,
					type);
			return new DiscClosedPattern(locations, type, stones, bubbleIndex,
					board.findBlockingLocs(locations, type));
		} else
			return new DiscOpenPattern(locations, type, bubbleIndex,
					board.findBlockingLocs(locations, type));

	}

	/**
	 * Checks all closed contiguous patterns in a given array.
	 *
	 * @param array
	 *            the array which the patterns are on
	 * @param arrayIndex
	 *            the index of the array on board
	 * @param type
	 *            the type of the array (row/col/ul-diag/ur-diag)
	 * @param first
	 *            whether this is the first player's pattern
	 * @param num
	 *            number of stones the pattern has
	 * @param board
	 *            the board where the patterns are on
	 * @return
	 */
	public static ArrayList<Pattern> checkClosedPatCont(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (array.length < num + 1)
			return patterns;
		int prev = Board.EMPTY_SPOT;
		int count = 0;
		int checker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;
		int blocker = first ? Board.SECOND_PLAYER : Board.FIRST_PLAYER;
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

	/**
	 * The method checks discrete closed patterns.
	 *
	 * @param array
	 *            the array where the patterns are on
	 * @param arrayIndex
	 *            the index of the given array on board
	 * @param type
	 *            there are four types: on row/column/ul-diag/ur-diag
	 * @param first
	 *            whether the patterns belong to the first player
	 * @param num
	 *            number of stones the pattern consists of
	 * @param board
	 *            the board where the patterns are on
	 * @return all discrete closed patterns in the given array
	 */
	public static ArrayList<Pattern> checkClosedPatDisc(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		if (num < 4) {
			try {
				throw new InvalidPatternException(
						"The number of stones in a closed discrete pattern must be at least four!");
			} catch (InvalidPatternException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (array.length < num + 2)
			return patterns;
		int checker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;
		int blocker = first ? Board.SECOND_PLAYER : Board.FIRST_PLAYER;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < array.length - num - 1; i++) {
			for (int j = 0; j <= num + 1; j++) {
				temp.add(array[i + j]);
			}
			int checkerFreq = Collections.frequency(temp, checker);
			int blockerFreq = Collections.frequency(temp, blocker);
			int emptyFreq = Collections.frequency(temp, Board.EMPTY_SPOT);
			if (emptyFreq == 1 && blockerFreq == 1 && checkerFreq == num) {
				if (temp.get(0) == blocker || temp.get(num + 1) == blocker) {
					int diff = temp.indexOf(blocker)
							- temp.indexOf(Board.EMPTY_SPOT);
					if (Math.abs(diff) != 1 && temp.get(0) != Board.EMPTY_SPOT
							&& temp.get(num + 1) != Board.EMPTY_SPOT) {
						BoardLocation firstStone;
						// add that pattern to the outcome
						if (temp.get(0) == blocker) {
							switch (type) {
							case Pattern.ON_ROW:
								firstStone = new BoardLocation(arrayIndex,
										i + 1);
								break;
							case Pattern.ON_COL:
								firstStone = new BoardLocation(i + 1,
										arrayIndex);
								break;
							case Pattern.ON_ULDIAG:
								firstStone = Board.convertDiagToXY(arrayIndex,
										i + 1, true);
								break;
							case Pattern.ON_URDIAG:
								firstStone = Board.convertDiagToXY(arrayIndex,
										i + 1, false);
								break;
							default:
								firstStone = Board.getInvalidBoardLocation();
								break;
							}
							patterns.add(makeDiscPattern(firstStone, type,
									temp.indexOf(Board.EMPTY_SPOT) - 1, num, true,
									board));
						} else {
							switch (type) {
							case Pattern.ON_ROW:
								firstStone = new BoardLocation(arrayIndex, i);
								break;
							case Pattern.ON_COL:
								firstStone = new BoardLocation(i, arrayIndex);
								break;
							case Pattern.ON_ULDIAG:
								firstStone = Board.convertDiagToXY(arrayIndex,
										i, true);
								break;
							case Pattern.ON_URDIAG:
								firstStone = Board.convertDiagToXY(arrayIndex,
										i, false);
								break;
							default:
								firstStone = Board.getInvalidBoardLocation();
								break;
							}
							patterns.add(makeDiscPattern(firstStone, type,
									temp.indexOf(Board.EMPTY_SPOT), num, true,
									board));
						}
					}
				}
			}
			temp.clear();
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
	 * @param first
	 *            indicates whether this is the first player's stone
	 * @param num
	 *            the number of stones in the pattern
	 * @param board
	 *            the board where the patterns are on
	 * @return arraylist of patterns found on that line/column
	 */
	public static ArrayList<Pattern> checkOpenPatCont(int[] array,
			int arrayIndex, int type, boolean first, int num, Board board) {
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		if (array.length < num)
			return patterns;
		int checker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;
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
				} else {
					count = 0;
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
									- num + 1, false);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_URDIAG, num, false, board);
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
									- num + 1, false);
							candidate = makeContiguousPattern(firstStone,
									Pattern.ON_URDIAG, num, false, board);
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

	/**
	 *
	 * @param firstStone
	 *            the first stone of the pattern's constituent. The first stone
	 *            is always the stone with the smallest y-coordinate on board.
	 *            If all the y-coordinates are the same (i.e. the pattern is on
	 *            a row), then it has the smallest x-coordinate.
	 * @param type
	 *            there are four types: on row/column/upper-right
	 *            diagonal/upper-left diagonal
	 * @param num
	 *            the number of stones in this pattern
	 * @param isClosed
	 *            indicates whether there is one stone blocking this pattern
	 * @param board
	 *            the board where the patterns are on
	 * @return The contiguous pattern with the first location and its type
	 *         given.
	 */
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
			pat = new ContClosedPattern(locations, type, blockedStones,
					board.findBlockingLocs(locations, type));
		} else
			pat = new ContOpenPattern(locations, type, board.findBlockingLocs(
					locations, type));
		return pat;

	}

}
