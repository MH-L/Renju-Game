package model;

import java.util.ArrayList;

import exceptions.InvalidIndexException;
import algorithm.BoardChecker;
import algorithm.ContClosedPattern;
import algorithm.ContOpenPattern;
import algorithm.DiscClosedPattern;
import algorithm.DiscOpenPattern;
import algorithm.Pattern;
import application.Game;

/**
 * A class for the board. A board is always 16*16. It contains grid locations.
 *
 * @author Minghao Liu
 * @Date 2015/4/9
 *
 */
public class Board {
	/**
	 * A height-by-width grid representing the board. Grid value is zero if the
	 * spot is empty, 1 if occupied by a stone belongs to the first player, 2 if
	 * occupied by a stone belongs to the second player.
	 */
	private int[][] basicGrid;
	/**
	 * All rows on the board. A row's index on board equals its index in the
	 * array list.
	 */
	private ArrayList<int[]> rows;
	/**
	 * All columns on the board. A column's index on board equals its index in
	 * the array list.
	 */
	private ArrayList<int[]> columns;
	/**
	 * All upper-left diagonals on the board.
	 */
	private ArrayList<int[]> diagonals_Uleft;
	/**
	 * All upper-right diagonals on the board.
	 */
	private ArrayList<int[]> diagonals_Uright;
	/**
	 * This is the width of the board. It is always 16.
	 */
	private static int width = 16;
	/**
	 * This is the height of the board. It is always 16.
	 */
	private static int height = 16;
	/**
	 * The number of diagonals on board. Note that there are the same number of
	 * upper-left diagonals and upper-right diagonals.
	 */
	private static int diag = 31;
	/**
	 * Player 1's stones on board.
	 */
	private ArrayList<BoardLocation> player1Stone;
	/**
	 * Player 2's stones on board.
	 */
	private ArrayList<BoardLocation> player2Stone;
	/**
	 * Number of stones in a row in order to win.
	 */
	public static final int NUM_STONES_TO_WIN = 5;
	/**
	 * Number of stones in a row which commits a foul. Note that this only
	 * applies to the first player.
	 */
	public static final int NUM_STONES_TO_FOUL = 6;
	/**
	 * Value of a grid if it is unoccupied.
	 */
	public static final int EMPTY_SPOT = 0;
	/**
	 * Value of a grid if it is occupied by the first player.
	 */
	public static final int FIRST_PLAYER = 1;
	/**
	 * Value of a grid if it is occupied by the second player.
	 */
	public static final int SECOND_PLAYER = 2;
	/**
	 * Display mode -- classic mode.
	 */
	public static final int CLASSIC_MODE = 1;
	/**
	 * Display mode -- fancy mode. Note that fancy mode requires UTF-8 encoding.
	 */
	public static final int FANCY_MODE = 2;

	/**
	 * A default constructor. Makes a default board of size 16.
	 */
	public Board() {
		this(16);
	}

	/**
	 * Constructs a board with the given size. The board is square in shape.
	 *
	 * @param size
	 *            The size of the board.
	 */
	public Board(int size) {
		width = size;
		height = size;
		diag = 2 * size - 1;
		this.basicGrid = initGrid();
		this.rows = initRows();
		this.columns = initCols();
		this.diagonals_Uleft = initDiags();
		this.diagonals_Uright = initDiags();
		this.player1Stone = new ArrayList<BoardLocation>();
		this.player2Stone = new ArrayList<BoardLocation>();
	}

	public ArrayList<BoardLocation> getPlayer1Stone() {
		return player1Stone;
	}

	public ArrayList<BoardLocation> getPlayer2Stone() {
		return player2Stone;
	}

	public ArrayList<int[]> getRows() {
		return this.rows;
	}

	public ArrayList<int[]> getColumns() {
		return this.columns;
	}

	public ArrayList<int[]> getULDiags() {
		return this.diagonals_Uleft;
	}

	public ArrayList<int[]> getURDiags() {
		return this.diagonals_Uright;
	}

	public int[][] getGrids() {
		return this.basicGrid;
	}

