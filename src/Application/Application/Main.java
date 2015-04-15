package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;

import java.io.IOException;
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
	private static int dispMode;
	private static boolean host;

	public static void main(String[] args) {
		// Get initializations
		reader = new Scanner(System.in);
		int mode = getGameMode();
		dispMode = getDisplayMode();
		game = Game.getInstance();
		printInstruction();

		// Initialize game mode
		switch (mode) {
			case Game.SINGLEPLAYER_GAME_MODE:
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
				break;
			case Game.MULTIPLAYER_GAME_MODE:
				game.initMultiplayer();
				break;
			case Game.NETWORK_GAME_MODE:
				host = getHost();
				try {
					NetworkGame g = new NetworkGame(host);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				break;
			default:
				System.err.println("Invalid game mode. Exiting");
				return;
		}

		System.out.println("The match is set as " + getModeAsString() + " in a " +
				Board.getWidth() + "x" + Board.getHeight() + " board as shown:");
		game.getBoard().renderBoard(dispMode);

		// Play the game
		if (mode == Game.NETWORK_GAME_MODE) {
			playNetwork();
		} else {
			playLocal();
		}
	}

	private static void playNetwork() {
	}

	private static void playLocal() {
		while (!Game.boardFull() && !Game.isWinning()) {
            if (game.getActivePlayer() == null) {
                throw new RuntimeException("There is no player!");
            }
            System.out.println("\nPlayer " + getActivePlayerAsString() + ", it is your turn.");
            try {
                game.makeMove();
                game.getBoard().renderBoard(dispMode);
            } catch (InvalidIndexException e) {
                switch (e.getMessage()) {
                    case "x":
                        actionGameOver();
                        return;
                    case "w":
                        try {
                            actionWithdraw();
                        } catch (WithdrawException e1) {
                            // Redo this turn since the player is out of withdrawals
                            continue;
                        }
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
		if (Game.isWinning()) {
            // get inactive player because the current player was toggled at the end of the round
            System.out.println("Player " + getInactivePlayerAsString() + ", You won!");
        } else if (Game.boardFull()) {
            System.out.println("There are no more moves left. You both lose!");
        }

		reader.close();
	}

	private static boolean getHost() {
		System.out.println(" Are you host or client?\n"
				+ " (" + NetworkGame.HOST + ") Host\n"
				+ " (" + NetworkGame.CLIENT+ ") Client\n");
		String host = reader.next();
		while (!host.equals(String.valueOf(NetworkGame.HOST)) &&
				!host.equals(String.valueOf(NetworkGame.CLIENT))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			host = reader.next();
		}
		return host.equals("1");
	}

	private static void actionWithdraw() throws WithdrawException {
		game.withdraw();
	}

	private static void actionGameOver() {
		System.out.println("Game Over!");
		reader.close();
	}

	private static void printInstruction() {
		// TODO fix the magic number
		System.out.println("Game instruction:\nEach player takes turn to place a stone on the board." +
				"\nYour goal is to place "+Board.NUM_STONES_TO_WIN+" consecutive stones in a row. " +
				"The first one to do so wins!" +
				"\nTo place a stone, enter the letter and number corresponding to the column and row respectively." +
				"\nSeparate the two by a comma." +
				"\n  For example: A,1 or 3,B." +
				"\n\nYou are allowed to undo your last move up to "+Player.NUM_REGRETS_LIMIT+" times." +
				"\nEnter \"w\" to withdraw when it is your turn." +
				"\nTo quit the game, enter \"x\". " +
				"\nTo see the instructions again, enter \"i\"\n");
	}

	private static int getGameMode() {
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:" +
				"\n (" + Game.MULTIPLAYER_GAME_MODE + ") Multi-player" +
				"\n (" + Game.SINGLEPLAYER_GAME_MODE + ") Single-player" +
				"\n (" + Game.NETWORK_GAME_MODE + ") Network");
		String gameMode = reader.next();
		while (!gameMode.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.NETWORK_GAME_MODE))) {
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

	/**
	 * Returns the active player as a string of either "one" if player 1 is active
	 * and "two" if player two is active
	 * @return
	 * 		"one" if player 1 is active.
	 * 		"two if player 2 is active.
	 */
	private static String getActivePlayerAsString(){
		if (game.isPlayer1Active()){
			return "one";
		} else return "two";
	}

	private static String getInactivePlayerAsString(){
		if(game.isPlayer1Active()){
			return "two";
		} else return "one";
	}

	/**
	 * Retusn the game mode as a string
	 *
	 * @return
	 * 		"single player" if the mode is SINGLEPLAYER_GAME_MODE
	 * 		"multi-player" if the mode is MULTIPLAYER_GAME_MODE
	 */
	private static String getModeAsString(){
		if (game.getMode()== Game.SINGLEPLAYER_GAME_MODE){
			return "single player";
		} else {
			return "multiplayer";
		}
	}
}