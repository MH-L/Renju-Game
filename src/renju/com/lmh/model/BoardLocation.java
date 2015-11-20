package renju.com.lmh.model;

import java.io.Serializable;

import renju.com.lmh.model.Board;

public class BoardLocation implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1911182207997358711L;
	public static final int ON_SAME_ROW = 1;
	public static final int ON_SAME_COL = 2;
	public static final int ON_SAME_ULDIAG = 3;
	public static final int ON_SAME_URDIAG = 4;
	public static final int ARE_SAME_LOC = 5;
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

	/**
	 * Checks if the two BoardLocation s are on the same row
	 * or column or upper-left diagonal or upper-right diagonal.
	 * @param loc1
	 * @param loc2
	 * @return
	 * 		0 if they are not, 1 if they are on the same row,
	 * 		2 if they are on the same column, 3 if they are on
	 * 		the same upper-left diagonal, 4 if they are on the
	 * 		same upper-right diagonal. (If they are the same, then
	 * 		the value 5 is returned).
	 */
	public static int isOnSameLine(BoardLocation loc1, BoardLocation loc2) {
		if (loc1 == null || loc2 == null || !Board.isReachable(loc1) || !Board.isReachable(loc2))
			return 0;
		if (loc1.equals(loc2))
			return ARE_SAME_LOC;
		if (loc1.getYPos() == loc2.getYPos())
			return ON_SAME_ROW;
		if (loc1.getXPos() == loc2.getXPos())
			return ON_SAME_COL;
		int loc1ULDiagIndex = Board.getULDiagIndex(loc1);
		int loc1URDiagIndex = Board.getURDiagIndex(loc1);
		int loc2ULDiagIndex = Board.getULDiagIndex(loc2);
		int loc2URDiagIndex = Board.getURDiagIndex(loc2);
		if (loc1ULDiagIndex == loc2ULDiagIndex)
			return ON_SAME_ULDIAG;
		if (loc1URDiagIndex == loc2URDiagIndex)
			return ON_SAME_URDIAG;
		return 0;
	}

}
