package application;

import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import model.Board;

import java.util.Scanner;
/**
 * The start point of the application.
 * @author Minghao Liu
 * @author Kelvin Yip
 *
 */
public class Main {
	private static Game game;
	private static Scanner reader;
	private static int dispMode;
	private static boolean host;

	public static void main(String[] args) {
		// Get initializations
		printHeader();
		reader = new Scanner(System.in);
		int mode = getGameMode();
		dispMode = getDisplayMode();
		printInstruction();

		// Initialize game mode
		switch (mode) {
			case Game.SINGLEPLAYER_GAME_MODE:
				int diff = getDifficulty();
				if (isValidDifficulty(diff)){
					// This would be better if initSinglePlayer handled this input
					// but creating a new exception might be overkill for something
					// that likely isn't going to change
					boolean playerFirst = getIfFirst();
					game = new SinglePlayer(diff, playerFirst);
				} else {
					System.err.println("Internal Error!");
					return;
				}
				break;
			case Game.MULTIPLAYER_GAME_MODE:
				game = new MultiPlayer();
				break;
			case Game.NETWORK_GAME_MODE:
				host = getHost();
				game = new Network(host);
				break;
			case Game.AI_VERSUS_AI_GAME_MODE:
				int diff1 = getDifficulty();
				int diff2 = getDifficulty();
				if (isValidDifficulty(diff1) && isValidDifficulty(diff2)){
					game = new AiVersusAi(diff1, diff2);
				} else {
					System.err.println("Internal Error!");
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
		} else if (mode == Game.AI_VERSUS_AI_GAME_MODE) {
			playAiVAi();
		}
		else {
			playLocal();
		}
	}

	private static void playAiVAi() {
		if (game.getActivePlayer() == null) {
			throw new RuntimeException("There is no player!");
		}
		while (!Game.isWinning() && !Game.boardFull()) {
			System.out.println("Computer " + (game.isPlayer1Active() ? "one" : "two")+ ", it is your turn.");
			try {
				game.makeMove();
				game.getBoard().renderBoard(dispMode);
				Thread.sleep(1000);
			} catch (InvalidIndexException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (Game.isWinning()) {
			// get inactive player because the current player was toggled at the
			// end of the round
			// if (game.getBoard().getTotalStones() <= 8)
			// System.err.println("Fuck! This is not even possible!");
			System.out.println("Player " + getInactivePlayerAsString()
					+ ", You won!");
		} else if (Game.boardFull()) {
			System.out
					.println("There are no more moves left. You both came to a draw!");
		}

		reader.close();
	}

	private static boolean isValidDifficulty(int diff) {
		return Game.NOVICE_DIFFICULTY <= diff && diff <= Game.ULTIMATE_DIFFICULTY;
	}

	private static void playNetwork() {
	}

	private static void playLocal() {
		if (game.getActivePlayer() == null) {
			throw new RuntimeException("There is no player!");
		}
		while (!Game.isWinning() && !Game.boardFull()) {
            System.out.println(getActivePlayerID() + ", it is your turn.");
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
							System.out.format(
									"You have %d withdrawals left.\n",
									((Player) game.getActivePlayer())
											.getRegrets());
							System.out.println("Now the board is shown below.");
							game.getBoard().renderBoard(dispMode);
                        } catch (WithdrawException e1) {
                            // Redo this turn since the player is out of withdrawals
                            continue;
                        } catch (InvalidIndexException e1) {
							e1.printStackTrace();
							System.out.println(e1.getMessage());
							continue;
						}
						break;
					case "i":
                        printInstruction();
                        break;
                    default:
                        // TODO fix this since Board also throws the exception which doesn't
                        // return the command issued as the message
                        // Could just give a generic response rather than returning the issued command
                        System.out.println("Your input, [" + e.getMessage() + "] is not a valid command or move.");
                }
            }
        }
		if (Game.isWinning()) {
			// get inactive player because the current player was toggled at the
			// end of the round
			// if (game.getBoard().getTotalStones() <= 8)
			// System.err.println("Fuck! This is not even possible!");
			System.out.println("Player " + getInactivePlayerAsString()
					+ ", You won!");
		} else if (Game.boardFull()) {
			System.out
					.println("There are no more moves left. You both came to a draw!");
		}

		reader.close();
	}

