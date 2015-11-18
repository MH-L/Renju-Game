package algorithm;

import java.util.ArrayList;

import exceptions.InvalidIndexException;
import utils.DeepCopy;
import model.Board;
import model.BoardLocation;
import model.VirtualBoard;

public class BoardTree {
//	private static int threadCount = 0;
	private static int nodeCount = 0;
	private int turn;
	private ArrayList<BoardTree> children;
	private Board node;
	private BoardLocation lastMove = null;
	private int score;
	private boolean applicable = false;
	private Algorithm alg;
	/**
	 * Scores for various kind of patterns.
	 */
	public static final double MAX_SCORE = 5000;
	public static final double MIN_SCORE = -5000;
	public static final double SCORE_OPEN_FOUR = 1000;
	public static final double SCORE_CONNECT_FOUR = 3;
	public static final double SCORE_OPEN_THREE = 2.5;
	public static final double SCORE_CONNECT_THREE = 0.8;
	public static final double SCORE_OPEN_TWO = 0.5;
	public static final double SCORE_THREE_THREE = 300;
	public static final double SCORE_FOUR_FOUR = 3000;
	public static final double SCORE_THREE_FOUR = 2000;
	public static final double SCORE_STAND_ALONE = 0.2;

	public BoardTree(Board board, int turn) {
		this.turn = turn;
		// TODO this needs refining.
		this.score = 0;
		this.node = board;
		this.children = new ArrayList<BoardTree>();
		alg = new BasicAlgorithm(node, (this.turn == Board.TURN_SENTE));
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
		alg = new BasicAlgorithm(node, (this.turn == Board.TURN_SENTE));
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

//	public BoardTree getMinMax() {
//		int maxIndex = 0;
//		for (int i = 0; i < children.size(); i++) {
//			if (children.get(i).getScore() > children.get(maxIndex).getScore()) {
////				maxIndex = i
//			}
//		}
//		return children.get(maxIndex);
//	}

	public BoardLocation getBestMove(int level) {
		System.out.println("The Board has Moves number: " + node.getTotalStones());
		this.children.clear();
		makeTree(level, this.turn);
		if (this.children.isEmpty())
			return null;
		int maxIndex = 0;
		for (int i = 0; i < this.children.size(); i++) {
			if (children.get(i).score > children.get(maxIndex).score) {
				maxIndex = i;
			}
		}
		System.out.println(nodeCount);
		return children.get(maxIndex).lastMove;
	}

	/**
	 * Evaluate the scores of the list of patterns. (Excluding sub-patterns, that will
	 * be evaluated separately)
	 * @param pat
	 * @return
	 */
	private double evalPatternsSmart(ArrayList<Pattern> pats) {
		// In the "Smart" solution, we assume that no two existing patterns could be blocked
		// by the same stone. Also, there could not be any two patterns not sharing
		// any stones on the board.
		if (pats.size() > 1) {

		} else {
			Pattern pat1 = pats.get(0);
			if (pat1.getNumLocs() == 4) {
				if (pat1 instanceof ContOpenPattern)
					return SCORE_OPEN_FOUR;
				else
					return SCORE_CONNECT_FOUR;
			} else {
				boolean hasThree = false;
				boolean hasFour = false;
				for (Pattern singlePat : pats) {
					if (singlePat.getNumLocs() == 3) {
						hasThree = true;
					} else if (singlePat.getNumLocs() >= 4) {
						hasFour = true;
					}
				}

				if (hasThree && hasFour) {
					return SCORE_THREE_FOUR;
				} else if (hasThree) {
					return SCORE_THREE_THREE;
				} else if (hasFour) {
					return SCORE_FOUR_FOUR;
				}
			}
		}
		// Shouldn't be reachable.
		return 0;
	}

	private int evalSubPatterns(ArrayList<Pattern> subPatterns) {
		for ()
	}

	private void makeTree(int depth, int ancestorTurn) {
		// if depth is 0, then just evaluate.
		if (depth == 0) {
			this.score = evalRoot();
			this.setApplicable();
			return;
		}
		// multi-threading solution (pre-mature)
//		ArrayList<Thread> threadQueue = new ArrayList<Thread>();
//		ArrayList<BoardLocation> feasibles = alg.generateFeasibleMoves();
//		int curMaxValue = MIN_SCORE;
//		int curMinValue = MAX_SCORE;
//		for (int i = 0; i < feasibles.size(); i++) {
//			BoardLocation feasibleMove = feasibles.get(i);
//			int turn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE :
//				Board.TURN_SENTE;
//			BoardTree child = new BoardTree((Board) DeepCopy.copy(node), feasibleMove, turn);
//			Thread childThread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						child.node.updateBoardLite(feasibleMove, (BoardTree.this.turn == Board.TURN_GOTE));
//					} catch (InvalidIndexException e) {
//						return;
//					}
//					child.makeTree(depth - 1, ancestorTurn);
//					try {
//						child.node.withdrawMoveLite(feasibleMove);
//					} catch (InvalidIndexException e) {
//						return;
//					}
//					// do something here to generate values for each nodes.
//					// still have to combine the alpha-beta.
//				}
//			});
//			synchronized(threadQueue) {
//				threadQueue.add(childThread);
//			}
//			while (threadCount > 2000) {
//
//			}
//			childThread.start();
//			threadCount ++;
//			this.appendChild(child);
//		}
//
//		for (Thread th : threadQueue) {
//			try {
//				th.join();
//			} catch (InterruptedException e) {
//				continue;
//			}
//
//			threadCount --;
//		}
//
//		for (BoardTree child : this.children) {
//			if (BoardTree.this.turn != ancestorTurn) {
//				// find the minimum for the opponent.
//				if (child.score < curMinValue && child.isApplicable()) {
//					curMinValue = child.score;
//				}
//			} else {
//				// find the maximum value of children
//				if (child.score > curMaxValue && child.isApplicable()) {
//					curMaxValue = child.score;
//				}
//			}
//		}

		ArrayList<BoardLocation> feasibles = alg.generateFeasibleMoves();
		double curMaxValue = MIN_SCORE;
		double curMinValue = MAX_SCORE;
		for (int i = 0; i < feasibles.size(); i++) {
			BoardLocation feasibleMove = feasibles.get(i);
			int turn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE :
				Board.TURN_SENTE;
			BoardTree child = new BoardTree(node, feasibleMove, turn);
			try {
				child.node.updateBoardLite(feasibleMove, (BoardTree.this.turn == Board.TURN_GOTE));
			} catch (InvalidIndexException e) {
				return;
			}
			child.makeTree(depth - 1, ancestorTurn);
			try {
				child.node.withdrawMoveLite(feasibleMove);
			} catch (InvalidIndexException e) {
				return;
			}
			// do something here to generate values for each nodes.
			// still have to combine the alpha-beta.
			this.appendChild(child);
			nodeCount ++;

			if (BoardTree.this.turn != ancestorTurn) {
				// find the minimum for the opponent.
				if (child.score < curMinValue && child.isApplicable()) {
					curMinValue = child.score;
				}
			} else {
				// find the maximum value of children
				if (child.score > curMaxValue && child.isApplicable()) {
					curMaxValue = child.score;
				}
			}
		}

		if (this.turn == ancestorTurn) {
			this.score = curMaxValue;
		} else {
			this.score = curMinValue;
		}

		this.setApplicable();
	}

	public static double evalBoard(Board board, int turn) {
		double sum = 0;
		double otherSum = 0;
		if (board.checkcol() || board.checkdiag() || board.checkrow()) {
			return MAX_SCORE;
		}
		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
				(board, turn == Board.TURN_SENTE, 4).size();
//		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
//				(board, turn == Board.TURN_SENTE, 5).size();
//		sum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardClosedPatCont
//				(board, turn == Board.TURN_SENTE, 6).size();

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

		otherSum += SCORE_OPEN_THREE * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 3).size();
		otherSum += SCORE_OPEN_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 4).size();
		otherSum += SCORE_CONNECT_FOUR * BoardChecker.checkBoardOpenPatCont
				(board, turn == Board.TURN_GOTE, 3).size();
		otherSum += SCORE_OPEN_TWO * BoardChecker.checkAllSubPatterns
				(board, turn == Board.TURN_GOTE).size();
		return sum - otherSum * 0.8;
//		return 1;
	}

	public double evalRoot() {
		return evalBoard(this.node, this.turn);
	}

	public void addChild(BoardLocation loc) {
		// TODO maybe just not copy the board?
		VirtualBoard vBoard = VirtualBoard.getVBoard((Board) DeepCopy.copy(this.node));
		int childTurn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE : Board.TURN_SENTE;
		this.children.add(new BoardTree(vBoard, loc, childTurn));
	}

	public void setApplicable() {
		applicable = true;
	}

	public boolean isApplicable() {
		return applicable;
	}
}
