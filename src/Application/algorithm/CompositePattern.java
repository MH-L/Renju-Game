package algorithm;

import java.util.ArrayList;

public class CompositePattern {
	private ArrayList<Pattern> patterns;
	private boolean isUrgent;

	public CompositePattern(ArrayList<Pattern> patterns) {
		this.patterns = patterns;
		this.isUrgent = Pattern.checkIsCompositeUrgent(patterns);
	}
}
