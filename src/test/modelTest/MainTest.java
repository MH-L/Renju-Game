package test.modelTest;

import static application.Game.boardFull;
import static application.Game.isWinning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import application.AI;
import application.AiVersusAi;
import application.Game;
import application.Main;
import application.Game.Difficulty;

public class MainTest {
	// To ensure the game runs normally.
	private Game game;

	@Before
	public void init() {
		Main main = new Main();
	}

	@Test
	public void testNormalRunning() {
		PrintWriter writer;
		PrintWriter logger;
		int blackWinCount = 0;
		int whiteWinCount = 0;
		int boardFullCount = 0;
		int totalMove = 0;
		try {
			writer = new PrintWriter("C:/Users/Minghao/Desktop/miscellaneous/Stats.txt",
					"UTF-8");
			writer.println("The first line");
			writer.println("The second line");

			for (int i = 0; i < 100; i++) {
				logger = new PrintWriter("C:/Users/Minghao/Desktop/miscellaneous/Logs.txt", "UTF-8");
				logger.println("The current iteration count is " + i);
				logger.println("The current stats is:");
				logger.println("Black win count: " + blackWinCount);
				logger.println("White win count: " + whiteWinCount);
				logger.println("Board full count: " + boardFullCount);
				logger.println("Total moves count: " + totalMove);
				logger.println("Please wait with patience.");
				logger.close();
				game = new AiVersusAi(Game.Difficulty.INTERMEDIATE,
						Game.Difficulty.INTERMEDIATE);
				while (!isWinning() && !boardFull()) {
					try {
						game.makeMove();
						totalMove ++;
					} catch (InvalidIndexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					game.getBoard().renderBoard(2);
				}
				if (isWinning()) {
					String writeString;
					if (game.isPlayer1Active()) {
						writeString = "White Wins.";
						whiteWinCount++;
					}
					else {
						writeString = "Black Wins.";
						blackWinCount++;
					}
					writer.println(writeString);
					System.out.println("Player " + ", You won!");
				} else if (boardFull()) {
					writer.println("Board Full.");
					boardFullCount ++;
					System.out.println("There are no more moves left. You both came to a draw!");
				}
			}
			writer.println("The black win count is: " + blackWinCount);
			writer.println("The white win count is: " + whiteWinCount);
			writer.println("The board full count is: " + boardFullCount);
			writer.println("The total number of moves is: " + totalMove);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

	}

	public void runIteratively(int times, String statsFileLoc, String logFileLoc, String encoding, Scanner reader) {
		assert (times > 0);
		Difficulty player1Diff = getDifficulties(reader);
		Difficulty player2Diff = getDifficulties(reader);
		PrintWriter writer;
		PrintWriter logger;
		int blackWinCount = 0;
		int whiteWinCount = 0;
		int boardFullCount = 0;
		int totalMove = 0;
		try {
			writer = new PrintWriter(statsFileLoc, encoding);
			writer.println("The first line");
			writer.println("The second line");

			for (int i = 0; i < times; i++) {
				logger = new PrintWriter(logFileLoc, encoding);
				logger.println("The current iteration count is " + i);
				logger.println("The current stats is:");
				logger.println("Black win count: " + blackWinCount);
				logger.println("White win count: " + whiteWinCount);
				logger.println("Board full count: " + boardFullCount);
				logger.println("Total moves count: " + totalMove);
				logger.println("Please wait with patience.");
				logger.close();
				game = new AiVersusAi(player1Diff, player2Diff);
				while (!isWinning() && !boardFull()) {
					try {
						game.makeMove();
						totalMove ++;
					} catch (InvalidIndexException e) {

					}
					game.getBoard().renderBoard(2);
				}
				if (isWinning()) {
					String writeString;
					if (game.isPlayer1Active()) {
						writeString = "White Wins.";
						whiteWinCount++;
					}
					else {
						writeString = "Black Wins.";
						blackWinCount++;
					}
					writer.println(writeString);
					System.out.println("Player " + ", You won!");
				} else if (boardFull()) {
					writer.println("Board Full.");
					boardFullCount ++;
					System.out.println("There are no more moves left. You both came to a draw!");
				}
			}
			writer.println("The black win count is: " + blackWinCount);
			writer.println("The white win count is: " + whiteWinCount);
			writer.println("The board full count is: " + boardFullCount);
			writer.println("The total number of moves is: " + totalMove);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
	}

	public static Difficulty getDifficulties(Scanner reader) {
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
					System.err.println("Invalid option. Please try again.");
			} catch (NumberFormatException e) {
				System.err.println("Invalid option. Please try again.");
			}
		}
		return Difficulty.values()[selection-1];
	}
}
