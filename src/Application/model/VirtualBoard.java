package model;

import java.util.ArrayList;

import exceptions.InvalidIndexException;

/**
 * Defines a virtual board. A virtual board is for calculating optimal locations
 * for AI to place its next stone.
 *
 * @author Minghao Liu
 *
 */
public class VirtualBoard extends Board {
	private ArrayList<BoardLocation> additionalP1stones;
	private ArrayList<BoardLocation> additionalP2stones;
	private int stepsToFuture;

	public static VirtualBoard getVBoard(Board board) {
		return new VirtualBoard(board.getGrids(), board.getRows(),
				board.getColumns(), board.getULDiags(), board.getURDiags(),
				board.getPlayer1Stone(), board.getPlayer2Stone());
	}

	@SuppressWarnings("unchecked")
	private VirtualBoard(int[][] grids, ArrayList<int[]> rows,
			ArrayList<int[]> cols, ArrayList<int[]> uldiags,
			ArrayList<int[]> urdiags, ArrayList<BoardLocation> player1locs,
			ArrayList<BoardLocation> player2locs) {
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
		return super.updateBoard(location, isFirst);
	}

}
