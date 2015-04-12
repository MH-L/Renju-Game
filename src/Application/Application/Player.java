package Application;

import Exceptions.*;
import Model.BoardLocation;

import java.util.Scanner;

/**
 * This class records the recent moves of the player.
 * @author Minghao Liu
 *
 */
public class Player implements IPlayer{
	private BoardLocation lastMove;
	private int num_hints;
	private int num_regrets;

	public Player() {
		this.num_hints = 3;
		this.num_regrets = 3;
		this.lastMove = null;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		if (this.lastMove == null)
			throw new NothingToWithdrawException("It is your first turn! You have nothing to withdraw!");
		else if (Model.Board.isReachable(lastMove)) {
			if (this.num_regrets > 0) {
				num_regrets --;
				// TODO refactor
				lastMove = Model.Board.getInvalidBoardLocation();
				return true;
			}
			else
				throw new OverWithdrawException("You don't have any withdrawals left!");
		} else
			throw new MultipleWithdrawException("You cannot withdraw two times!");
	}

	@Override
	public void forceWithdraw() {
		lastMove = Model.Board.getInvalidBoardLocation();
	}

	public int getRegrets() {
		return num_regrets;
	}

	public int getNumHints() {
		return num_hints;
	}

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		// note that the y-coord is the first, and the x-coord is the second;
		// to comply with the indices of the grid.
		// also notice that the location is always 1-based (user-friendly).enerated method stub
		Scanner input = new Scanner(System.in);
		String move = input.nextLine();
		if (!move.contains(",")) {
			throw new InvalidIndexException(move);
		}
		String[] inputs = move.split(",");
		if (inputs.length != 2) {
			throw new InvalidIndexException("The input you entered is invalid!");
		}
		int x_coord = translateLetter(inputs[0]);
		int y_coord = Integer.parseInt(inputs[1]);
		return new BoardLocation(y_coord - 1, x_coord - 1);
	}

	private static int translateLetter(String letter) {
		return letter.toLowerCase().toCharArray()[0] - 96;
	}

}
