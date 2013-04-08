package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public class EuclideanDistanceScore implements Similarity {

	@Override
	public double getScore(List<Rankable> items, Voter user1, Voter user2) {

		double value = -1;

		for (Rankable item : items) {

			Integer vote1 = user1.getVote(item);
			Integer vote2 = user2.getVote(item);

			if (vote1 != null && vote2 != null) {
				value += Math.pow(vote1 - vote2, 2);
			}
		}

		if (value == -1) {
			return -1;
		}

		// we don't need the distance, but a measure of how close two voters are
		return 1 / (1 + Math.sqrt(value));
	}
}
