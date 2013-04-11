package org.aitek.ml.core.similarity;

import java.text.DecimalFormat;
import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public class EuclideanDistance implements Measurable {

	@Override
	public double getDistanceBetweenUsers(List<Rankable> items, Voter user1, Voter user2) {

		return getScore(items, user1, user2, 2);
	}

	public double getScore(List<Rankable> items, Voter user1, Voter user2, int decimals) {

		double squaresSum = 0;
		boolean matched = false;

		for (Rankable item : items) {

			Integer vote1 = user1.getVote(item);
			Integer vote2 = user2.getVote(item);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.pow(vote1 - vote2, 2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		// we don't need the distance, but a measure of how close two voters are
		double distance = 1 / (1 + Math.sqrt(squaresSum));
		// FIX THIS
		DecimalFormat dm = new DecimalFormat("#.###");
		return Double.valueOf(dm.format(distance));
	}
}
