package org.aitek.ml.demo;

import java.text.DecimalFormat;
import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.Voter;
import org.aitek.ml.core.similarity.Measurable;
import org.aitek.ml.core.similarity.SimilarityFactory;
import org.aitek.ml.core.similarity.SimilarityFactory.SimilarityMethod;
import org.aitek.ml.tools.RandomData;

public class ItemBasedSample {

	public static void main(String[] args) throws Exception {

		// reads data from disk
		List<Voter> users = RandomData.createUsers(10);
		List<Rankable> items = RandomData.createItems(100);
		RandomData.readDataset(items, users);
		Rankable anItem = items.get(4);

		for (SimilarityMethod similarityMethod : SimilarityMethod.values()) {
			Measurable similarity = SimilarityFactory.getSimilarity(similarityMethod);
			printGrid(similarity, users, items, 2);
			System.out.println("The user closer to User n.4 is " + anItem.getClosestRankable(users, items, similarity));
			// System.out.println("The most desirable item for User n.4 is " + ((Item)
			// anItem).getMostDesiderableItem(items, users, similarity));
		}

	}

	private static void printGrid(Measurable measurable, List<Voter> users, List<Rankable> items, int decimalsNumber) {

		System.out.println("\n\nMethod: " + measurable.getClass().getSimpleName());
		System.out.print("User\t");

		StringBuffer decimals = new StringBuffer();
		for (int d = 0; d < decimalsNumber; d++) {
			decimals.append("#");
		}
		DecimalFormat dm = new DecimalFormat("#." + decimals.toString());

		for (int j = 0; j < users.size(); j++) {
			System.out.print("\tn." + j);
		}
		System.out.println();

		for (int j = 0; j < items.size(); j++) {
			Rankable item1 = items.get(j);
			System.out.print(item1 + "\t");
			for (int i = 0; i <= j; i++) {
				Rankable item2 = items.get(i);
				double distance = measurable.getDistanceBetweenItems(users, item1, item2);
				System.out.print(Double.valueOf(dm.format(distance)) + "\t");
			}
			System.out.println("");
		}

	}
}
