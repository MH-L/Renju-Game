package ModelTest;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Exceptions.InvalidIndexException;
import Model.Board;
import Model.BoardLocation;

/**
 * A class for testing both board and board location classes.
 * @author Minghao Liu
 * @Date 2015/4/9
 *
 */
public class BoardTest {
	private Board bd;

	@Before
	public void initBoard() {
		this.bd = new Board();
	}

	public void printAllDiags() {
		for (int i = 0; i < bd.getULDiags().size(); i++) {
			for (int j = 0; j < bd.getULDiagByIndex(i).length; j++) {
				System.out.print(bd.getULDiagByIndex(i)[j]);
			}
			System.out.println("");
		}

		for (int i = 0; i < bd.getURDiags().size(); i++) {
			for (int j = 0; j < bd.getURDiagByIndex(i).length; j++) {
				System.out.print(bd.getURDiagByIndex(i)[j]);
			}
			System.out.println("");
		}
	}

	public int sumAllDiags() {
		int count = 0;
		for (int i = 0; i < bd.getURDiags().size(); i++) {
			for (int j = 0; j < bd.getURDiagByIndex(i).length; j++) {
				if (bd.getURDiagByIndex(i)[j] != Board.EMPTY_SPOT)
					count ++;
			}
		}
		return count;
	}

	@Test
	public void testIsValid() {
		BoardLocation bdloc = new BoardLocation(16,16);
		assertFalse(Board.isReachable(bdloc));
		bdloc = new BoardLocation(0,0);
		assertTrue(Board.isReachable(bdloc));
		bdloc = new BoardLocation(-1,-1);
		assertFalse(Board.isReachable(bdloc));
		bdloc = new BoardLocation(1,-1);
		assertFalse(Board.isReachable(bdloc));
		bdloc = new BoardLocation(17,2);
		assertFalse(Board.isReachable(bdloc));
	}

	@Test
	public void testUpdate() throws InvalidIndexException {
		BoardLocation toPlace = new BoardLocation(0,0);
		BoardLocation player = new BoardLocation(1,0);
		bd.updateBoard(player, true);
		bd.updateBoard(toPlace, false);
		bd.updateBoard(new BoardLocation(7,7), false);

		assertEquals(bd.getGrids()[0][0], 2);
		assertEquals(bd.getGrids()[1][0], 1);
		assertEquals(bd.getGridVal(new BoardLocation(0,0)), 2);
		assertEquals(bd.getGridVal(new BoardLocation(1,0)), 1);
		assertFalse(bd.updateBoard(player, false));
		assertEquals(bd.getGrids()[1][0], 1);
		assertEquals(bd.getULDiags().get(15)[0], 2);
		assertEquals(bd.getULDiags().get(16)[0], 1);
		assertEquals(bd.getURDiags().get(0)[0], 2);
		assertEquals(bd.getURDiags().get(1)[1], 1);
		assertEquals(3, bd.getTotalStones());
		assertEquals(bd.getULDiagByIndex(15)[7], 2);
		assertEquals(bd.getURDiagByIndex(14)[7], 2);
	}

	@Test
	public void testConstructor() {
		int[][] grids = bd.getGrids();
		assertEquals(16, grids.length);
		assertEquals(16, grids[0].length);
		ArrayList<int[]> rows = bd.getRows();
		assertEquals(16, rows.size());
		ArrayList<int[]> cols = bd.getColumns();
		assertEquals(16, cols.size());
		ArrayList<int[]> diags = bd.getULDiags();
		assertEquals(31, diags.size());
		assertEquals(16, diags.get(15).length);
		assertEquals(14, diags.get(17).length);
		assertEquals(12, diags.get(11).length);
		diags = bd.getURDiags();
		assertEquals(31, diags.size());
	}

	@Test
	public void testCheckRows() throws InvalidIndexException {
		bd.updateBoard(new BoardLocation(7,9), false);
		bd.updateBoard(new BoardLocation(7,8), false);
		bd.updateBoard(new BoardLocation(7,10), false);
		bd.updateBoard(new BoardLocation(7,11), false);
		bd.updateBoard(new BoardLocation(7,12), false);
		assertTrue(bd.checkrow());
		assertFalse(bd.checkcol());
		assertFalse(bd.checkdiag());
	}

	@Test
	public void testCheckCols() throws InvalidIndexException {
		bd.updateBoard(new BoardLocation(7,9), false);
		bd.updateBoard(new BoardLocation(8,9), false);
		bd.updateBoard(new BoardLocation(9,9), false);
		bd.updateBoard(new BoardLocation(10,9), false);
		bd.updateBoard(new BoardLocation(11,9), false);
		assertTrue(bd.checkcol());
		assertFalse(bd.checkrow());
		assertFalse(bd.checkdiag());
	}

	@Test
	public void testCheckDiags() throws InvalidIndexException {
		assertTrue(bd.updateBoard(new BoardLocation(7,9), false));
		assertTrue(bd.updateBoard(new BoardLocation(6,8), false));
		assertTrue(bd.updateBoard(new BoardLocation(8,10), false));
		assertTrue(bd.updateBoard(new BoardLocation(9,11), false));
		assertFalse(bd.checkdiag());
		bd.updateBoard(new BoardLocation(10,12), false);
		assertTrue(bd.checkdiag());
		assertFalse(bd.checkcol());
		assertFalse(bd.checkrow());
		bd.withdrawMove(new BoardLocation(8,10));
		System.out.println(bd.getPlayer1Stone().size());
		System.out.println(bd.getPlayer2Stone().size());
		assertEquals(bd.getTotalStones(), 4);
		assertFalse(bd.checkdiag());
		assertTrue(bd.updateBoard(new BoardLocation(8,10), false));
		assertEquals(bd.getTotalStones(), 5);
		assertTrue(bd.checkdiag());

	}

	@Test
	public void testWithdrawAndReset() throws InvalidIndexException {
		bd.updateBoard(new BoardLocation(2,0), true);
		bd.updateBoard(new BoardLocation(2,1), true);
		assertEquals(bd.getGridVal(new BoardLocation(2,0)), 1);
		bd.withdrawMove(new BoardLocation(2,0));
		assertEquals(bd.getGridVal(new BoardLocation(2,0)), 0);
		bd.reset();
		assertTrue(bd.isEmpty());
		assertEquals(bd.getTotalStones(), 0);
	}
}
