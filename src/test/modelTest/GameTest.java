package test.modelTest;

import static org.junit.Assert.*;
import model.BoardLocation;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import application.Game;

public class GameTest {
	private Game game;

	@Before
	public void init() {
		game = Game.getInstance();
		game.initSinglePlayer(Game.NOVICE_DIFFICULTY);
		game.setFirst(true);
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
		BoardLocation AILM = game.getInactivePlayer().getLastMove();
		assertEquals(game.getBoard().getTotalStones(), 0);
	}
}
