package org.aitek.ml.demo;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;
import org.aitek.ml.core.similarity.Measurable;
import org.aitek.ml.core.similarity.SimilarityFactory;
import org.aitek.ml.core.similarity.SimilarityFactory.SimilarityType;
import org.aitek.ml.tools.RandomData;

public class Similarity {

	public static void main(String[] args) throws Exception {

		// reads data from disk
		List<Voter> users = RandomData.createUsers(10);
		List<Rankable> items = RandomData.createItems(100);
		RandomData.readDataset(items, users);

		Measurable euclidean = SimilarityFactory.getSimilarity(SimilarityType.EUCLIDEAN);
		printGrid(euclidean, users, items);

		Measurable manhattan = SimilarityFactory.getSimilarity(SimilarityType.MANHATTAN);
		printGrid(manhattan, users, items);

		Measurable pearson = SimilarityFactory.getSimilarity(SimilarityType.PEARSON);
		printGrid(pearson, users, items);

		// System.out.println(RandomData.getDataAsCsv(items, users, false));
		// RandomData.setRandomVotes(items, users);
	}

	private static void printGrid(Measurable distance, List<Voter> users, List<Rankable> items) {

		System.out.println("\n\nMethod: " + distance.getClass().getSimpleName());
		System.out.print("\t");

		for (int j = 0; j < users.size(); j++) {
			System.out.print("\t" + j);
		}
		System.out.println();

		for (int j = 0; j < users.size(); j++) {
			Voter user1 = users.get(j);
			System.out.print(user1 + "\t");
			for (int i = 0; i <= j; i++) {
				Voter user2 = users.get(i);
				System.out.print(distance.getScore(items, user1, user2) + "\t");
			}
			System.out.println("");
		}

	}

}
