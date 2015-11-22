package renju.com.lmh.test;

import static renju.com.lmh.application.Game.boardFull;
import static renju.com.lmh.application.Game.isWinning;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.junit.Test;

import renju.com.lmh.application.AiVersusAi;
import renju.com.lmh.application.Game;
import renju.com.lmh.application.Game.Difficulty;
import renju.com.lmh.exception.InvalidIndexException;

public class MainTest {
	// To ensure the game runs normally.
	private Game game;

	@Test
	public void testNormalRunning() {
		PrintWriter writer;
		PrintWriter logger;
		int blackWinCount = 0;
		int whiteWinCount = 0;
		int boardFullCount = 0;
		int totalMove = 0;
		long iterationTime = 0;
		try {
			writer = new PrintWriter("C:/Users/Minghao/Desktop/miscellaneous/Stats.txt",
					"UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			long currentTime = System.nanoTime();
			for (int i = 0; i < 20000; i++) {
				long iterationStartTime = System.nanoTime();
				long timeElapsed = System.nanoTime() - currentTime;
				logger = new PrintWriter("C:/Users/Minghao/Desktop/miscellaneous/Logs.txt", "UTF-8");
				logger.println("Total time elapsed from the start of the iteration: " +
						timeElapsed);
				logger.println("The time it took for the last iteration to run:" + iterationTime);
				logger.println("The current iteration count is " + i);
				logger.println("The current stats is:");
				logger.println("Black win count: " + blackWinCount);
				logger.println("White win count: " + whiteWinCount);
				logger.println("Board full count: " + boardFullCount);
				logger.println("Total moves count: " + totalMove);
				logger.println("Please wait with patience.");
				logger.close();
				game = new AiVersusAi(Game.Difficulty.INTERMEDIATE,
						Game.Difficulty.ADVANCED);
				while (!isWinning() && !boardFull()) {
					try {
						game.makeMove();
						totalMove ++;
//						try {
//							Thread.sleep(700);
//						} catch (InterruptedException e) {
//							continue;
//						}
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
				iterationTime = System.nanoTime() - iterationStartTime;
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
