package AlgorithmTest;

import org.junit.Before;
import org.junit.Test;

import Model.Board;

public class BoardCheckerTest {
	private Board board;

	@Before
	public void initBoard() {
		this.board = new Board();
	}

	@Test
	public void testCheckDiscPattern() {
		board.updateBoard(new BoardLocation(2,2), true);
	}
}
