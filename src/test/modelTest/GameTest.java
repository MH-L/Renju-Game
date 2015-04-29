package test.modelTest;

import application.game.Game;
import application.game.SinglePlayer;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;

	@Before
	public void init() {
		game = new SinglePlayer(Game.Difficulty.NOVICE, true);
	}

	@Test
	public void testWithdraw() throws InvalidIndexException, WithdrawException {
//		assertTrue(game.getBoard().isEmpty());
//		game.makeMove(game.getActivePlayer(), game.getActivePlayer().makeMove());
//		// Must enter a valid location in order
//		// to let the test pass (BAD!!!).
//		game.makeMove(game.getActivePlayer(), game.getActivePlayer().makeMove());
//		assertEquals(game.getBoard().getTotalStones(), 2);
//		assertEquals(game.getBoard().getPlayer1Stone().size(), 1);
//		assertEquals(game.getBoard().getPlayer2Stone().size(), 1);
//		game.withdraw();
//		assertEquals(game.getBoard().getTotalStones(), 0);
	}
}
