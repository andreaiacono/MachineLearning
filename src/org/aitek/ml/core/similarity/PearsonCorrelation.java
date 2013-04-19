package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Voter;

public class PearsonCorrelation implements Similarity {

	@Override
	public double getDistanceBetweenVoters(List<Item> items, Voter voter1, Voter voter2) {

		double voter1Sum = 0;
		double voter2Sum = 0;
		double voter1SquaresSum = 0;
		double voter2SquaresSum = 0;
		double votersSquaresProductsSum = 0;
		int matches = 0;

		for (Item item : items) {

			Integer vote1 = voter1.getVote(item);
			Integer vote2 = voter2.getVote(item);

			if (vote1 != null && vote2 != null) {
				voter1Sum += vote1;
				voter2Sum += vote2;
				voter1SquaresSum += vote1 * vote1;
				voter2SquaresSum += vote2 * vote2;
				votersSquaresProductsSum += vote1 * vote2;
				matches++;
			}
		}

		if (matches == 0) {
			return -100;
		}

		double numerator = votersSquaresProductsSum - (voter1Sum * voter2Sum / matches);
		double denominator = Math.sqrt((voter1SquaresSum - Math.pow(voter1Sum, 2) / matches) * (voter2SquaresSum - Math.pow(voter2Sum, 2) / matches));

		if (denominator == 0) {
			return 0;
		}

		return ((numerator / denominator) + 1) / 2;
	}

	@Override
	public double getDistanceBetweenItems(List<Voter> voters, Item item1, Item item2) {

		double voter1Sum = 0;
		double voter2Sum = 0;
		double voter1SquaresSum = 0;
		double voter2SquaresSum = 0;
		double votersSquaresProductsSum = 0;
		int matches = 0;

		for (Voter voter : voters) {

			Integer vote1 = voter.getVote(item1);
			Integer vote2 = voter.getVote(item2);

			if (vote1 != null && vote2 != null) {
				voter1Sum += vote1;
				voter2Sum += vote2;
				voter1SquaresSum += vote1 * vote1;
				voter2SquaresSum += vote2 * vote2;
				votersSquaresProductsSum += vote1 * vote2;
				matches++;
			}
		}

		if (matches == 0) {
			return -100;
		}

		double numerator = votersSquaresProductsSum - (voter1Sum * voter2Sum / matches);
		double denominator = Math.sqrt((voter1SquaresSum - Math.pow(voter1Sum, 2) / matches) * (voter2SquaresSum - Math.pow(voter2Sum, 2) / matches));

		if (denominator == 0) {
			return 0;
		}

		return ((numerator / denominator) + 1) / 2;
	}
}
