package application;

import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import model.Board;

import java.util.Scanner;

import static application.Game.*;

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
		Mode mode = getGameMode();
		dispMode = getDisplayMode();
		printInstruction();

		// Initialize game mode
		switch (mode) {
			case SINGLEPLAYER:
				Difficulty diff = getDifficulty();
				boolean playerFirst = getIfFirst();
				game = new SinglePlayer(diff, playerFirst);
				break;
			case MULTIPLAYER:
				game = new MultiPlayer();
				break;
			case NETWORK:
				host = getHost();
				game = new Network(host);
				break;
			case AIVERSUSAI:
				Difficulty diff1 = getDifficulty();
				Difficulty diff2 = getDifficulty();
				game = new AiVersusAi(diff1, diff2);
				break;
			default:
				System.err.println("Invalid game mode. Exiting");
				return;
		}

		System.out.println("The match is set as " + mode.toString() + " in a " +
				Board.getWidth() + "x" + Board.getHeight() + " board as shown:");
		game.getBoard().renderBoard(dispMode);

		// Play the game
		if (mode == Mode.NETWORK) {
			playNetwork();
		} else if (mode == Mode.AIVERSUSAI) {
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
		while (!isWinning() && !boardFull()) {
			System.out.println("\nPlayer " + getActivePlayerAsString() + ", it is your turn.");
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
		if (isWinning()) {
			System.out.println("Player " + getInactivePlayerAsString()
					+ ", You won!");
		} else if (boardFull()) {
			System.out.println("There are no more moves left. You both came to a draw!");
		}

		reader.close();
	}

	private static void playNetwork() {
	}

	private static void playLocal() {
		if (game.getActivePlayer() == null) {
			throw new RuntimeException("There is no player!");
		}
		while (!isWinning() && !boardFull()) {
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
		if (isWinning()) {
			// get inactive player because the current player was toggled at the
			// end of the round
			// if (game.getBoard().getTotalStones() <= 8)
			// System.err.println("Fuck! This is not even possible!");
			System.out.println("Player " + getInactivePlayerAsString()
					+ ", You won!");
		} else if (boardFull()) {
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

	private static Mode getGameMode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Select a game mode:\n");
		for (Mode m : Mode.values()) {
			sb.append(" (").append(m.ordinal()+1).append(") ").append(m.toString()).append("\n");
		}
		System.out.println(sb.toString());
		int selection = 0;
		while (selection < 1 || selection > Mode.values().length) {
			try{
				selection = Integer.parseInt(reader.next());
				if (selection < 1 || selection > Difficulty.values().length)
					System.err.println("Invalid option. Please try again");
			} catch (NumberFormatException e) {
				System.err.println("Invalid option. Please try again");
			}
		}
		return Mode.values()[selection-1];
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

	private static Difficulty getDifficulty() {
		StringBuilder sb = new StringBuilder();
		sb.append("Please select the game difficulty:\n");
		for (Difficulty d : Difficulty.values()) {
			sb.append(" (").append(d.ordinal()+1).append(") ").append(d.toString()).append("\n");
		}
		System.out.println(sb.toString());
		int selection = 0;
		while (selection < 1 || selection > Difficulty.values().length) {
			try{
				selection = Integer.parseInt(reader.next());
				if (selection < 1 || selection > Difficulty.values().length)
					System.err.println("Invalid option. Please try again");
			} catch (NumberFormatException e) {
				System.err.println("Invalid option. Please try again");
			}
		}
		return Difficulty.values()[selection-1];
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

	private static boolean getIfFirst() {
		System.out.println("Do you want to make move first?\n"
				+ "(1) Yes\n(2) No");
		String input = reader.nextLine();
		while (!input.equals("1") && !input.equals("2")) {
			System.out
					.println("The choice you entered is invalid. Please re-enter.");
			input = reader.nextLine();
		}
		return Integer.parseInt(input) == 1;
	}
}