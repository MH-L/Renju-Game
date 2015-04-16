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
