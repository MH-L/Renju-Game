package Model;

import Algorithm.ContClosedPattern;
import Algorithm.ContOpenPattern;
import Algorithm.Pattern;
import Exceptions.InvalidIndexException;

import java.util.ArrayList;

/**
 * A class for the board. A board is always 16*16. It contains grid locations.
 *
 * @author Minghao Liu
 * @Date 2015/4/9
 *
 */
public class Board {
	// Board is a 16*16 grid.
	private int[][] basicGrid;
	private ArrayList<int[]> rows;
	private ArrayList<int[]> columns;
	private ArrayList<int[]> diagonals_Uleft;
	private ArrayList<int[]> diagonals_Uright;
	private ArrayList<ArrayList<BoardLocation>> locations;
	/**
	 * This is the width of the board. It is always 16.
	 */
	private static int width = 16;
	/**
	 * This is the height of the board. It is always 16.
	 */
	private static int height = 16;
	private static int diag = 31;
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static int getDiag() {
		return diag;
	}

	private ArrayList<BoardLocation> player1Stone;
	private ArrayList<BoardLocation> player2Stone;

	public static final int NUM_STONES_TO_WIN = 5;
	public static final int NUM_STONES_TO_FOUL = 6;
	public static final int EMPTY_SPOT = 0;
	public static final int FIRST_PLAYER = 1;
	public static final int SECOND_PLAYER = 2;
	public static final int CLASSIC_MODE = 1;
	public static final int FANCY_MODE = 2;

	/**
	 * A default constructor. Makes a default board of size 16.
	 */
	public Board() {
		this(16);
	}

	public Board(int size) {
		width = size;
		height = size;
		diag = 2 * size - 1;
		this.basicGrid = initGrid();
		this.rows = initRows();
		this.columns = initCols();
		this.diagonals_Uleft = initDiags();
		this.diagonals_Uright = initDiags();
		this.locations = initLocs();
		this.player1Stone = new ArrayList<BoardLocation>();
		this.player2Stone = new ArrayList<BoardLocation>();
	}

	public ArrayList<BoardLocation> getPlayer1Stone() {
		return player1Stone;
	}

	public ArrayList<BoardLocation> getPlayer2Stone() {
		return player2Stone;
	}

	// Getters for the board class.
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

	public ArrayList<ArrayList<BoardLocation>> getLocations() {
		return this.locations;
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
	 * Initialize the grid of the board. The grid is always 16*16. The init grid
	 * is empty. Each entry is just 0.
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
		if (locations.get(row_num).get(col_num).occupied())
			return false;
		int marker;
		if (first)
			marker = FIRST_PLAYER;
		else
			marker = SECOND_PLAYER;

		this.basicGrid[row_num][col_num] = marker;
		this.locations.get(row_num).get(col_num).setValue(marker);
		this.getColumns().get(col_num)[row_num] = marker;
		this.getRows().get(row_num)[col_num] = marker;
		int indexURDiag = getULDiagIndex(loc);
		int indexULDiag = getURDiagIndex(loc);
		if (indexURDiag >= width)
			this.getURDiags().get(indexURDiag)[width - 1 - col_num] = marker;
		else
			this.getURDiags().get(indexURDiag)[row_num] = marker;
		if (indexULDiag >= width)
			this.getULDiags().get(indexULDiag)[col_num] = marker;
		else
			this.getULDiags().get(indexULDiag)[row_num] = marker;
		this.player1Stone.add(loc);
		return true;

	}

	public boolean boardFull() {
		for (int i = 0; i < this.basicGrid.length; i++)
			for (int j = 0; j < this.basicGrid[0].length; j++)
				if (this.basicGrid[i][j] == 0)
					return false;
		return true;
	}

	public ArrayList<ArrayList<BoardLocation>> initLocs() {
		ArrayList<ArrayList<BoardLocation>> locations = new ArrayList<ArrayList<BoardLocation>>();
		for (int i = 0; i < width; i++) {
			ArrayList<BoardLocation> locs = new ArrayList<BoardLocation>();
			for (int j = 0; j < height; j++) {
				BoardLocation bdloc = new BoardLocation(i, j);
				locs.add(j, bdloc);
			}
			locations.add(i, locs);
		}
		return locations;
	}

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
		this.locations.get(y_coord).get(x_coord).setValue(EMPTY_SPOT);
		if (this.player1Stone.size() > this.player2Stone.size())
			this.player1Stone.remove(lastMove);
		else
			this.player2Stone.remove(lastMove);

	}

	/**
	 * Get the total number of stones on the board.
	 *
	 * @return the total number of stones.
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
	 * @return true if the location is reachable and false otherwise.
	 */
	public static boolean isReachable(BoardLocation location) {
		if (location == null)
			return false;
		return location.getXPos() < width && location.getXPos() > -1
				&& location.getYPos() < height && location.getYPos() > -1;
	}

	public static ArrayList<BoardLocation> findBlockingLocs(
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
		for (int i = 0; i < locations.size(); i++) {
			BoardLocation candidate = new BoardLocation(locations.get(i)
					.getYPos() + firstIncrement, locations.get(i).getXPos() + secondIncrement);
			BoardLocation anotherCandidate = new BoardLocation(locations
					.get(i).getYPos() - firstIncrement, locations.get(i).getXPos() - secondIncrement);
			if (!locations.contains(candidate) && isReachable(candidate))
				retLocs.add(candidate);
			if (!locations.contains(anotherCandidate)
					&& isReachable(anotherCandidate))
				retLocs.add(anotherCandidate);
		}

		return retLocs;
	}

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
				return new BoardLocation(width - 1 - diagIndex - subIndex, 2
						* diagIndex + subIndex - width + 1);
		}

	}

	// TODO make a static method to determine if a pattern is dead
	// i.e. if there is no chance to win
	public static boolean isPatternDead(Pattern pat) {
		if (pat.getClass().equals(ContOpenPattern.class)
				|| pat.getClass().equals(ContClosedPattern.class)) {
			if (pat.getClass().equals(ContOpenPattern.class)) {
				if (pat.getLocations().size() == 3) {
					// this is a special case since three can be already dead
					// even if open
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

			}
		} else {
			// TODO pattern is discontinuous. need to do this part.
		}

		return false;
	}

	public static int getULDiagIndex(BoardLocation loc) {
		return loc.getXPos() + loc.getYPos();
	}

	public static int getURDiagIndex(BoardLocation loc) {
		return loc.getYPos() - loc.getXPos() + width - 1;
	}

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
			BoardLocation candidate1 = new BoardLocation(yPos + firstIncrement, xPos + secondIncrement);
			BoardLocation candidate2 = new BoardLocation(yPos - firstIncrement, xPos - secondIncrement);
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
}
