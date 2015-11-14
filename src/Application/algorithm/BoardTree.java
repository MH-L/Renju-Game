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
		return 0;
	}

	public int evalRoot() {
		return evalBoard(this.node, this.turn);
	}

	public void addChild(int xcoord, int ycoord) {
		VirtualBoard vBoard = (VirtualBoard) DeepCopy.copy(this.node);
		BoardLocation parentToChild = new BoardLocation(ycoord, xcoord);
		int childTurn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE : Board.TURN_SENTE;
		this.children.add(new BoardTree(vBoard, parentToChild, childTurn));
	}
}
