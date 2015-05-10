package algorithm;

import java.util.ArrayList;

import model.Board;

public class BoardTree {
	private ArrayList<BoardTree> children;
	private static Board node;

	public BoardTree(Board board) {
		node = board;
		this.children = null;
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
<<<<<<< HEAD
			if (arrayTrees.get(i).getDepth() > maxDepth)
=======
			if (arrayTrees.get(i).getDepth > maxDepth)
>>>>>>> master
				maxDepth = arrayTrees.get(i).getDepth();
		}
		return maxDepth;
	}
}
