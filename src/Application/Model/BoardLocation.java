package Model;

public class BoardLocation {
	private int x_pos;
	private int y_pos;
	/**
	 * Indicates which party's chess is placed in the location.
	 */
	private int value;
	/**
	 * Important Notice: The y-coor and the x-coor is flipped,
	 * different from convention!
	 * @param y_loc The row number of the grid. Also the y coordinate.
	 * @param x_loc The column number of the grid. Also the x coordinate.
	 */
	public BoardLocation(int y_loc, int x_loc) {
		this.x_pos = x_loc;
		this.y_pos = y_loc;
	}

	public int getXPos() {
		return x_pos;
	}

	public int getYPos() {
		return y_pos;
	}

	/**
	 * Determines whether the location is reacheable.
	 * A location is reacheable if its x-coordinate and y-coordinate
	 * are both in the range from 0 to 15. (Since the board is 16*16)
	 * @return true if the location is reacheable and false otherwise.
	 */
	public boolean isReachable() {
		// TODO abstract this out of BoardLocation. This should be part of the Board
		return x_pos < 16 && x_pos > -1 && y_pos < 16 && y_pos > -1;
	}

	public boolean occupied() {
		return this.value != 0;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