	public int[] getColumnByIndex(int index) {
		if (index < 0 || index > width)
			try {
				throw new InvalidIndexException(
						"The column index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}

		return this.getColumns().get(index);
	}

	public int[] getRowByIndex(int index) {
		if (index < 0 || index > height) {
			try {
				throw new InvalidIndexException(
						"The row index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getRows().get(index);
	}

	public int[] getULDiagByIndex(int index) {
		if (index < 0 || index > diag) {
			try {
				throw new InvalidIndexException(
						"The diagonal index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getULDiags().get(index);
	}

	public int[] getURDiagByIndex(int index) {
		if (index < 0 || index > diag) {
			try {
				throw new InvalidIndexException(
						"The diagonal index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getURDiags().get(index);
	}

	/**
	 * Initialize all grids on the board. The init grid has no stones on it.
	 * Each entry is just 0.
	 *
	 * @return The initial grid.
	 */
	private int[][] initGrid() {
		int[][] grid = new int[height][width];
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid.length; j++)
				grid[i][j] = 0;
		return grid;
	}

	/**
	 * Initialize all rows on the board. Each element is set to zero when
	 * initialized.
	 *
	 * @return
	 */
	private ArrayList<int[]> initRows() {
		ArrayList<int[]> rows = new ArrayList<int[]>();
		for (int i = 0; i < height; i++) {
			int[] arr = new int[width];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = 0;
			}
			rows.add(arr);
		}
		return rows;
	}

	/**
	 * Initializes all columns on the board.
	 *
	 * @return
	 */
	private ArrayList<int[]> initCols() {
		ArrayList<int[]> cols = new ArrayList<int[]>();
		for (int i = 0; i < width; i++) {
			int[] arr = new int[height];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = 0;
			}
			cols.add(arr);
		}
		return cols;
	}

	/**
	 * Initializes all diagonals on the board.
	 *
	 * @return An array list of all diagonals.
	 */
	private ArrayList<int[]> initDiags() {
		ArrayList<int[]> diags = new ArrayList<int[]>();
		for (int i = 0; i < diag; i++) {
			int[] arr;
			if (i < width)
				arr = new int[i + 1];
			else
				arr = new int[diag - i];
			for (int j = 0; j < arr.length; j++)
				arr[j] = 0;
			diags.add(arr);
		}
		return diags;
	}

	/**
	 * Checks the rows to see if there are five stones in a row that belongs to
	 * the same player.
	 *
	 * @return True if there is such combination, false if there is not.
	 */
	public boolean checkrow() {
		int consectCount = 0; // This counts the number of consecutive stones
								// for one player.
		int prev = EMPTY_SPOT; // This is the last stone. 0 denotes that there
								// is no last stone.
		for (int[] array : this.rows) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != EMPTY_SPOT)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_WIN)
					return true;
			}
			prev = 0;
			consectCount = 0;
		}
		return false;
	}

	/**
	 * Check columns to see if there are five stones in a row that belongs to
	 * the same player.
	 *
	 * @return True if there are, false if cannot find such combination.
	 */
	public boolean checkcol() {
		int consectCount = 0;
		int prev = EMPTY_SPOT;
		for (int[] array : this.columns) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != EMPTY_SPOT)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_WIN)
					return true;
			}
			prev = 0;
			consectCount = 0;
		}
		return false;
	}

