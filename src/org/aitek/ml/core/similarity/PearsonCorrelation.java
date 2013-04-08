package org.aitek.ml.core.similarity;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;

public class PearsonCorrelation implements Similarity {

	@Override
	public double getScore(List<Rankable> items, Voter user1, Voter user2) {

		double user1Sum = 0;
		double user2Sum = 0;
		double user1SquaresSum = 0;
		double user2SquaresSum = 0;
		double usersSquaresProductsSum = 0;
		int matches = 0;

		for (Rankable item : items) {

			Integer vote1 = user1.getVote(item);
			Integer vote2 = user2.getVote(item);

			if (vote1 != null && vote2 != null) {
				user1Sum += vote1;
				user2Sum += vote2;
				user1SquaresSum += vote1 * vote1;
				user2SquaresSum += vote2 * vote2;
				usersSquaresProductsSum += vote1 * vote2;
				matches++;
			}
		}

		if (matches == 0) {
			return -1;
		}

		double numerator = usersSquaresProductsSum - (user1Sum * user2Sum / matches);
		double denominator = Math.sqrt((user1SquaresSum - Math.pow(user1Sum, 2) / matches) * (user2SquaresSum - Math.pow(user2Sum, 2) / matches));

		if (denominator == 0) {
			return 0;
		}

		return numerator / denominator;
	}

}