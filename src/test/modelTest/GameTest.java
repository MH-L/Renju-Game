package test.modelTest;

import application.command.Move;
import application.command.Withdraw;
import application.game.Game;
import application.game.SinglePlayer;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import model.Board;
import model.BoardLocation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
	private Game game;

	@Before
	public void init() {
		game = new SinglePlayer(Game.Difficulty.NOVICE, true);
	}

	@Test
	public void testWithdraw() throws InvalidIndexException, WithdrawException {
		assertTrue(game.getBoard().isEmpty());
		game.doCommand(new Move(game.getPlayer1(), new BoardLocation(0, 0)));
		assertEquals(game.getBoard().getTotalStones(), 1);
		game.doCommand(new Move(game.getPlayer2(), new BoardLocation(1, 0)));
		assertEquals(game.getBoard().getTotalStones(), 2);
		assertEquals(game.getBoard().getPlayer1Stone().size(), 1);
		assertEquals(game.getBoard().getPlayer2Stone().size(), 1);
		game.getBoard().renderBoard(Board.CLASSIC_MODE);
		// Not player 2's turn so nothing should happen.
		game.doCommand(new Withdraw(game.getPlayer2()));
		assertEquals(game.getBoard().getTotalStones(), 2);
		assertEquals(game.getPlayerRegrets(game.getPlayer2()), Game.NUM_REGRETS_LIMIT);
		game.doCommand(new Withdraw(game.getPlayer1()));
		assertEquals(game.getBoard().getTotalStones(), 0);
		assertEquals(game.getPlayerRegrets(game.getPlayer2()), Game.NUM_REGRETS_LIMIT);
		assertEquals(game.getPlayerRegrets(game.getPlayer1()), Game.NUM_REGRETS_LIMIT-1);
		game.getBoard().renderBoard(Board.CLASSIC_MODE);
	}
}
