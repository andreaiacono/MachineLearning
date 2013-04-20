package org.aitek.ml.similarity;

import java.util.List;

import org.aitek.ml.domain.Item;
import org.aitek.ml.domain.Voter;

public class EuclideanDistance implements Similarity {

	@Override
	public double getDistanceBetweenVoters(List<Item> items, Voter voter1, Voter voter2) {

		double squaresSum = 0;
		boolean matched = false;

		for (Item item : items) {

			Integer vote1 = voter1.getVote(item);
			Integer vote2 = voter2.getVote(item);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.pow(vote1 - vote2, 2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		// we don't need the distance, but a measure of how close two voters are
		return 1 / (1 + Math.sqrt(squaresSum));

	}

	@Override
	public double getDistanceBetweenItems(List<Voter> voters, Item item1, Item item2) {

		double squaresSum = 0;
		boolean matched = false;

		for (Voter voter : voters) {

			Integer vote1 = voter.getVote(item1);
			Integer vote2 = voter.getVote(item2);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.pow(vote1 - vote2, 2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		// we don't need the distance, but a measure of how close two items are
		return 1 / (1 + Math.sqrt(squaresSum));
	}

}
