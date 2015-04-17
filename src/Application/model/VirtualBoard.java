package model;

import java.util.ArrayList;

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

	public VirtualBoard(Board board) {
		this.basicGrid = board.basicGrid;
		this.columns = board.columns;
		this.diagonals_Uleft = board.diagonals_Uleft;
		this.diagonals_Uright = board.diagonals_Uright;
		this.rows = board.rows;
		this.player1Stone = board.player1Stone;
		this.player2Stone = board.player2Stone;
		this.stepsToFuture = 0;
		this.additionalP1stones = new ArrayList<BoardLocation>();
		this.additionalP2stones = new ArrayList<BoardLocation>();
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

}
