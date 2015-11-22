package renju.com.lmh.test;

import org.junit.Before;
import org.junit.Test;

import renju.com.lmh.application.Game;
import renju.com.lmh.application.SinglePlayer;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.exception.WithdrawException;

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
		game.makeMove();
		// Must enter a valid location in order
		// to let the test pass (BAD!!!).
		game.makeMove();
		assertEquals(game.getBoard().getTotalStones(), 2);
		assertEquals(game.getBoard().getPlayer1Stone().size(), 1);
		assertEquals(game.getBoard().getPlayer2Stone().size(), 1);
		game.withdraw();
		assertEquals(game.getBoard().getTotalStones(), 0);
	}
}
