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
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:\n " +
				" (" + Game.MULTIPLAYER_GAME_MODE + ") multiplayer\n " +
				" (" + Game.SINGLEPLAYER_GAME_MODE + ") singleplayer");
		String inputStream = reader.next();
		while (!inputStream.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!inputStream.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			inputStream = reader.next();
		}
		// TODO get difficulty from the user
		game = Game.getInstance(Integer.parseInt(inputStream), 4);
		game.getBoard().renderBoard();
		boolean counter = true;
		String dispStr;
		// note that the y-coord is the first, and the x-coord is the second;
		// to comply with the indices of the grid.
		// also notice that the location is always 1-based (user-friendly).
		while (!boardFull(game.getBoard()) && !isWinning(game.getBoard())) {
			if (counter)
				dispStr = "First player, ";
			else
				dispStr = "Second player, ";

			System.out.println(dispStr + "now it is your turn to enter the place you want to\n insert the"
					+ " peg, use two numbers ranging from 1 to 16,\n and separate them using"
					+ " a comma.\n Note that the first one is the x-coordinate,\n and the"
					+ " second one is the y-coordinate. If you want to withdraw\n"
					+ " your last choice, enter w. If you want to exit the game,"
					+ " enter x.");
			inputStream = reader.next();
			if (inputStream.equals("x")) {
				System.out.println("Game Over!");
				reader.close();
				return;
			} else if (inputStream.equals("w")) {
				try {
					game.getPlayer1().withdraw();
				} catch (WithdrawException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
			if (inputStream.contains(",")) {
				inputStream = inputStream.trim();
				String[] inputs = inputStream.split(",");
				if (inputs.length == 2) {
					int x_coord = translate(inputs[0]);
					int y_coord = Integer.parseInt(inputs[1]);
					BoardLocation toPlace = new BoardLocation(y_coord - 1, x_coord - 1);
					// TODO handle isReachable by exception
					if (!toPlace.isReachable()) {
						System.out.println("The input you entered is not valid.\n All coordinates"
								+ " must be between 1 and 16.");
						continue;
					} else {
						boolean success;
						if (counter)
							success = game.getBoard().updateBoard(toPlace, true);
						else
							success = game.getBoard().updateBoard(toPlace, false);
						if (!success) {
							System.out.println("The move is invalid. Please try another.");
							continue;
						} else {
							// Succeeded.
							game.getBoard().renderBoard();
						}

					}
				} else {
					System.out.println("The input you entered is invalid!");
					continue;
				}
			}
			counter = !counter;
		}
		if (isWinning(game.getBoard())){
			System.out.println("You won!");
		}
		reader.close();
	}

	public static void showboard() {
		System.out.println("Here is the initial board.");
		// Prints out the initial board.
		// Note that player's pegs are denoted by an blank circle;
		// while the AI's pegs are denoted by a solid circle.
		// Board locations which are unoccupied are denoted by
		// a empty square.
		for (int i = 0; i < 16; i++)
			System.out.println("\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1"
					+ "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1");
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
}
