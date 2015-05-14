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
		Pattern.removeDuplicates(pats);
		ArrayList<CompositePattern> Cpatterns = new ArrayList<CompositePattern>();
		ArrayList<Integer> checkedIndexes = new ArrayList<Integer>();
		ArrayList<Pattern> curList = new ArrayList<Pattern>();
		for (int i = 0; i < pats.size(); i++) {
			if (checkedIndexes.contains(i))
				continue;
			checkedIndexes.add(i);
			Pattern pat = pats.get(i);
			curList.add(pat);
			for (int j = i + 1; j < pats.size(); j++) {
				if (checkedIndexes.contains(j))
					continue;
				Pattern pat2 = pats.get(j);
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
}
