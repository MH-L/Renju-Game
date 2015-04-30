package application;

import application.command.Command;
import application.command.Move;
import application.command.Quit;
import application.command.Withdraw;
import exceptions.InvalidIndexException;
import model.BoardLocation;

import java.util.Random;
import java.util.Scanner;

/**
 * This class records the recent moves of the player.
 * @author Minghao Liu
 * @author Kelvin Yip
 *
 */
public class Player implements IPlayer {
	private String id;

	public Player() {
//		System.out.println("What is your User ID?");
//		Scanner reader = new Scanner(System.in);
//		setId(reader.nextLine());
		// generate random ID since System.in input for testing doesn't work for me
		String alphaNumerals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder id = new StringBuilder();
		Random rnd = new Random();
		while (id.length() < 16) {
			int index = (int) (rnd.nextInt(alphaNumerals.length()));
			id.append(alphaNumerals.charAt(index));
		}
		this.id = id.toString();
		System.out.println("Hello, " + getId());
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

//	public boolean withdraw() throws WithdrawException {
//		if (this.lastMove == null)
//			throw new NothingToWithdrawException("It is your first turn! You have nothing to withdraw!");
//		else if (model.Board.isReachable(lastMove)) {
//			if (this.num_regrets > 0) {
//				num_regrets --;
//				// TODO refactor
//				lastMove = model.Board.getInvalidBoardLocation();
//				return true;
//			}
//			else
//				throw new OverWithdrawException("You don't have any withdrawals left!");
//		} else
//			throw new MultipleWithdrawException("You cannot withdraw two times!");
//	}

	public BoardLocation makeMove() throws InvalidIndexException {
		Scanner input = new Scanner(System.in);
		String move = input.nextLine();
		BoardLocation lastMove = translateBoardLocation(move);
		return lastMove;
	}

	public static BoardLocation translateBoardLocation(String move) throws InvalidIndexException {
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

	@Override
	public Command getCommand() {
		Scanner in = new Scanner(System.in);
		String move = in.nextLine().trim();
		if (move.contains(",")) {
			try {
				return new Move(this, translateBoardLocation(move));
			} catch (InvalidIndexException e) {
				System.out.println("Invalid index. Try again");
				getCommand();
			}
		} else if(move.equals("x")) {
			return new Quit(this);
		} else if (move.equals("w")){
			return new Withdraw(this);
		}
		return null;
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