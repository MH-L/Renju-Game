package algorithm;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositePattern {
	private ArrayList<Pattern> patterns;
	private boolean isUrgent;

	public CompositePattern(ArrayList<Pattern> patterns) {
		this.patterns = patterns;
		this.isUrgent = Pattern.checkIsCompositeUrgent(patterns);
	}

	public static ArrayList<CompositePattern> makeCompositePats(ArrayList<Pattern> pats) {
		ArrayList<Pattern> duplicatedRemoved = Pattern.removeDuplicates(pats);
		ArrayList<CompositePattern> Cpatterns = new ArrayList<CompositePattern>();
		ArrayList<Integer> checkedIndexes = new ArrayList<Integer>();
		ArrayList<Pattern> curList = new ArrayList<Pattern>();
		for (int i = 0; i < duplicatedRemoved.size(); i++) {
			if (checkedIndexes.contains(i))
				continue;
			checkedIndexes.add(i);
			Pattern pat = duplicatedRemoved.get(i);
			curList.add(pat);
			for (int j = i + 1; j < duplicatedRemoved.size(); j++) {
				if (checkedIndexes.contains(j))
					continue;
				Pattern pat2 = duplicatedRemoved.get(j);
				if (pat.isIntersecting(pat2)) {
					curList.add(pat2);
					checkedIndexes.add(j);
				}
			}
			if (curList.size() >= 2) {
				CompositePattern candidate = new CompositePattern(curList);
				Cpatterns.add(candidate);
			}
			curList.clear();
		}
		return Cpatterns;
	}

	public static void filterUrgentComposites(ArrayList<CompositePattern> composites) {
		Iterator<CompositePattern> iter = composites.iterator();
		while (iter.hasNext()) {
			if (!iter.next().isUrgent)
				iter.remove();
		}
	}

	public boolean isUrgent() {
		return isUrgent;
	}

	public boolean isFourFour() {
		int fours = 0;
		for (Pattern ptn : this.patterns) {
			if (ptn.getLocations().size() == 4) {
				fours ++;
			}
		}

		return fours >= 2;
	}

	public boolean isFourThree() {
		boolean hasFour = false;
		boolean hasThree = false;
		for (Pattern ptn : this.patterns) {
			if (ptn.getLocations().size() == 4) {
				hasFour = true;
			} else if (ptn.getLocations().size() == 3) {
				hasThree = true;
			}
		}

		return hasFour && hasThree;
	}

	public boolean isThreeThree() {
		int threes = 0;
		for (Pattern ptn : this.patterns) {
			if (ptn.getLocations().size() == 3) {
				threes ++;
			}
		}

		return threes >= 2;
	}
}
