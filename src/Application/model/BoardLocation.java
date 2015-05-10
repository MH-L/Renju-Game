package model;

import java.io.Serializable;

public class BoardLocation implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1911182207997358711L;
	private int x_pos;
	private int y_pos;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x_pos;
		result = prime * result + y_pos;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardLocation other = (BoardLocation) obj;
		if (x_pos != other.x_pos)
			return false;
		if (y_pos != other.y_pos)
			return false;
		return true;
	}

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

	public boolean compare(BoardLocation loc) {
		return this.getXPos() == loc.getXPos() && this.getYPos() == loc.getYPos();
	}

	public static boolean compare(BoardLocation loc1, BoardLocation loc2) {
		return loc1.getXPos() == loc2.getXPos() && loc1.getYPos() == loc2.getYPos();
	}

}