	private static void printHeader() {
		System.out.println("Renju Game. First Release. 2015/4/18.");
		System.out.println("Created By: Minghao Liu. Refactor: Kelvin Yip.");
	}

	private static void actionWithdraw() throws WithdrawException,
			InvalidIndexException {
		game.withdraw();
	}

	private static boolean getHost() {
		System.out.println(" Are you host or client?\n"
				+ " (" + Network.HOST + ") Host\n"
				+ " (" + Network.CLIENT + ") Client\n");
		String host = reader.next();
		while (!host.equals(String.valueOf(Network.HOST)) &&
				!host.equals(String.valueOf(Network.CLIENT))) {
			System.out.println("Invalid input. Please re-enter your choice.");
			host = reader.next();
		}
		return host.equals("1");
	}


	private static void actionGameOver() {
		System.out.println("Game Over!");
		reader.close();
	}

	private static void printInstruction() {
		// TODO fix the magic number
		System.out
				.println("Game instruction:\nEach player takes turn to place a stone on the board."
						+ "\nYour goal is to place "
						+ Board.NUM_STONES_TO_WIN
						+ " consecutive stones in a row. "
						+ "The first one to do so wins!"
						+ "\nTo place a stone, enter the letter and number corresponding to the column and row respectively."
						+ "\nSeparate the two by a comma."
						+ "\n  For example: A,1 or 3,B are all valid inputs."
						+ "\nHowever, inputs such as \"13,12\", \"A,G\", \"B\""
						+ "\nare not valid ones."
						+ "\n\nYou are allowed to undo your last move up to "
						+ Player.NUM_REGRETS_LIMIT
						+ " times."
						+ "\nEnter \"w\" to withdraw when it is your turn."
						+ "\nTo quit the game, enter \"x\". "
						+ "\nTo see the instructions again, enter \"i\"\n");
	}

	private static int getGameMode() {
		System.out.println(" Welcome to the Renju Game!\n Select a game mode:" +
				"\n (" + Game.MULTIPLAYER_GAME_MODE + ") Multi-player" +
				"\n (" + Game.SINGLEPLAYER_GAME_MODE + ") Single-player" +
				"\n (" + Game.NETWORK_GAME_MODE + ") Network" +
				"\n (" + Game.AI_VERSUS_AI_GAME_MODE + ") AI v AI" );
		String gameMode = reader.next();
		while (!gameMode.equals(String.valueOf(Game.MULTIPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.SINGLEPLAYER_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.NETWORK_GAME_MODE)) &&
				!gameMode.equals(String.valueOf(Game.AI_VERSUS_AI_GAME_MODE))) {
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
		// TODO change here in future releases. since now we cannot set up more
		// difficult versions.
		System.out.println(" Please enter the AI difficulty:\n"
				+ " (" + Game.NOVICE_DIFFICULTY + ") Novice\n"
				+ " (" + Game.INTERMEDIATE_DIFFICULTY + ") Intermediate (coming soon)\n"
				+ " (" + Game.ADVANCED_DIFFICULTY + ") Advanced (coming soon)\n"
				+ " (" + Game.ULTIMATE_DIFFICULTY + ") Ultimate (coming soon)\n");
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
	 * If the active player is a Player, then return their user ID.
	 * Else, return the string "Computer".
	 * @return
	 * 		The active player's user ID
	 */
	private static String getActivePlayerID(){
		if (game.getActivePlayer() instanceof Player)
			return ((Player) game.getActivePlayer()).getId();
		else return "Computer";
	}

	private static String getInactivePlayerAsString(){
		if(game.isPlayer1Active()){
			return "two";
		} else return "one";
	}

	/**
	 * Return the game mode as a string
	 *
	 * @return
	 * 		"single player" if the mode is SINGLEPLAYER_GAME_MODE
	 * 		"multi-player" if the mode is MULTIPLAYER_GAME_MODE
	 */
	private static String getModeAsString(){
		if (game instanceof SinglePlayer){
			return "single player";
		} else {
			return "multiplayer";
		}
	}

	private static boolean getIfFirst() {
		System.out.println("Do you want to make the first move?\n"
				+ "(1) Yes\n(2) No");
		String input = reader.next();
		while (!input.equals("1") && !input.equals("2")) {
			System.out
					.println("The choice you entered is invalid. Please re-enter.");
			input = reader.nextLine();
		}
		return Integer.parseInt(input) == 1;
	}
}