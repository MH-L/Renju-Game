package com.lmh.test.algorithmTest;

import org.junit.Before;
import org.junit.Test;

import renju.com.lmh.algorithm.BoardTree;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;

public class BoardTreeTest {
	private Board testBoard;
	@Before
	public void initialize() throws InvalidIndexException {
		this.testBoard = new Board();
//		testBoard.updateBoard(new BoardLocation(7, 7), true);
//		testBoard.updateBoard(new BoardLocation(8, 7), false);
//		testBoard.updateBoard(new BoardLocation(8, 8), true);
//		testBoard.updateBoard(new BoardLocation(6, 6), false);
//		testBoard.updateBoard(new BoardLocation(6, 8), true);
//		testBoard.updateBoard(new BoardLocation(5, 9), false);
		testBoard.updateBoardSolitaire(new BoardLocation(7, 8), true);
		testBoard.updateBoardSolitaire(new BoardLocation(8, 7), false);
		testBoard.updateBoardSolitaire(new BoardLocation(8, 6), true);
		testBoard.updateBoardSolitaire(new BoardLocation(6, 8), false);
		testBoard.updateBoardSolitaire(new BoardLocation(6, 6), true);
		testBoard.updateBoardSolitaire(new BoardLocation(9, 8), false);
		testBoard.updateBoardSolitaire(new BoardLocation(5, 8), true);
		testBoard.updateBoardSolitaire(new BoardLocation(7, 9), false);
		testBoard.renderBoard(Board.FANCY_MODE);
		System.out.println(testBoard.getSecondPattern().size());
	}

	@Test
	public void testAnalyseMoves() {
		BoardTree tree = new BoardTree(testBoard, Board.TURN_SENTE);
		BoardLocation loc = tree.getBestMove(4);
		System.out.println(String.format("The xcoordinate is %d, and the y is %d.",
				loc.getXPos(), loc.getYPos()));
	}

	@Test
	public void testEvaluateBoard() {
		double score = BoardTree.evalBoard(testBoard, Board.TURN_SENTE);
		System.out.println("The score is: " + score);
	}
}
