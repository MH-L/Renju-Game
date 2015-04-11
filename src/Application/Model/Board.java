package Model;

import Exceptions.InvalidIndexException;

import java.util.ArrayList;

/**
 * A class for the board. A board
 * is always 16*16. It contains
 * grid locations.
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
	private static int WIDTH = 16;
	/**
	 * This is the height of the board. It is always 16.
	 */
	private static int HEIGHT = 16;
	private static int DIAG = 31;

	private static final int NUM_STONES_TO_WIN = 5;
	private static final int EMPTY_SPOT = 0;
	private static final int FIRST_PLAYER = 1;
	private static final int SECOND_PLAYER = 2;
	/**
	 * A default constructor. Makes a default board of size 16.
	 */
	public Board() {
		this(16);
	}

	public Board(int size) {
		WIDTH = size;
		HEIGHT = size;
		DIAG = 2*size - 1;
		this.basicGrid = initGrid();
		this.rows = initRows();
		this.columns = initCols();
		this.diagonals_Uleft = initDiags();
		this.diagonals_Uright = initDiags();
		this.locations = initLocs();
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
		return this.diagonals_Uleft;
	}

	public int[][] getGrids() {
		return this.basicGrid;
	}

	public ArrayList<ArrayList<BoardLocation>> getLocations() {
		return this.locations;
	}

	public int[] getColumnByIndex(int index) {
		if (index < 0 || index > WIDTH)
			try {
				throw new InvalidIndexException("The column index is out of bounds.");
			} catch (InvalidIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return this.getColumns().get(index);
	}

	public int[] getRowByIndex(int index) {
		if (index < 0 || index > HEIGHT) {
			try {
				throw new InvalidIndexException("The row index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getRows().get(index);
	}

	public int[] getULDiagByIndex(int index) {
		if (index < 0 || index > DIAG) {
			try {
				throw new InvalidIndexException("The diagonal index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getULDiagByIndex(index);
	}

	public int[] getURDiagByIndex(int index) {
		if (index < 0 || index > DIAG) {
			try {
				throw new InvalidIndexException("The diagonal index is out of bounds.");
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			}
		}

		return this.getURDiagByIndex(index);
	}

	/**
	 * Initialize the grid of the board.
	 * The grid is always 16*16.
	 * The init grid is empty. Each entry is just 0.
	 * @return The initial grid.
	 */
	private int[][] initGrid() {
		int[][] grid = new int[HEIGHT][WIDTH];
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid.length; j++)
				grid[i][j] = 0;
		return grid;
	}

	private ArrayList<int[]> initRows() {
		ArrayList<int[]> rows = new ArrayList<int[]>();
		for (int i = 0; i < HEIGHT; i++) {
			int[] arr = new int[WIDTH];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = 0;
			}
			rows.add(arr);
		}
		return rows;
	}

	private ArrayList<int[]> initCols() {
		ArrayList<int[]> cols = new ArrayList<int[]>();
		for (int i = 0; i < WIDTH; i++) {
			int[] arr = new int[HEIGHT];
			for (int j = 0; j < arr.length; j++) {
				arr[j] = 0;
			}
			cols.add(arr);
		}
		return cols;
	}

	private ArrayList<int[]> initDiags() {
		ArrayList<int[]> diags = new ArrayList<int[]>();
		for (int i = 0; i < DIAG; i++) {
			int[] arr;
			if (i < WIDTH)
				arr = new int[i + 1];
			else
				arr = new int[DIAG - i];
			for (int j = 0; j < arr.length; j++)
				arr[j] = 0;
			diags.add(arr);
		}
		return diags;
	}

	public boolean checkrow() {
		int consectCount = 0; // This counts the number of consecutive stones for one player.
		int prev = 0; // This is the last stone. 0 denotes that there is no last stone.
		for (int[] array : this.rows) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != 0)
					consectCount ++;
				else
					consectCount = 1;
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
		int prev = 0;
		for (int[] array : this.columns) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != 0)
					consectCount ++;
				else
					consectCount = 1;
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
		int prev = 0;
		for (int[] array : this.diagonals_Uleft) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != 0)
					consectCount ++;
				else
					consectCount = 1;
				prev = array[i];
				if (consectCount >= NUM_STONES_TO_WIN)
					return true;
			}
			prev = 0;
			consectCount = 0;
		}

		for (int[] array : this.diagonals_Uright) {
			for (int i = 0; i < array.length; i++) {
				if (prev == array[i] && array[i] != 0)
					consectCount ++;
				else
					consectCount = 1;
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
	 * The function updates the board given the location and the player.
	 * @param loc Indicates the board location to place the stone
	 * @param player True means it is player's stone, otherwise it is computer's stone.
	 * @return
	 * 		false if it did not succeed. true if succeeded
	 * @throws InvalidIndexException
	 */
	public boolean updateBoard(BoardLocation loc, boolean player) throws InvalidIndexException {
		if (!isReachable(loc))
			throw new InvalidIndexException("The location indexes is out of bound!");
		int col_num = loc.getXPos();
		int row_num = loc.getYPos();
		if (locations.get(row_num).get(col_num).occupied())
			return false;
		if (player) {
			this.basicGrid[row_num][col_num] = FIRST_PLAYER;
			this.locations.get(row_num).get(col_num).setValue(FIRST_PLAYER);
			this.getColumns().get(col_num)[row_num] = FIRST_PLAYER;
			this.getRows().get(row_num)[col_num] = FIRST_PLAYER;
			int indexURDiag = col_num + row_num;
			int indexULDiag = row_num - col_num + WIDTH - 1;
			if (indexURDiag >= WIDTH)
				this.getURDiags().get(indexURDiag)[WIDTH - 1 - col_num] = FIRST_PLAYER;
			else
				this.getURDiags().get(indexURDiag)[row_num] = FIRST_PLAYER;
			if (indexULDiag >= WIDTH)
				this.getULDiags().get(indexULDiag)[col_num] = FIRST_PLAYER;
			else
				this.getULDiags().get(indexULDiag)[row_num] = FIRST_PLAYER;
			return true;
		} else {
			this.basicGrid[row_num][col_num] = SECOND_PLAYER;
			this.locations.get(row_num).get(col_num).setValue(SECOND_PLAYER);
			this.getColumns().get(col_num)[row_num] = SECOND_PLAYER;
			this.getRows().get(row_num)[col_num] = SECOND_PLAYER;
			int indexURDiag = col_num + row_num;
			int indexULDiag = row_num - col_num + WIDTH - 1;
			if (indexURDiag >= WIDTH)
				this.getURDiags().get(indexURDiag)[WIDTH - 1 - col_num] = SECOND_PLAYER;
			else
				this.getURDiags().get(indexURDiag)[row_num] = SECOND_PLAYER;
			if (indexULDiag >= WIDTH)
				this.getULDiags().get(indexULDiag)[col_num] = SECOND_PLAYER;
			else
				this.getULDiags().get(indexULDiag)[row_num] = SECOND_PLAYER;
			return true;
		}
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
		for (int i = 0; i < WIDTH; i++){
			ArrayList<BoardLocation> locs = new ArrayList<BoardLocation>();
			for (int j = 0; j < HEIGHT; j++) {
				BoardLocation bdloc = new BoardLocation(i, j);
				locs.add(j, bdloc);
			}
			locations.add(i, locs);
		}
		return locations;
	}

	public void renderBoard() {
		System.out.println("   A B C D E F G H I J K L M N O P");
		for (int i = 0; i < this.basicGrid.length; i++) {
			System.out.print(i + 1);
			if (i < 9)
				System.out.print("\u0020\u0020");
			else
				System.out.print("\u0020");
			for (int j = 0; j < this.basicGrid[0].length; j++) {
				if (this.basicGrid[i][j] == EMPTY_SPOT)
					System.out.print("\u25A1\u0020");
				else if (this.basicGrid[i][j] == FIRST_PLAYER)
					System.out.print("\u25CB\u0020");
				else
					System.out.print("\u25CF\u0020");
			}
			System.out.print('\n');
		}
	}

	/**
	 * Resets the board to the init board. Also changes other fields associated with
	 * the board.
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

	public void withdrawMove(BoardLocation lastMove) {
		int x_coord = lastMove.getXPos();
		int y_coord = lastMove.getYPos();
		int indexUL = y_coord - x_coord + WIDTH - 1;
		int indexUR = y_coord + x_coord;
		int ULIndex = indexUL >= WIDTH ? y_coord : x_coord;
		int URIndex = indexUR >= WIDTH ? WIDTH - 1 - x_coord : y_coord;
		this.basicGrid[y_coord][x_coord] = 0;
		this.getColumns().get(x_coord)[y_coord] = EMPTY_SPOT;
		this.getRows().get(y_coord)[x_coord] = EMPTY_SPOT;
		this.getULDiags().get(indexUL)[ULIndex] = EMPTY_SPOT;
		this.getURDiags().get(indexUR)[URIndex] = EMPTY_SPOT;
		this.locations.get(y_coord).get(x_coord).setValue(EMPTY_SPOT);

	}

	/**
	 * Get the total number of stones on the board.
	 * @return the total number of stones.
	 */
	public int getTotalStones() {
		int count = 0;
		for (int i = 0; i < this.basicGrid.length; i++)
			for (int j = 0; j < this.basicGrid[0].length; j++)
				if (getGridVal(new BoardLocation(i, j)) != EMPTY_SPOT)
					count ++;
		return count;
	}

	public static BoardLocation getInvalidBoardLocation(){
		return new BoardLocation(HEIGHT, WIDTH);
	}

	/**
	 * Determines whether the location is reacheable.
	 * A location is reacheable if its x-coordinate and y-coordinate
	 * are both in the range from 0 to 15. (Since the board is 16*16)
	 * @return true if the location is reacheable and false otherwise.
	 */
	public static boolean isReachable(BoardLocation location) {
		return location.getXPos() < WIDTH && location.getXPos() > -1
				&& location.getYPos() < HEIGHT && location.getYPos() > -1;
	}
}
