package renju.com.lmh.algorithm;

import java.util.ArrayList;

import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.model.Board;
import renju.com.lmh.model.BoardLocation;
import renju.com.lmh.model.VirtualBoard;
import renju.com.lmh.utils.DeepCopy;

public class BoardTree {
//  for multi threaded version to monitor the number of threads currently running.
//	private static int threadCount = 0;
	// The default score that any board remains under evaluation uses.
	public static long totalTimeElapsed;
	private static final double UNAPPLICABLE_SCORE = 0.000001;
	private static final double FILTER_THRESHOLD = 0.5;
	private static int nodeCount = 0;
	private int turn;
	private ArrayList<BoardTree> children;
	private Board node;
	private BoardLocation lastMove = null;
	private double score;
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
	public static final double SCORE_STAND_ALONE = 0.15;

	public BoardTree(Board board, int turn) {
		this.turn = turn;
		// TODO this needs refining.
		this.score = UNAPPLICABLE_SCORE;
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
		this.score = UNAPPLICABLE_SCORE;
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

	public double getScore() {
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
	private static double evalPatternsSmart(ArrayList<Pattern> pats) {
		// In the "Smart" solution, we assume that no two existing patterns could be blocked
		// by the same stone. Also, there could not be any two patterns not sharing
		// any stones on the board.
		if (pats.size() < 2) {
			if (pats.size() == 0)
				return 0.0;
			Pattern pt = pats.get(0);
			if (pt.getNumLocs() == 3) {
				return SCORE_OPEN_THREE;
			} else if (pt.getNumLocs() == 4) {
				if (pt instanceof ContOpenPattern)
					return SCORE_OPEN_FOUR;
				else
					return SCORE_CONNECT_FOUR;
			}
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

	private static double evalSubPatterns(ArrayList<Pattern> subPatterns) {
		double sum = 0;
		for (Pattern pat : subPatterns) {
			if (pat.getNumLocs() == 2) {
				sum += SCORE_OPEN_TWO;
			} else if (pat.getNumLocs() == 3) {
				sum += SCORE_CONNECT_THREE;
			}
		}

		return sum;
	}

	private void makeTree(int depth, int ancestorTurn) {
		// if depth is 0, then just evaluate.
		if (depth == 0) {
			this.score = evalBoard(node, ancestorTurn);
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
		// TODO sometimes evalBoard() is called twice.
		if (this.score == UNAPPLICABLE_SCORE) {
			this.score = evalBoard(node, ancestorTurn);
		}
		double curMaxValue = MIN_SCORE;
		double curMinValue = MAX_SCORE;
		for (int i = 0; i < feasibles.size(); i++) {
			BoardLocation feasibleMove = feasibles.get(i);
			int turn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE :
				Board.TURN_SENTE;
			BoardTree child = new BoardTree(node, feasibleMove, turn);
			try {
				long start = System.nanoTime();
				child.node.updateBoardSolitaire(feasibleMove, (this.turn == Board.TURN_GOTE));
				long end = System.nanoTime();
				totalTimeElapsed += end - start;
			} catch (InvalidIndexException e) {
				return;
			}

			double childScore = evalBoard(child.node, ancestorTurn);
			child.score = childScore;
			if (childScore - this.score < FILTER_THRESHOLD) {
				try {
					long start = System.nanoTime();
					child.node.withdrawMoveSolitaire(feasibleMove);
					long end = System.nanoTime();
					totalTimeElapsed += end - start;
				} catch (InvalidIndexException e) {
					return;
				}
				continue;
			}

			child.makeTree(depth - 1, ancestorTurn);
			try {
				child.node.withdrawMoveSolitaire(feasibleMove);
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

		if (this.children.size() == 0) {
			for (int i = 0; i < feasibles.size(); i++) {
				BoardLocation feasibleMove = feasibles.get(i);
				int turn = (this.turn == Board.TURN_SENTE) ? Board.TURN_GOTE :
					Board.TURN_SENTE;
				BoardTree child = new BoardTree(node, feasibleMove, turn);
				try {
					child.node.updateBoardLite(feasibleMove, (this.turn == Board.TURN_GOTE));
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
		}

		if (this.turn == ancestorTurn) {
			this.score = curMaxValue;
		} else {
			this.score = curMinValue;
		}

		this.setApplicable();
	}

	public static double evalBoard(Board board, int turn) {
		ArrayList<Pattern> selfPatterns = turn == Board.TURN_SENTE ?
				board.getFirstPattern() : board.getSecondPattern();

		ArrayList<Pattern> otherPatterns = turn == Board.TURN_GOTE ?
				board.getFirstPattern() : board.getSecondPattern();

		ArrayList<Pattern> selfSubPatterns = turn == Board.TURN_SENTE ?
				board.getFirstPlayerSubPattern() : board.getSecondPlayerSubPattern();

		ArrayList<Pattern> otherSubPatterns = turn == Board.TURN_GOTE ?
				board.getFirstPlayerSubPattern() : board.getSecondPlayerSubPattern();
		double sum = 0;
		double otherSum = 0;
		sum += evalSubPatterns(selfSubPatterns);
		sum += evalPatternsSmart(selfPatterns);
		otherSum += evalSubPatterns(otherSubPatterns);
		otherSum += evalPatternsSmart(otherPatterns);
		return sum - otherSum * 0.8;
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
