package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;
import Model.BoardLocation;

import java.util.Scanner;
/**
 * The start point of the application.
 * @author Minghao Liu
 * @ModifiedBy Kelvin Yip
 *
 */
public class Main {
	// TODO make changes to make this final
	private static Game game;

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		String inputStream = getGameMode(reader);
		String dispMode = getDisplayMode(reader);
		// TODO get difficulty from the user
		printInstruction();
		if (Integer.parseInt(inputStream) == 2) {
			String difficulty = getDifficulty(reader);
			switch(Integer.parseInt(difficulty)) {
				case 1:
					game = Game.getInstance(2, 1);
					break;
				case 2:
					game = Game.getInstance(2, 2);
					break;
				case 3:
					game = Game.getInstance(2, 3);
					break;
				case 4:
					game = Game.getInstance(2, 4);
					break;
				default:
					System.out.println("Internal Error!");
					return;
			}
		}
		game = Game.getInstance(Integer.parseInt(inputStream), 4);
		System.out.println("Now the game starts.\nThe initial board is shown as follows:\n");
		game.getBoard().renderBoard(Integer.parseInt(dispMode));
		if (game.getMode() == 1) {
			singlePlayerGameStart(reader, inputStream, dispMode);
		} else if (game.getMode() == 2) {
			multiPlayerGameStart(reader, dispMode);
		}

		reader.close();
	}

	private static boolean sanitiseAndPrint(String inputStream, boolean isPlayer1, String dispMode) {
		if (inputStream.contains(",")) {
			inputStream = inputStream.trim();
			String[] inputs = inputStream.split(",");
			if (inputs.length != 2) {
				System.out.println("The input you entered is invalid!");
				return false;
			} else if (!isInteger(inputs[1])) {
				System.out.println("The second input must be an integer from 1 to 16!");
				return false;
			}
			int x_coord = translate(inputs[0]);
			int y_coord = Integer.parseInt(inputs[1]);
			BoardLocation toPlace = new BoardLocation(y_coord - 1,
					x_coord - 1);
			try {
				game.getBoard().updateBoard(toPlace, isPlayer1);
				game.getBoard().renderBoard(Integer.parseInt(dispMode));
			} catch (InvalidIndexException e) {
				System.out.println("The move is invalid. Please try another.");
				return false;
			}
		} else {
			System.out.println("The input is invalid. Please try again!");
			return false;
		}
		return true;

	}

	private static void actionGameOver(Scanner reader) {
		System.out.println("Game Over!");
		reader.close();
	}

	private static void printInstruction() {
		// TODO fix the magic number
		System.out.println("Game instruction:\nEach player takes turn to place a stone on the board." +
				"\nThe first one to place 5 consecutive stones in a row wins the game." +
				"\nTo place a stone, select the x-position then y-position delimited by a comma." +
				"\n  For example: A,1 or B,3." +
				"\n\nYou are allowed to undo your last move up to 3 times." +
				"Enter \"w\" to withdraw when it is your turn." +
				"\nTo exit the game, enter \"x\". " +
				"\nTo reshow the instruction, enter \"i\"\n");

	}

	public static boolean isWinning(Board board) {
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	public static boolean boardFull(Board board) {
		return board.boardFull();
	}

	public static int translate(String letter) {
		return letter.toLowerCase().toCharArray()[0] - 96;
	}

	private static String getGameMode(Scanner reader) {
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:\n " +
				" (" + Game.MULTIPLAYER_GAME_MODE + ") multiplayer\n " +
				" (" + Game.SINGLEPLAYER_GAME_MODE + ") singleplayer");
		String gameMode = reader.next();
		while (!gameMode.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			gameMode = reader.next();
		}
		return gameMode;
	}

	private static String getDisplayMode(Scanner reader) {
		System.out.println(" Please enter your display mode:\n"
				+ " (" + Board.CLASSIC_MODE + ") Classic mode\n"
				+ " (" + Board.FANCY_MODE + ") Fancy mode\n"
				+ " (Note that the fancy mode may require unicode plugin for your cmd.)");
		String displayMode = reader.next();
		while (!displayMode.equals(String.valueOf(Board.CLASSIC_MODE)) &&
				!displayMode.equals(String.valueOf(Board.FANCY_MODE))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			displayMode = reader.next();
		}
		return displayMode;
	}

	public static boolean isInteger(String input) {
		try {
			@SuppressWarnings("unused")
			int i = Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	private static void singlePlayerGameStart(Scanner reader, String inputStream, String dispMode) {
		boolean isPlayer1 = true;
		String dispStr;
		while (!boardFull(game.getBoard()) && !isWinning(game.getBoard())) {
			if (isPlayer1)
				dispStr = "one";
			else
				dispStr = "two";

			System.out.println("Player " + dispStr + ", it is your turn.");
			inputStream = reader.next();
			switch (inputStream){
				case "x":
					actionGameOver(reader);
					return;
				case "w":
					try {
						game.getPlayer1().withdraw();
					} catch (WithdrawException e) {
						System.out.println(e.getMessage());
						continue;
					}
					break;
				case "i":
					printInstruction();
					break;
			}
			if (!sanitiseAndPrint(inputStream, isPlayer1, dispMode))
				continue;
			isPlayer1 = !isPlayer1;
		}
		if (isWinning(game.getBoard())) {
			System.out.println("You won!");
		}
	}

	private static String getDifficulty(Scanner reader) {
		System.out.println(" Please enter the AI difficulty:\n"
				+ " (" + Game.NOVICE_DIFFICULTY + ") Novice\n"
				+ " (" + Game.INTERMEDIATE_DIFFICULTY + ") Intermediate\n"
				+ " (" + Game.ADVANCED_DIFFICULTY + ") Advanced\n"
				+ " (" + Game.ULTIMATE_DIFFICULTY + ") Ultimate\n");
		String difficulty = reader.next();

		while (!difficulty.equals(String.valueOf(Game.NOVICE_DIFFICULTY)) &&
				!difficulty.equals(String.valueOf(Game.INTERMEDIATE_DIFFICULTY)) &&
				!difficulty.equals(String.valueOf(Game.ADVANCED_DIFFICULTY)) &&
				!difficulty.equals(String.valueOf(Game.ULTIMATE_DIFFICULTY))) {
			System.out.println("Invalid difficulty level. Please re-enter your choice.");
			difficulty = reader.next();
		}
		return difficulty;
	}

	private static void multiPlayerGameStart(Scanner reader, String dispMode) {


	}

}
