package org.aitek.ml.core.similarity;

import java.text.DecimalFormat;
import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public class ManhattanDistance implements Measurable {

	@Override
	public double getScore(List<Rankable> items, Voter user1, Voter user2) {

		double squaresSum = 0;
		boolean matched = false;

		for (Rankable item : items) {

			Integer vote1 = user1.getVote(item);
			Integer vote2 = user2.getVote(item);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.abs(vote1 - vote2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		double distance = 1 / (1 + squaresSum);
		// FIX THIS
		DecimalFormat dm = new DecimalFormat("#.###");
		return Double.valueOf(dm.format(distance));
	}
}
