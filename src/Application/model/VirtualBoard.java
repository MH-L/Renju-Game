package model;

import java.util.ArrayList;

import algorithm.Pattern;
import exceptions.InvalidIndexException;

/**
 * Defines a virtual board. A virtual board is for calculating optimal locations
 * for AI to place its next stone.
 *
 * @author Minghao Liu
 *
 */
public class VirtualBoard extends Board {
	/**
	 * Just complying with the board class (which implements serializable).
	 */
	private static final long serialVersionUID = 8912340532666938303L;
	private ArrayList<BoardLocation> additionalP1stones;
	private ArrayList<BoardLocation> additionalP2stones;
	private int stepsToFuture;
	private boolean lastUpdateChanged;

	public static VirtualBoard getVBoard(Board board) {
		return new VirtualBoard(board.getGrids(), board.getRows(),
				board.getColumns(), board.getULDiags(), board.getURDiags(),
				board.getPlayer1Stone(), board.getPlayer2Stone(), board.getFirstPattern(),
				board.getSecondPattern(), board.getFirstCriticalLocs(), board.getSecondCriticalLocs());
	}

	@SuppressWarnings("unchecked")
	private VirtualBoard(int[][] grids, ArrayList<int[]> rows,
			ArrayList<int[]> cols, ArrayList<int[]> uldiags,
			ArrayList<int[]> urdiags, ArrayList<BoardLocation> player1locs,
			ArrayList<BoardLocation> player2locs, ArrayList<Pattern> firstPatterns,
			ArrayList<Pattern> secondPatterns, ArrayList<BoardLocation> firstCriticalLocs,
			ArrayList<BoardLocation> secondCriticalLocs) {
		setBasicGrid((int[][]) grids.clone());
		setRows((ArrayList<int[]>) rows.clone());
		setColumns((ArrayList<int[]>) cols.clone());
		setDiagonals_Uleft((ArrayList<int[]>) uldiags.clone());
		setDiagonals_Uright((ArrayList<int[]>) urdiags.clone());
		setPlayer1Stone((ArrayList<BoardLocation>) player1locs.clone());
		setPlayer2Stone((ArrayList<BoardLocation>) player2locs.clone());
		stepsToFuture = 0;
		additionalP1stones = new ArrayList<BoardLocation>();
		additionalP2stones = new ArrayList<BoardLocation>();
		setFirstPattern(firstPatterns);
		setSecondPattern(secondPatterns);
		setFirstCriticalLocs(firstCriticalLocs);
		setSecondCriticalLocs(secondCriticalLocs);
		lastUpdateChanged = false;
	}

	public ArrayList<BoardLocation> getAdditionalP1stones() {
		return additionalP1stones;
	}

	public ArrayList<BoardLocation> getAdditionalP2stones() {
		return additionalP2stones;
	}

	public int getStepsToFuture() {
		return stepsToFuture;
	}

	@Override
	public boolean updateBoard(BoardLocation location, boolean isFirst)
			throws InvalidIndexException {
		stepsToFuture++;
		if (isFirst)
			additionalP1stones.add(location);
		else
			additionalP2stones.add(location);
		lastUpdateChanged = super.updateBoard(location, isFirst);
		return lastUpdateChanged;
	}

	@Override
	public void withdrawMove(BoardLocation location) throws InvalidIndexException {
		if (!lastUpdateChanged)
			return;
		stepsToFuture--;
		super.withdrawMove(location);
	}

	public boolean updateBoardLite(BoardLocation loc, boolean first) throws InvalidIndexException {
		if (!isReachable(loc))
			throw new InvalidIndexException(
					"The location indexes is out of bound!");
		int col_num = loc.getXPos();
		int row_num = loc.getYPos();
		if (this.isOccupied(loc))
			return false;
		int marker = first ? Board.FIRST_PLAYER : Board.SECOND_PLAYER;

		this.getGrids()[row_num][col_num] = marker;
		this.getColumns().get(col_num)[row_num] = marker;
		this.getRows().get(row_num)[col_num] = marker;
		int indexURDiag = getURDiagIndex(loc);
		int indexULDiag = getULDiagIndex(loc);
		if (indexURDiag >= getWidth())
			this.getURDiags().get(indexURDiag)[getWidth() - 1 - col_num] = marker;
		else
			this.getURDiags().get(indexURDiag)[row_num] = marker;
		if (indexULDiag >= getWidth())
			this.getULDiags().get(indexULDiag)[col_num] = marker;
		else
			this.getULDiags().get(indexULDiag)[row_num] = marker;
		if (first)
			this.getPlayer1Stone().add(loc);
		else
			this.getPlayer2Stone().add(loc);
		return true;
	}

	public void withdrawMoveLite(BoardLocation lastMove) throws InvalidIndexException {
		if (!isReachable(lastMove)) {
			throw new InvalidIndexException(
					"The location to withdraw is invalid.");
		}
		boolean first;
		int x_coord = lastMove.getXPos();
		int y_coord = lastMove.getYPos();
		int indexUL = y_coord - x_coord + getWidth() - 1;
		int indexUR = y_coord + x_coord;
		int ULIndex = indexUL >= getWidth() ? x_coord : y_coord;
		int URIndex = indexUR >= getWidth() ? getWidth() - 1 - x_coord : y_coord;
		this.getGrids()[y_coord][x_coord] = 0;
		this.getColumns().get(x_coord)[y_coord] = EMPTY_SPOT;
		this.getRows().get(y_coord)[x_coord] = EMPTY_SPOT;
		this.getULDiags().get(indexUL)[ULIndex] = EMPTY_SPOT;
		this.getURDiags().get(indexUR)[URIndex] = EMPTY_SPOT;
		if (getPlayer1Stone().contains(lastMove)) {
			this.getPlayer1Stone().remove(lastMove);
			first = true;
		}
		else {
			this.getPlayer2Stone().remove(lastMove);
			first = false;
		}
	}
}
