package algorithm;

import java.util.ArrayList;

import utils.DeepCopy;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class BoardTree {
	private int turn;
	private ArrayList<BoardTree> children;
	private Board node;
	private BoardLocation lastMove = null;
	private int score;
	public static final int MAX_SCORE = 5000;
	public static final int MIN_SCORE = -5000;
	public static final int SCORE_OPEN_FOUR = 1000;
	public static final int SCORE_CONNECT_FOUR = 3;
	public static final int SCORE_OPEN_THREE = 2;
	public static final int SCORE_OPEN_TWO = 1;
	public static final int SCORE_THREE_THREE = 300;
	public static final int SCORE_FOUR_FOUR = 3000;
	public static final int SCORE_THREE_FOUR = 2000;

	public BoardTree(Board board, int turn) {
		this.turn = turn;
		// TODO this needs refining.
		this.score = 0;
		this.node = board;
		this.children = new ArrayList<BoardTree>();
	}

	/**
	 * Constructor only for adding board to the children array of
	 * an existing board.
	 * @param board
	 * @param lastMove
	 * @param turn
	 */
	private BoardTree(Board board, BoardLocation lastMove, int turn) {
		this.turn = turn;
		// TODO this needs refining.
		this.score = 0;
		this.node = board;
		this.children = new ArrayList<BoardTree>();
		this.lastMove = lastMove;
	}

	public void appendChild(BoardTree tree) {
		if (children == null)
			children = new ArrayList<BoardTree>();
		children.add(tree);
	}

	public int getDepth() {
		if (children == null)
			return 0;
		int childrenDepth = getDepthHelper(children);
		return childrenDepth == 0 ? 0 : 1 + childrenDepth;
	}

	private int getDepthHelper(ArrayList<BoardTree> arrayTrees) {
		if (arrayTrees.isEmpty())
			return 0;
		int maxDepth = 0;
		for (int i = 0; i < arrayTrees.size(); i++) {
			if (arrayTrees.get(i).getDepth() > maxDepth)
				maxDepth = arrayTrees.get(i).getDepth();
		}
		return maxDepth;
	}

	public Board getNode() {
		return node;
	}

	public BoardLocation getLastMove() {
		return lastMove;
	}

	public int getScore() {
		return this.score;
	}

	public BoardTree getMinMax() {
		int maxIndex = 0;
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getScore() > children.get(maxIndex).getScore()) {
//				maxIndex = i
			}
		}
		return children.get(maxIndex);
	}

	public static int evalBoard(Board board, int turn) {
		Algorithm alg = new BasicAlgorithm(board, turn == Board.TURN_SENTE);
		ArrayList<BoardLocation> feasibleMoves = alg.generateFeasibleMoves();
		int sum = 0;
		int otherSum = 0;
		if (board.checkcol() || board.checkdiag() || board.checkrow()) {
			return MAX_SCORE;
		}
		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_SENTE, 4).size();
		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_SENTE, 5).size();
		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_SENTE, 6).size();

		sum += SCORE_OPEN_THREE * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_SENTE, 3).size();
		sum += SCORE_OPEN_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_SENTE, 4).size();
		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_SENTE, 3).size();
		sum += SCORE_OPEN_TWO * BoardChecker.checkAllSubPatterns
				(board, turn == Board.TURN_SENTE).size();

		otherSum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_GOTE, 4).size();
		otherSum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_GOTE, 5).size();
		otherSum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_GOTE, 6).size();

		otherSum += SCORE_OPEN_THREE * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 3).size();
		otherSum += SCORE_OPEN_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 4).size();
		otherSum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 3).size();
		otherSum += SCORE_OPEN_TWO * BoardChecker.checkAllSubPatterns
				(board, turn == Board.TURN_GOTE).size();
		return (int) (sum - otherSum * 0.8);
	}

	public int evalRoot() {
		return evalBoard(this.node, this.turn);
	}

	public void addChild(BoardLocation loc) {
		// TODO maybe just not copy the board?
		VirtualBoard vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.node));
		int childTurn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE : Board.TURN_SENTE;
		this.children.add(new BoardTree(vBoard, loc, childTurn));
	}
}
