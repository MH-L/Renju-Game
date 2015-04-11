package Application;

import Exceptions.WithdrawException;
import Model.Board;
import Model.BoardLocation;

import java.util.Scanner;

public class Main {
	// TODO make changes to make this final
	private static Game game;

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		String inputStream = getGameMode(reader);
		// TODO get difficulty from the user
		game = Game.getInstance(Integer.parseInt(inputStream), 4);
		printInstruction();
		game.getBoard().renderBoard();
		boolean isPlayer1 = true;
		String dispStr;
		// note that the y-coord is the first, and the x-coord is the second;
		// to comply with the indices of the grid.
		// also notice that the location is always 1-based (user-friendly).
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
			if (inputStream.contains(",")) {
				inputStream = inputStream.trim();
				String[] inputs = inputStream.split(",");
				if (inputs.length != 2) {
					System.out.println("The input you entered is invalid!");
					continue;
				}
				int x_coord = translate(inputs[0]);
				int y_coord = Integer.parseInt(inputs[1]);
				BoardLocation toPlace = new BoardLocation(y_coord - 1, x_coord - 1);
				// TODO handle isReachable by exception
				if (!toPlace.isReachable()) {
					System.out.println("The input you entered is not valid.\n All coordinates"
							+ " must be between 1 and 16.");
					continue;
				};
				boolean success = game.getBoard().updateBoard(toPlace, isPlayer1);
				if (!success) {
					System.out.println("The move is invalid. Please try another.");
					continue;
				} else {
					game.getBoard().renderBoard();
				}
			}
			isPlayer1 = !isPlayer1;
		}
		if (isWinning(game.getBoard())){
			System.out.println("You won!");
		}
		reader.close();
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
}
