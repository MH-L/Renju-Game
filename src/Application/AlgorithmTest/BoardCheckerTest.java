package AlgorithmTest;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import Algorithm.Pattern;
import Exceptions.InvalidIndexException;
import Model.Board;
import Model.BoardLocation;

public class BoardCheckerTest {
	private Board board;

	@Before
	public void initBoard() {
		this.board = new Board();
	}

	@Test
	public void testCheckDiscPattern() throws InvalidIndexException {
		board.updateBoard(new BoardLocation(2, 2), true);
		board.updateBoard(new BoardLocation(2, 3), true);
		board.updateBoard(new BoardLocation(2, 5), true);
		assertEquals(
				Algorithm.BoardChecker.checkBoardOpenPatDisc(board, true, 3)
						.size(), 0);
		assertEquals(
				Algorithm.BoardChecker.checkBoardOpenPatCont(board, true, 3)
						.size(), 0);
		assertEquals(Algorithm.BoardChecker.checkOpenPatDisc(
				board.getRowByIndex(2), 2, Pattern.ON_ROW, true, 3, board), 1);

	}
}
