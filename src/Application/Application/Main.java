package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;

import java.util.Scanner;
/**
 * The start point of the application.
 * @author Minghao Liu
 * @ModifiedBy Kelvin Yip
 *
 */
public class Main {
	private static Game game;
	private static Scanner reader;
	private static int dispMode = Board.CLASSIC_MODE;

	public static void main(String[] args) {
		// Get initializations
		reader = new Scanner(System.in);
		int mode = getGameMode();
		dispMode = getDisplayMode();
		game = Game.getInstance();
		printInstruction();

		// Initialize game mode
		if (mode == Game.SINGLEPLAYER_GAME_MODE) {
			int diff = getDifficulty();
			if (Game.NOVICE_DIFFICULTY <= diff && diff <= Game.ULTIMATE_DIFFICULTY){
				// This would be better if initSinglePlayer handled this input
				// but creating a new exception might be overkill for something
				// that likely isn't going to change
				game.initSinglePlayer(diff);
			} else {
				System.err.println("Internal Error!");
				return;
			}
		} else {
			game.initMultiplayer();
		}

		System.out.println("Now the game starts.\nThe initial board is shown as follows:\n");
		game.getBoard().renderBoard(dispMode);

		// Play the game
		while (!boardFull(game.getBoard()) && !isWinning(game.getBoard())) {
			if (game.getActivePlayer() == null){
				throw new RuntimeException("There is no player!");
			}
			System.out.println("Player " + game.getActivePlayerAsString() + ", it is your turn.");
			try {
				game.makeMove();
				game.getBoard().renderBoard(dispMode);
			} catch (InvalidIndexException e) {
				switch (e.getMessage()){
					case "x":
						actionGameOver();
						return;
					case "w":
						if (actionWithdraw())
							continue;
					case "i":
						printInstruction();
						break;
					default:
						// TODO fix this since Board also throws the exception which doesn't
						// return the command issued as the message
						// Could just give a generic response rather than returning the issued command
						System.out.println("Your input, [" + e.getMessage() + "] is not a valid command or move.");
				}
				continue;
			}
			game.toggleActivePlayer();
		}
		if (isWinning(game.getBoard())) {
			System.out.println("You won!");
		} else if (boardFull(game.getBoard())) {
			System.out.println("There are no more moves left. You both lose!");
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

	private static void actionGameOver() {
		System.out.println("Game Over!");
		reader.close();
	}

	private static void printInstruction() {
		// TODO fix the magic number
		System.out.println("Game instruction:\nEach player takes turn to place a stone on the board." +
				"\nYour goal is to place 5 consecutive stones in a row. The first one to do so wins!" +
				"\nTo place a stone, enter the letter and number corresponding to the column and row respectively." +
				"\nSeparate the two by a comma." +
				"\n  For example: A,1 or 3,B." +
				"\n\nYou are allowed to undo your last move up to 3 times." +
				"\nEnter \"w\" to withdraw when it is your turn." +
				"\nTo quit the game, enter \"x\". " +
				"\nTo see the instructions again, enter \"i\"\n");
	}

	public static boolean isWinning(Board board) {
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	public static boolean boardFull(Board board) {
		return board.boardFull();
	}

	private static int getGameMode() {
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:" +
				"\n (" + Game.MULTIPLAYER_GAME_MODE + ") Multi-player" +
				"\n (" + Game.SINGLEPLAYER_GAME_MODE + ") Single-player");
		String gameMode = reader.next();
		while (!gameMode.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			gameMode = reader.nextLine();
		}
		return Integer.parseInt(gameMode);
	}

	private static int getDisplayMode() {
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
		return Integer.parseInt(displayMode);
	}

//	private static void singlePlayerGameStart(Scanner reader, String inputStream, String dispMode) {
//		boolean isPlayer1 = true;
//		String dispStr;
//		while (!boardFull(game.getBoard()) && !isWinning(game.getBoard())) {
//			if (isPlayer1)
//				dispStr = "one";
//			else
//				dispStr = "two";
//
//			System.out.println("Player " + dispStr + ", it is your turn.");
//			inputStream = reader.next();
//			switch (inputStream){
//				case "x":
//					actionGameOver(reader);
//					return;
//				case "w":
//					try {
//						game.getPlayer1().withdraw();
//					} catch (WithdrawException e) {
//						System.out.println(e.getMessage());
//						continue;
//					}
//					break;
//				case "i":
//					printInstruction();
//					break;
//			}
//			if (!sanitiseAndPrint(inputStream, isPlayer1, dispMode))
//				continue;
//			isPlayer1 = !isPlayer1;
//		}
//		if (isWinning(game.getBoard())) {
//			System.out.println("You won!");
//		}
//	}

	private static int getDifficulty() {
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
		return Integer.parseInt(difficulty);
	}

	private static void multiPlayerGameStart() {


	}

}
