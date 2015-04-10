package ModelTest;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

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

	@Test
	public void testIsValid() {
		BoardLocation bdloc = new BoardLocation(16,16);
		assertFalse(bdloc.isReachable());
		bdloc = new BoardLocation(0,0);
		assertTrue(bdloc.isReachable());
		bdloc = new BoardLocation(-1,-1);
		assertFalse(bdloc.isReachable());
		bdloc = new BoardLocation(1,-1);
		assertFalse(bdloc.isReachable());
		bdloc = new BoardLocation(17,2);
		assertFalse(bdloc.isReachable());
	}

	@Test
	public void testUpdate() {
		BoardLocation toPlace = new BoardLocation(0,0);
		BoardLocation player = new BoardLocation(1,0);
		bd.updateBoard(player, true);
		bd.updateBoard(toPlace, false);
		assertEquals(bd.getGrids()[0][0], 2);
		assertEquals(bd.getGrids()[1][0], 1);
		assertEquals(bd.getGridVal(new BoardLocation(0,0)), 2);
		assertEquals(bd.getGridVal(new BoardLocation(1,0)), 1);
		assertEquals(bd.getLocations().get(0).get(0).getValue(), 2);
		assertEquals(bd.getLocations().get(1).get(0).getValue(), 1);
		bd.updateBoard(player, false);
		assertEquals(bd.getGrids()[1][0], 1);
		assertEquals(bd.getULDiags().get(15)[0], 2);
		assertEquals(bd.getULDiags().get(16)[0], 1);
		assertEquals(bd.getURDiags().get(0)[0], 2);
		assertEquals(bd.getURDiags().get(1)[1], 1);
		assertEquals(2, bd.getTotalStones());
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
	public void testCheckRows() {
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
	public void testCheckCols() {
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
	public void testCheckDiags() {
		bd.updateBoard(new BoardLocation(7,9), false);
		bd.updateBoard(new BoardLocation(6,8), false);
		bd.updateBoard(new BoardLocation(8,10), false);
		bd.updateBoard(new BoardLocation(9,11), false);
		bd.updateBoard(new BoardLocation(10,12), false);
		assertTrue(bd.checkdiag());
		assertFalse(bd.checkcol());
		assertFalse(bd.checkrow());
	}

	@Test
	public void testWithdrawAndReset() {
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
