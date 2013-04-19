package org.aitek.ml.demo;

import java.util.List;

import org.aitek.ml.core.Item;
import org.aitek.ml.core.Voter;
import org.aitek.ml.core.similarity.Similarity;
import org.aitek.ml.core.similarity.SimilarityFactory;
import org.aitek.ml.core.similarity.SimilarityFactory.SimilarityMethod;
import org.aitek.ml.tools.RandomData;

public class ItemBasedSample {

	public static void main(String[] args) throws Exception {

		// reads data from disk
		int votersNumber = 10;
		int itemsNumber = 20;
		List<Voter> voters = RandomData.createReaders(votersNumber);
		List<Item> items = RandomData.createItems(itemsNumber);
		RandomData.readDataset(items, itemsNumber, voters, votersNumber);

		for (SimilarityMethod similarityMethod : SimilarityMethod.values()) {
			Similarity similarity = SimilarityFactory.getSimilarity(similarityMethod);
			printTopN(similarity, voters, items, 5);
		}

	}

	private static void printTopN(Similarity similarity, List<Voter> voters, List<Item> items, int topN) {

		System.out.println("\n\nClosest items with: " + similarity.getClass().getSimpleName());

		for (int j = 0; j < items.size(); j++) {
			Item item = items.get(j);
			List<Item> closestItems = item.getClosestItems(voters, items, similarity, 10);
			System.out.print(item + ": \t");
			for (int i = 0; i < closestItems.size() && i < topN; i++) {
				System.out.print(closestItems.get(i));
				if (i < closestItems.size() - 1) {
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}
}
