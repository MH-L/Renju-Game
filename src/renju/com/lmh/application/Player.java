package renju.com.lmh.application;

import java.util.Scanner;

import renju.com.lmh.application.IPlayer;
import renju.com.lmh.exception.*;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

/**
 * This class records the recent moves of the player.
 * @author Minghao Liu
 * @author Kelvin Yip
 *
 */
public class Player implements IPlayer{

	public static final int NUM_HINTS_LIMIT = 3;
	public static final int NUM_REGRETS_LIMIT = 3;

	private BoardLocation lastMove;
	private int num_hints;
	private int num_regrets;

	public Player() {
		this.num_hints = NUM_HINTS_LIMIT;
		this.num_regrets = NUM_REGRETS_LIMIT;
		this.lastMove = null;
	}

	@Override
	public boolean withdraw() throws WithdrawException {
		if (this.lastMove == null)
			throw new NothingToWithdrawException("It is your first turn! You have nothing to withdraw!");
		else if (Board.isReachable(lastMove)) {
			if (this.num_regrets > 0) {
				num_regrets --;
				// TODO refactor
				lastMove = Board.getInvalidBoardLocation();
				return true;
			}
			else
				throw new OverWithdrawException("You don't have any withdrawals left!");
		} else
			throw new MultipleWithdrawException("You cannot withdraw two times!");
	}

	@Override
	public void forceWithdraw() {
		lastMove = Board.getInvalidBoardLocation();
	}

	public int getRegrets() {
		return num_regrets;
	}

	public int getNumHints() {
		return num_hints;
	}

	@Override
	public BoardLocation makeMove() throws InvalidIndexException {
		Scanner input = new Scanner(System.in);
		String move = input.nextLine();
		if (!move.contains(",")) {
			throw new InvalidIndexException(move);
		}
		String[] inputs = move.trim().split("\\s*,\\s*");
		if (inputs.length != 2) {
			throw new InvalidIndexException(move);
		}
		int x_coord;
		int y_coord;
		if (isAlpha(inputs[0]) && isNumber(inputs[1])) {
			x_coord = translateLetter(inputs[0]);
			y_coord = Integer.parseInt(inputs[1]);
		} else if (isNumber(inputs[0]) && isAlpha(inputs[1])) {
			x_coord = translateLetter(inputs[1]);
			y_coord = Integer.parseInt(inputs[0]);
		} else
			throw new InvalidIndexException(move);
		lastMove = new BoardLocation(y_coord - 1, x_coord - 1);
		return new BoardLocation(y_coord - 1, x_coord - 1);
	}

	private static int translateLetter(String letter) {
		if (letter.length() < 1)
			return 0;
		else
			return letter.toLowerCase().toCharArray()[0] - 96;
	}

	private static boolean isAlpha(String string){
		char[] chars = string.toCharArray();
		for (char c : chars){
			if (!Character.isLetter(c)){
				return false;
			}
		}
		return true;
	}

	public BoardLocation getLastMove() {
		return lastMove;
	}

	private static boolean isNumber(String string) {
		char[] chars = string.toCharArray();
		for (char c : chars){
			if (!Character.isDigit(c)){
				return false;
			}
		}
		return true;
	}
}