	/**
	 * Check diagonals to see if there are five stones in a row that belongs to
	 * the same player.
	 *
	 * @return True if there are, false if cannot find such combination.
	 */
	public boolean checkdiag() {
		int consectCount = 0;
		int prev = EMPTY_SPOT;
		for (int[] array : this.diagonals_Uleft) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != EMPTY_SPOT)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_WIN)
					return true;
			}
			prev = EMPTY_SPOT;
			consectCount = 0;
		}

		consectCount = 0;
		prev = EMPTY_SPOT;

		for (int[] array : this.diagonals_Uright) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != EMPTY_SPOT)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_WIN)
					return true;
			}
			prev = EMPTY_SPOT;
			consectCount = 0;
		}

		return false;
	}

	public boolean isOccupied(BoardLocation loc) {
		int row = loc.getYPos();
		int col = loc.getXPos();
		return this.basicGrid[row][col] != 0;
	}

	/**
	 * The function updates the board given the location and the player.
	 *
	 * @param loc
	 *            Indicates the board location to place the stone
	 * @param player
	 *            True means it is player's stone, otherwise it is computer's
	 *            stone.
	 * @return false if it did not succeed. true if succeeded
	 * @throws InvalidIndexException
	 */
	public boolean updateBoard(BoardLocation loc, boolean first)
			throws InvalidIndexException {
		if (!isReachable(loc))
			throw new InvalidIndexException(
					"The location indexes is out of bound!");
		int col_num = loc.getXPos();
		int row_num = loc.getYPos();
		if (this.isOccupied(loc))
			return false;
		int marker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;

		this.basicGrid[row_num][col_num] = marker;
		this.getColumns().get(col_num)[row_num] = marker;
		this.getRows().get(row_num)[col_num] = marker;
		int indexURDiag = getURDiagIndex(loc);
		int indexULDiag = getULDiagIndex(loc);
		if (indexURDiag >= width)
			this.getURDiags().get(indexURDiag)[width - 1 - col_num] = marker;
		else
			this.getURDiags().get(indexURDiag)[row_num] = marker;
		if (indexULDiag >= width)
			this.getULDiags().get(indexULDiag)[col_num] = marker;
		else
			this.getULDiags().get(indexULDiag)[row_num] = marker;
		if (first)
			this.player1Stone.add(loc);
		else
			this.player2Stone.add(loc);
		return true;

	}

	/**
	 * Check if the board is full of stones.
	 *
	 * @return True if the board is full and false otherwise.
	 */
	public boolean boardFull() {
		for (int i = 0; i < this.basicGrid.length; i++)
			for (int j = 0; j < this.basicGrid[0].length; j++)
				if (this.basicGrid[i][j] == 0)
					return false;
		return true;
	}

	/**
	 * Render the board on console.
	 *
	 * @param mode
	 *            Classic mode -- display empty spot as "-", first player's
	 *            stone as "X" and second player's stone as "O". Fancy mode --
	 *            displays empty spot as an empty square, first player's stone
	 *            as an empty circle, second player's stone as a solid circle.
	 */
	public void renderBoard(int mode) {
		System.out.println("   A B C D E F G H I J K L M N O P");
		char firstPlayerChar;
		char secondPlayerChar;
		char emptyLocChar;
		if (mode == CLASSIC_MODE) {
			firstPlayerChar = 'X';
			secondPlayerChar = 'O';
			emptyLocChar = '-';
		} else {
			firstPlayerChar = '\u25CB';
			secondPlayerChar = '\u25CF';
			emptyLocChar = '\u25A1';
		}

		for (int i = 0; i < this.basicGrid.length; i++) {
			System.out.print(i + 1);
			if (i < 9)
				System.out.print("\u0020\u0020");
			else
				System.out.print("\u0020");
			for (int j = 0; j < this.basicGrid[0].length; j++) {
				if (this.basicGrid[i][j] == EMPTY_SPOT)
					System.out.print(emptyLocChar + "\u0020");
				else if (this.basicGrid[i][j] == FIRST_PLAYER)
					System.out.print(firstPlayerChar + "\u0020");
				else
					System.out.print(secondPlayerChar + "\u0020");
			}
			System.out.print('\n');
		}
	}

	/**
	 * Resets the board to the init board. Also changes other fields associated
	 * with the board.
	 */
	public void reset() {
		this.basicGrid = initGrid();
		for (int i = 0; i < this.rows.size(); i++)
			for (int j = 0; j < this.rows.get(0).length; j++)
				this.getRows().get(i)[j] = EMPTY_SPOT;

		for (int i = 0; i < this.columns.size(); i++)
			for (int j = 0; j < this.columns.get(0).length; j++)
				this.getColumns().get(i)[j] = EMPTY_SPOT;

		for (int i = 0; i < this.diagonals_Uleft.size(); i++)
			for (int j = 0; j < this.diagonals_Uleft.get(i).length; j++)
				this.getULDiags().get(i)[j] = EMPTY_SPOT;

		for (int i = 0; i < this.diagonals_Uright.size(); i++)
			for (int j = 0; j < this.diagonals_Uright.get(i).length; j++)
				this.getURDiags().get(i)[j] = EMPTY_SPOT;
		this.player1Stone.clear();
		this.player2Stone.clear();

	}

	public boolean isEmpty() {
		for (int i = 0; i < this.basicGrid.length; i++)
			for (int j = 0; j < this.basicGrid[0].length; j++)
				if (this.basicGrid[i][j] != EMPTY_SPOT)
					return false;
		return true;
	}

	public int getGridVal(BoardLocation grid) {
		if (!isReachable(grid))
			return -1;
		int x_coord = grid.getXPos();
		int y_coord = grid.getYPos();
		return this.basicGrid[y_coord][x_coord];
	}

	/**
	 * Withdraws the given move on board. There is no stone after withdrawal.
	 *
	 * @param lastMove
	 *            The BoardLocation to withdraw.
	 * @throws InvalidIndexException
	 *             If the given BoardLocation is unreachable.
	 */
	public void withdrawMove(BoardLocation lastMove)
			throws InvalidIndexException {
		if (!isReachable(lastMove)) {
			throw new InvalidIndexException(
					"The location to withdraw is invalid.");
		}
		int x_coord = lastMove.getXPos();
		int y_coord = lastMove.getYPos();
		int indexUL = y_coord - x_coord + width - 1;
		int indexUR = y_coord + x_coord;
		int ULIndex = indexUL >= width ? x_coord : y_coord;
		int URIndex = indexUR >= width ? width - 1 - x_coord : y_coord;
		this.basicGrid[y_coord][x_coord] = 0;
		this.getColumns().get(x_coord)[y_coord] = EMPTY_SPOT;
		this.getRows().get(y_coord)[x_coord] = EMPTY_SPOT;
		this.getULDiags().get(indexUL)[ULIndex] = EMPTY_SPOT;
		this.getURDiags().get(indexUR)[URIndex] = EMPTY_SPOT;
		if (this.player1Stone.size() > this.player2Stone.size())
			this.player1Stone.remove(lastMove);
		else
			this.player2Stone.remove(lastMove);

	}

	/**
	 * Get the total number of stones on the board.
	 *
	 * @return The total number of stones.
	 */
	public int getTotalStones() {
		return this.player1Stone.size() + this.player2Stone.size();
	}

	public static BoardLocation getInvalidBoardLocation() {
		return new BoardLocation(height, width);
	}

	/**
	 * Determines whether the location is reachable. A location is reachable if
	 * its x-coordinate and y-coordinate are both in the range from 0 to 15.
	 * (Since the board is 16*16)
	 *
	 * @return True if the location is reachable and false otherwise.
	 */
	public static boolean isReachable(BoardLocation location) {
		if (location == null)
			return false;
		return location.getXPos() < width && location.getXPos() > -1
				&& location.getYPos() < height && location.getYPos() > -1;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static int getDiag() {
		return diag;
	}

	/**
	 * Finds the locations where the other player can block the pattern.
	 *
	 * @param locations
	 *            The BoardLocations forming the pattern.
	 * @param type
	 *            Whether the pattern is on row/column/upper-left
	 *            diagonal/upper-right diagonal.
	 * @return The list of all proper blocking locations of the given pattern.
	 */
	public ArrayList<BoardLocation> findBlockingLocs(
			ArrayList<BoardLocation> locations, int type) {
		ArrayList<BoardLocation> retLocs = new ArrayList<BoardLocation>();
		int firstIncrement;
		int secondIncrement;
		switch (type) {
		case Pattern.ON_ROW:
			firstIncrement = 0;
			secondIncrement = 1;
			break;
		case Pattern.ON_COL:
			firstIncrement = 1;
			secondIncrement = 0;
			break;
		case Pattern.ON_ULDIAG:
			firstIncrement = 1;
			secondIncrement = 1;
			break;
		case Pattern.ON_URDIAG:
			firstIncrement = 1;
			secondIncrement = -1;
			break;
		default:
			firstIncrement = 0;
			secondIncrement = 0;
			break;

		}
		if (Pattern.findBubbleIndex(locations, type) != -1
				&& locations.size() == 4) {
			int bubbleIndex = Pattern.findBubbleIndex(locations, type);
			ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
			retVal.add(new BoardLocation(locations.get(0).getYPos()
					+ firstIncrement * bubbleIndex, locations.get(0).getXPos()
					+ secondIncrement * bubbleIndex));
			return retVal;
		}

		for (int i = 0; i < locations.size(); i++) {
			BoardLocation candidate = new BoardLocation(locations.get(i)
					.getYPos() + firstIncrement, locations.get(i).getXPos()
					+ secondIncrement);
			BoardLocation anotherCandidate = new BoardLocation(locations.get(i)
					.getYPos() - firstIncrement, locations.get(i).getXPos()
					- secondIncrement);
			if (!locations.contains(candidate) && isReachable(candidate))
				if (this.basicGrid[candidate.getYPos()][candidate.getXPos()] == Board.EMPTY_SPOT
						&& !retLocs.contains(candidate))
					retLocs.add(candidate);
			if (!locations.contains(anotherCandidate)
					&& isReachable(anotherCandidate))
				if (this.basicGrid[anotherCandidate.getYPos()][anotherCandidate
						.getXPos()] == Board.EMPTY_SPOT
						&& !retLocs.contains(candidate))
					retLocs.add(anotherCandidate);
		}
		return retLocs;
	}

	/**
	 * Converts the diagonal index and the subIndex in a diagonal to x and y
	 * coordinates on board.
	 *
	 * @param diagIndex
	 *            The index of the diagonal on board.
	 * @param subIndex
	 *            The index of the location on the given diagonal
	 * @param isUL
	 *            Whether the diagonal is from upper-left to bottom-right.
	 * @return A BoardLocation as the result of the conversion.
	 */
	public static BoardLocation convertDiagToXY(int diagIndex, int subIndex,
			boolean isUL) {
		assert (diagIndex > -1 && diagIndex < diag && subIndex > -1 && subIndex < (diagIndex > (diag - 1) / 2 ? diag
				- diagIndex
				: diagIndex + 1));
		if (isUL)
			if (diagIndex < height)
				return new BoardLocation(subIndex, subIndex - diagIndex + width
						- 1);
			else
				return new BoardLocation(diagIndex + subIndex - width + 1,
						subIndex);
		else {
			if (diagIndex < height)
				return new BoardLocation(subIndex, diagIndex - subIndex);
			else
				return new BoardLocation(diagIndex + subIndex - width + 1,
						width - 1 - subIndex);
		}

	}

	/**
	 * Determines whether the pattern has potential to form five in a row.
	 * Special case if the pattern belongs to the first player, then more than
	 * five stones in a row is not allowed.
	 *
	 * @param pat
	 *            The pattern to check.
	 * @return True if the pattern has potential, false otherwise.
	 */
	public boolean isPatternDead(Pattern pat, boolean first) {
		int blocker = first ? Board.SECOND_PLAYER : Board.FIRST_PLAYER;
		if (pat.getClass().equals(ContOpenPattern.class)
				|| pat.getClass().equals(ContClosedPattern.class)) {
			if (pat.getClass().equals(ContOpenPattern.class)) {
				if (pat.getLocations().size() == 3) {
					if (pat.getType() == Pattern.ON_ULDIAG) {
						int ULIndex = getULDiagIndex(pat.getLocations().get(0));
						int[] curULDiag = getULDiagByIndex(ULIndex);
						if (ULIndex < 4 || ULIndex > diag - 4)
							return true;
						else if (getULDiagSubIndex(pat.getLocations().get(0)) == 0) {
							if (curULDiag[4] == blocker)
								return true;
						} else if (getULDiagSubIndex(pat.getLocations().get(0)) == curULDiag.length - 3) {
							if (curULDiag[curULDiag.length - 5] == blocker)
								return true;
						}
					} else if (pat.getType() == Pattern.ON_URDIAG) {
						int URIndex = getURDiagIndex(pat.getLocations().get(0));
						int[] curURDiag = getURDiagByIndex(URIndex);
						if (URIndex < 4 || URIndex > diag - 4)
							return true;
						else if (getURDiagSubIndex(pat.getLocations().get(0)) == 0) {
							if (curURDiag[4] == blocker)
								return true;
						} else if (getURDiagSubIndex(pat.getLocations().get(0)) == curURDiag.length - 3) {
							if (curURDiag[curURDiag.length - 5] == blocker)
								return true;
						}
					} else if (pat.getType() == Pattern.ON_ROW) {
						int[] curRow = this.getRowByIndex(pat.getLocations()
								.get(0).getYPos());
						if (pat.getLocations().get(0).getXPos() == 0) {
							if (curRow[4] == blocker)
								return true;
						} else if (pat.getLocations().get(0).getXPos() == width - 3) {
							if (curRow[width - 5] == blocker)
								return true;
						}
					} else if (pat.getType() == Pattern.ON_COL) {
						int[] curCol = this.getColumnByIndex(pat.getLocations()
								.get(0).getXPos());
						if (pat.getLocations().get(0).getYPos() == 0) {
							if (curCol[4] == blocker)
								return true;
						} else if (pat.getLocations().get(0).getYPos() == height - 3) {
							if (curCol[height - 5] == blocker)
								return true;
						}
					}
				} else if (pat.getLocations().size() == 4) {
					if (pat.getType() == Pattern.ON_ULDIAG) {
						int diagIndex = getULDiagIndex(pat.getLocations()
								.get(0));
						if (diagIndex < 4 || diagIndex > width - 4)
							return true;
						else
							return false;
					} else if (pat.getType() == Pattern.ON_URDIAG) {
						int diagIndex = getURDiagIndex(pat.getLocations()
								.get(0));
						if (diagIndex < 4 || diagIndex > width - 4)
							return true;
						else
							return false;
					} else {
						return false;
					}
				}
			} else {
				// TODO pattern is not open. need to do this part.
				BoardLocation firstStone = pat.getLocations().get(0);
				switch (pat.getType()) {
				case Pattern.ON_ROW:
					int rowSubIndex = firstStone.getXPos();
					if (pat.getLocations().size() == 4 && rowSubIndex == 0)
						return true;
					else if (pat.getLocations().size() == 4
							&& rowSubIndex == width - 4)
						return true;
					break;
				case Pattern.ON_COL:
					break;
				case Pattern.ON_ULDIAG:
					int ULDiagIndex = getULDiagIndex(firstStone);
					int[] ULDiag = getULDiagByIndex(ULDiagIndex);
					if (ULDiag.length <= 5)
						return true;
					else if (getULDiagSubIndex(firstStone) == 0) {
						// the pattern is cornered.
						if (pat.getLocations().size() == 4)
							return true;
					} else if (getULDiagSubIndex(firstStone) == ULDiag.length - 4
							&& pat.getLocations().size() == 4)
						return true;
					break;
				case Pattern.ON_URDIAG:
					int URDiagIndex = getURDiagIndex(firstStone);
					int[] URDiag = getURDiagByIndex(URDiagIndex);
					if (URDiag.length <= 5)
						return true;
					else if (getURDiagSubIndex(firstStone) == 0) {
						// the pattern is cornered.
						if (pat.getLocations().size() == 4)
							return true;
					} else if (getURDiagSubIndex(firstStone) == URDiag.length - 4
							&& pat.getLocations().size() == 4)
						return true;
					break;
				default:
					break;
				}
			}
		} else {
			if (pat.getClass().equals(algorithm.DiscOpenPattern.class)) {
				BoardLocation firstStone = pat.getLocations().get(0);
				if (pat.getType() == Pattern.ON_ULDIAG) {
					int ULDiagIndex = getULDiagIndex(firstStone);
					int[] ULDiag = getULDiagByIndex(ULDiagIndex);
					if (ULDiag.length < 5)
						return true;
				} else if (pat.getType() == Pattern.ON_URDIAG) {
					int URDiagIndex = getURDiagIndex(firstStone);
					int[] URDiag = getURDiagByIndex(URDiagIndex);
					if (URDiag.length < 5)
						return true;
				}
				return false;
			} else if (pat.getClass().equals(algorithm.DiscClosedPattern.class)) {
				return false;
			}
		}
		return false;
	}

	public static int getURDiagIndex(BoardLocation loc) {
		return loc.getXPos() + loc.getYPos();
	}

	public static int getULDiagIndex(BoardLocation loc) {
		return loc.getYPos() - loc.getXPos() + width - 1;
	}

	public static int getURDiagSubIndex(BoardLocation loc) {
		int urIndex = getURDiagIndex(loc);
		if (urIndex >= width)
			return width - 1 - loc.getXPos();
		else
			return loc.getYPos();
	}

	public static int getULDiagSubIndex(BoardLocation loc) {
		int ulIndex = getULDiagIndex(loc);
		if (ulIndex >= width)
			return loc.getXPos();
		else
			return loc.getYPos();
	}

	/**
	 * Get a list of stones blocking the given closed pattern.
	 *
	 * @param locations
	 *            The BoardLocations forming the pattern.
	 * @param type
	 *            The type of the pattern. Whether it is on a row/ column/
	 *            upper-right diagonal/ upper-left diagonal.
	 * @return A list of all blocking stones of the other party.
	 */
	public ArrayList<BoardLocation> getBlockedStones(
			ArrayList<BoardLocation> locations, int type) {
		boolean first = this.getGridVal(locations.get(0)) == 1;
		ArrayList<BoardLocation> retLocs = new ArrayList<BoardLocation>();
		int firstIncrement = 0;
		int secondIncrement = 0;
		switch (type) {
		case Pattern.ON_ROW:
			firstIncrement = 0;
			secondIncrement = 1;
			break;
		case Pattern.ON_COL:
			firstIncrement = 1;
			secondIncrement = 0;
			break;
		case Pattern.ON_ULDIAG:
			firstIncrement = 1;
			secondIncrement = 1;
			break;
		case Pattern.ON_URDIAG:
			firstIncrement = 1;
			secondIncrement = -1;
			break;
		default:
			return retLocs;
		}

		for (int i = 0; i < locations.size(); i++) {
			int xPos = locations.get(i).getXPos();
			int yPos = locations.get(i).getYPos();
			BoardLocation candidate1 = new BoardLocation(yPos + firstIncrement,
					xPos + secondIncrement);
			BoardLocation candidate2 = new BoardLocation(yPos - firstIncrement,
					xPos - secondIncrement);
			if (Board.isReachable(candidate1)) {
				if (first) {
					if (this.getGridVal(candidate1) == 2)
						retLocs.add(candidate1);
				} else {
					if (this.getGridVal(candidate1) == 1)
						retLocs.add(candidate1);
				}
			}

			if (Board.isReachable(candidate2)) {
				if (first) {
					if (this.getGridVal(candidate2) == 2)
						retLocs.add(candidate2);
				} else {
					if (this.getGridVal(candidate2) == 1)
						retLocs.add(candidate2);
				}
			}
		}
		return retLocs;
	}

	/**
	 * Checks if the first player fouled. The first player fouled if and only if
	 * it has more than five stones in a row.
	 *
	 * @return True if the first player fouls and false otherwise.
	 */
	public boolean checkFoul() {
		int consectCount = 0;
		int prev = EMPTY_SPOT;
		for (int[] array : this.rows) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] == FIRST_PLAYER)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_FOUL)
					return true;
			}
			prev = 0;
			consectCount = 0;
		}

		prev = EMPTY_SPOT;
		consectCount = 0;

		for (int[] array : this.columns) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] == FIRST_PLAYER)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_FOUL)
					return true;
			}
			prev = 0;
			consectCount = 0;
		}

		prev = EMPTY_SPOT;
		consectCount = 0;

		for (int[] array : this.diagonals_Uleft) {
			for (int i : array) {
				if (prev == i && i == FIRST_PLAYER)
					consectCount++;
				else {
					if (i != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = i;
				if (consectCount >= NUM_STONES_TO_FOUL)
					return true;
			}
			prev = EMPTY_SPOT;
			consectCount = 0;
		}

		consectCount = 0;
		prev = EMPTY_SPOT;

		for (int[] array : this.diagonals_Uright) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] == FIRST_PLAYER)
					consectCount++;
				else {
					if (array[i] != EMPTY_SPOT)
						consectCount = 1;
					else
						consectCount = 0;
				}
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_FOUL)
					return true;
			}
			prev = EMPTY_SPOT;
			consectCount = 0;
		}
		return false;
	}

	public static boolean isMiddleLocation(BoardLocation loc) {
		if (loc == null || !Board.isReachable(loc))
			return false;
		int x_coord = loc.getXPos();
		int y_coord = loc.getYPos();
		return !(x_coord < 3 || x_coord > width - 4 || y_coord < 3 || y_coord > width - 4);
	}

	public static boolean isInCorner(BoardLocation loc) {
		if (loc == null || !Board.isReachable(loc))
			return false;
		return (loc.getXPos() == 0 || loc.getXPos() == width - 1)
				&& (loc.getYPos() == 0 || loc.getYPos() == height - 1);
	}

	public static boolean isOnSide(BoardLocation loc) {
		if (loc == null || !Board.isReachable(loc))
			return false;
		return !isInCorner(loc)
				&& (loc.getXPos() == 0 || loc.getXPos() == width - 1
						|| loc.getYPos() == 0 || loc.getYPos() == height - 1);
	}

	/**
	 * Finds the adjacent locations to a location on board.
	 *
	 * @param loc
	 *            The location to check.
	 * @return All adjacent locations to that location.
	 */
	public static ArrayList<BoardLocation> findAdjacentLocs(BoardLocation loc) {
		if (loc == null || !Board.isReachable(loc))
			return new ArrayList<BoardLocation>();
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		int y_coor = loc.getYPos();
		int x_cord = loc.getXPos();
		if (!isInCorner(loc) && !isOnSide(loc)) {
			retVal.add(new BoardLocation(y_coor + 1, x_cord - 1));
			retVal.add(new BoardLocation(y_coor + 1, x_cord + 1));
			retVal.add(new BoardLocation(y_coor - 1, x_cord - 1));
			retVal.add(new BoardLocation(y_coor - 1, x_cord + 1));
			retVal.add(new BoardLocation(y_coor + 1, x_cord));
			retVal.add(new BoardLocation(y_coor - 1, x_cord));
			retVal.add(new BoardLocation(y_coor, x_cord + 1));
			retVal.add(new BoardLocation(y_coor, x_cord - 1));
		} else if (isOnSide(loc)) {
			if (y_coor == 0) {
				retVal.add(new BoardLocation(y_coor + 1, x_cord + 1));
				retVal.add(new BoardLocation(y_coor + 1, x_cord - 1));
				retVal.add(new BoardLocation(y_coor + 1, x_cord));
				retVal.add(new BoardLocation(y_coor, x_cord + 1));
				retVal.add(new BoardLocation(y_coor, x_cord - 1));
			} else if (x_cord == 0) {
				retVal.add(new BoardLocation(y_coor - 1, x_cord + 1));
				retVal.add(new BoardLocation(y_coor + 1, x_cord + 1));
				retVal.add(new BoardLocation(y_coor, x_cord + 1));
				retVal.add(new BoardLocation(y_coor - 1, x_cord));
				retVal.add(new BoardLocation(y_coor + 1, x_cord));
			} else if (y_coor == height - 1) {
				retVal.add(new BoardLocation(y_coor - 1, x_cord + 1));
				retVal.add(new BoardLocation(y_coor - 1, x_cord - 1));
				retVal.add(new BoardLocation(y_coor - 1, x_cord));
				retVal.add(new BoardLocation(y_coor, x_cord + 1));
				retVal.add(new BoardLocation(y_coor, x_cord - 1));
			} else {
				retVal.add(new BoardLocation(y_coor - 1, x_cord - 1));
				retVal.add(new BoardLocation(y_coor + 1, x_cord - 1));
				retVal.add(new BoardLocation(y_coor, x_cord - 1));
				retVal.add(new BoardLocation(y_coor - 1, x_cord));
				retVal.add(new BoardLocation(y_coor + 1, x_cord));
			}
		} else if (isInCorner(loc)) {
			if (y_coor == 0) {
				if (x_cord == 0) {
					retVal.add(new BoardLocation(y_coor + 1, x_cord + 1));
					retVal.add(new BoardLocation(y_coor, x_cord + 1));
					retVal.add(new BoardLocation(y_coor + 1, x_cord));
				} else {
					retVal.add(new BoardLocation(y_coor + 1, x_cord - 1));
					retVal.add(new BoardLocation(y_coor, x_cord - 1));
					retVal.add(new BoardLocation(y_coor + 1, x_cord));
				}
			} else {
				if (x_cord == 0) {
					retVal.add(new BoardLocation(y_coor - 1, x_cord + 1));
					retVal.add(new BoardLocation(y_coor, x_cord + 1));
					retVal.add(new BoardLocation(y_coor - 1, x_cord));
				} else {
					retVal.add(new BoardLocation(y_coor - 1, x_cord - 1));
					retVal.add(new BoardLocation(y_coor, x_cord - 1));
					retVal.add(new BoardLocation(y_coor - 1, x_cord));
				}
			}
		}
		return retVal;
	}

	public static int findTotalDistToSides(BoardLocation loc) {
		if (loc == null || !Board.isReachable(loc))
			return -1;
		int x_coord = loc.getXPos();
		int y_coord = loc.getYPos();
		int x_dist = Math.min(x_coord, width - 1 - x_coord);
		int y_dist = Math.min(y_coord, height - 1 - y_coord);
		return x_dist + y_dist;
	}

	public static int findDistance(BoardLocation loc1, BoardLocation loc2) {
		if (loc1 == null || loc2 == null || !Board.isReachable(loc1)
				|| !Board.isReachable(loc2))
			return -1;
		return Math.abs(loc1.getXPos() - loc2.getXPos())
				+ Math.abs(loc1.getYPos() - loc2.getYPos());
	}

	/**
	 * Spiraling from inward (the center of the board), finds the first
	 * occurrence of empty spot. Note that the checker spirals out clockwise.
	 *
	 * @return The first empty spot found on board.
	 */
	public BoardLocation findEmptyLocSpiral() {
		if (Game.boardFull())
			return Board.getInvalidBoardLocation();
		BoardLocation firstLoc;
		if (width % 2 == 0)
			firstLoc = new BoardLocation(height / 2 - 1, width / 2 - 1);
		else
			firstLoc = new BoardLocation(height / 2, width / 2);
		int firstIncX = 1;
		int secondIncY = 1;
		int thirdIncX = -1;
		int fourthIncY = -1;
		int curIncX = 1;
		int curIncY = 0;
		BoardLocation curLoc = firstLoc;
		if (width % 2 == 0)
			while (Board.isReachable(curLoc)) {
				if (!isOccupied(curLoc))
					return curLoc;
				int x_coord = curLoc.getXPos();
				int y_coord = curLoc.getYPos();
				if (x_coord == y_coord && x_coord < width / 2) {
					curIncX = firstIncX;
					curIncY = 0;
				} else if (x_coord + y_coord == width - 1
						&& y_coord < height / 2) {
					curIncX = 0;
					curIncY = 1;
				} else if (x_coord == y_coord && x_coord >= width / 2) {
					curIncX = -1;
					curIncY = 0;
				} else if (x_coord + y_coord == width - 2
						&& y_coord >= width / 2
						&& (x_coord != firstLoc.getXPos() || y_coord != firstLoc
								.getYPos())) {
					curIncX = 0;
					curIncY = -1;
				}
				curLoc = new BoardLocation(y_coord + curIncY, x_coord + curIncX);
			}
		else
			while (Board.isReachable(curLoc)) {
				if (!isOccupied(curLoc))
					return curLoc;
				int x_coord = curLoc.getXPos();
				int y_coord = curLoc.getYPos();
				if (x_coord == y_coord && x_coord <= width / 2) {
					curIncX = firstIncX;
					curIncY = 0;
				} else if (x_coord + y_coord == width && x_coord <= width / 2) {
					curIncX = 0;
					curIncY = secondIncY;
				} else if (x_coord == y_coord && x_coord > width / 2) {
					curIncX = thirdIncX;
					curIncY = 0;
				} else if (x_coord + y_coord == width - 1
						&& y_coord > height / 2) {
					curIncX = 0;
					curIncY = fourthIncY;
				}
				curLoc = new BoardLocation(y_coord + curIncY, x_coord + curIncX);
			}
		return Board.getInvalidBoardLocation();
	}

	public static BoardLocation getLocationWithLargestDist(
			ArrayList<BoardLocation> locations) {
		if (locations.size() == 0)
			return null;
		int maxIndex = 0;
		for (int i = 0; i < locations.size(); i++) {
			if (Board.findTotalDistToSides(locations.get(maxIndex)) < Board
					.findTotalDistToSides(locations.get(i)))
				maxIndex = i;
		}
		return locations.get(maxIndex);
	}

	public ArrayList<BoardLocation> filterOccupied(
			ArrayList<BoardLocation> locations) {
		ArrayList<BoardLocation> retVal = new ArrayList<BoardLocation>();
		for (BoardLocation loc : locations)
			if (!isOccupied(loc))
				retVal.add(loc);
		return retVal;
	}

	public boolean isPatternWinning(Pattern pat) {
		BoardLocation firstStone = pat.getLocations().get(0);
		int checker = basicGrid[firstStone.getYPos()][firstStone.getXPos()];
		int blocker = checker == Board.FIRST_PLAYER ? Board.SECOND_PLAYER
				: Board.FIRST_PLAYER;
		if (pat.getClass() == ContOpenPattern.class) {
			if (pat.getLocations().size() == 4) {
				switch (pat.getType()) {
				case Pattern.ON_ROW:
					if (firstStone.getXPos() == 0) {
						if (basicGrid[firstStone.getYPos()][5] == blocker)
							return false;
					} else if (firstStone.getXPos() == width - 3)
						if (basicGrid[firstStone.getYPos()][width - 5] == blocker)
							return false;
					break;
				case Pattern.ON_COL:
					if (firstStone.getYPos() == 0) {
						if (basicGrid[5][firstStone.getXPos()] == blocker)
							return false;
					} else if (firstStone.getYPos() == height - 3)
						if (basicGrid[height - 5][firstStone.getXPos()] == blocker)
							return false;
					break;
				case Pattern.ON_ULDIAG:
					int ULIndex = Board.getULDiagIndex(firstStone);
					int ULSubIndex = Board.getULDiagSubIndex(firstStone);
					int[] ulDiag = this.getULDiagByIndex(ULIndex);
					if (ulDiag.length <= 5)
						return false;
					else if (ULSubIndex == 0) {
						if (ulDiag[5] == blocker)
							return false;
					} else if (ULSubIndex == ulDiag.length - 3) {
						if (ulDiag[ulDiag.length - 5] == blocker)
							return false;
					}
					break;
				case Pattern.ON_URDIAG:
					int URIndex = Board.getURDiagIndex(firstStone);
					int URSubIndex = Board.getURDiagSubIndex(firstStone);
					int[] urDiag = this.getURDiagByIndex(ULIndex);
					if (urDiag.length <= 5)
						return false;
					else if (URSubIndex == 0) {
						if (urDiag[5] == blocker)
							return false;
					} else if (URSubIndex == urDiag.length - 3) {
						if (urDiag[urDiag.length - 5] == blocker)
							return false;
					}
					break;
				default:
					break;
				}
				return true;
			}
		} else if (pat.getClass() == DiscOpenPattern.class
				|| pat.getClass() == DiscClosedPattern.class) {
			int[] array;
			ArrayList<Pattern> patternsFound;
			switch (pat.getType()) {
			case Pattern.ON_ROW:
				array = this.getRowByIndex(firstStone.getYPos());
				break;
			case Pattern.ON_COL:
				array = this.getColumnByIndex(firstStone.getXPos());
				break;
			case Pattern.ON_ULDIAG:
				array = this.getULDiagByIndex(Board.getULDiagIndex(firstStone));
				break;
			case Pattern.ON_URDIAG:
				array = this.getURDiagByIndex(Board.getURDiagIndex(firstStone));
				break;
			default:
				break;
			}
			patternsFound = BoardChecker.checkOpenPatCont(array, 0,
					pat.getType(), true, 4, this);
			if (patternsFound.size() != 0
					&& pat.getLocations().contains(
							patternsFound.get(0).getLocations().get(0)))
				return true;
		}

		return false;
	}
}
