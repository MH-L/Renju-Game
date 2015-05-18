package algorithm;

import java.util.ArrayList;

import model.Board;
import model.BoardLocation;

public class BoardTree {
	private ArrayList<BoardTree> children;
	private Board node;
	private BoardLocation lastMove = null;

	public BoardTree(Board board) {
		this.node = board;
		this.children = null;
	}

	public BoardTree(Board board, BoardLocation lastMove) {
		this.node = board;
		this.children = null;
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
}
