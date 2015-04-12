package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;

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

		while (!boardFull(game.getBoard()) && !isWinning(game.getBoard())) {
			System.out.println("Player " + game.getActivePlayerAsString() + ", it is your turn.");
			try {
				game.makeMove();
				game.getBoard().renderBoard();
			} catch (InvalidIndexException e) {
				switch (e.getMessage()){
					case "x":
						actionGameOver(reader);
						return;
					case "w":
						if (actionWithdraw())
							continue;
						break;
					case "i":
						printInstruction();
						break;
					default:
						System.out.println("The move is invalid. Please try another.");
				}
				continue;
			}
			game.toggleActivePlayer();
		}
		if (isWinning(game.getBoard())) {
			System.out.println("You won!");
		}
		reader.close();
	}

	private static boolean actionWithdraw() {
		try {
            game.getPlayer1().withdraw();
        } catch (WithdrawException e) {
            System.out.println(e.getMessage());
			return true;
        }
		return false;
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

	private static String getGameMode(Scanner reader) {
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:\n " +
				" (" + Game.MULTIPLAYER_GAME_MODE + ") multiplayer\n " +
				" (" + Game.SINGLEPLAYER_GAME_MODE + ") singleplayer");
		String gameMode = reader.next();
		while (!gameMode.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			gameMode = reader.nextLine();
		}
		return gameMode;
	}
}
