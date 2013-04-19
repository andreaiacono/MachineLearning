package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Voter;

public class ManhattanDistance implements Similarity {

	@Override
	public double getDistanceBetweenVoters(List<Item> items, Voter voter1, Voter voter2) {

		double squaresSum = 0;
		boolean matched = false;

		for (Item item : items) {

			Integer vote1 = voter1.getVote(item);
			Integer vote2 = voter2.getVote(item);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.abs(vote1 - vote2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		return 1 / (1 + squaresSum);
	}

	@Override
	public double getDistanceBetweenItems(List<Voter> voters, Item item1, Item item2) {

		double squaresSum = 0;
		boolean matched = false;

		for (Voter voter : voters) {

			Integer vote1 = voter.getVote(item1);
			Integer vote2 = voter.getVote(item2);

			if (vote1 != null && vote2 != null) {
				squaresSum += Math.abs(vote1 - vote2);
				matched = true;
			}
		}

		if (!matched) {
			return -1;
		}

		return 1 / (1 + squaresSum);
	}
}
