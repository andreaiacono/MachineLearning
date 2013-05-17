package org.aitek.ml.demo;

import java.text.DecimalFormat;
import java.util.List;

import org.aitek.ml.domain.Item;
import org.aitek.ml.domain.Reader;
import org.aitek.ml.domain.Voter;
import org.aitek.ml.similarity.Similarity;
import org.aitek.ml.similarity.SimilarityFactory;
import org.aitek.ml.similarity.SimilarityFactory.SimilarityMethod;
import org.aitek.ml.tools.RandomData;

public class UserBasedSample {

	public static void main(String[] args) throws Exception {

		// reads data from disk
		int readersNumber = 10;
		int itemsNumber = 100;
		List<Voter> readers = RandomData.createReaders(readersNumber);
		List<Item> items = RandomData.createItems(itemsNumber);
		RandomData.readDataset(items, itemsNumber, readers, readersNumber);
		Voter aReader = readers.get(4);

		for (SimilarityMethod similarityMethod : SimilarityMethod.values()) {
			Similarity similarity = SimilarityFactory.getSimilarity(similarityMethod);
			printGrid(similarity, readers, items, 2);
			System.out.println("The reader closer to Reader n.4 is " + aReader.getClosestVoter(readers, items, similarity));
			System.out.println("The most desirable item for Reader n.4 is " + ((Reader) aReader).getMostDesiderableItem(items, readers, similarity));
		}
	}

	private static void printGrid(Similarity measurable, List<Voter> readers, List<Item> items, int decimalsNumber) {

		System.out.println("\n\nMethod: " + measurable.getClass().getSimpleName());
		System.out.print("Reader|Item    ");

		StringBuffer decimals = new StringBuffer();
		for (int d = 0; d < decimalsNumber; d++) {
			decimals.append("#");
		}
		DecimalFormat dm = new DecimalFormat("#." + decimals.toString());

		for (int j = 0; j < readers.size(); j++) {
			System.out.print("\tn." + j);
		}
		System.out.println();

		for (int j = 0; j < readers.size(); j++) {
			Voter reader1 = readers.get(j);
			System.out.print(reader1 + "\t");
			for (int i = 0; i <= j; i++) {
				Voter reader2 = readers.get(i);
				double distance = measurable.getDistanceBetweenVoters(items, reader1, reader2);
				System.out.print(Double.valueOf(dm.format(distance)) + "\t");
			}
			System.out.println("");
		}

	}
}
