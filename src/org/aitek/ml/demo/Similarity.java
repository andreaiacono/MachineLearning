package org.aitek.ml.demo;

import java.util.List;

import org.aitek.ml.core.Rankable;
import org.aitek.ml.core.User;
import org.aitek.ml.core.Voter;
import org.aitek.ml.core.similarity.Measurable;
import org.aitek.ml.core.similarity.SimilarityFactory;
import org.aitek.ml.core.similarity.SimilarityFactory.SimilarityMethod;
import org.aitek.ml.tools.RandomData;

public class Similarity {

	public static void main(String[] args) throws Exception {

		// reads data from disk
		List<Voter> users = RandomData.createUsers(10);
		List<Rankable> items = RandomData.createItems(100);
		RandomData.readDataset(items, users);
		Voter aUser = users.get(4);

		for (SimilarityMethod similarityMethod : SimilarityMethod.values()) {
			Measurable similarity = SimilarityFactory.getSimilarity(similarityMethod);
			printGrid(similarity, users, items);
			System.out.println("The user closer to User n.4 is " + aUser.getCloserVoter(users, items, similarity));
			System.out.println("The most desirable item for User n.4 is " + ((User) aUser).getMostDesiderableItem(items, users, similarity));
		}

		// the highest score for item

		// System.out.println(RandomData.getDataAsCsv(items, users, false));
		// RandomData.setRandomVotes(items, users);
	}

	private static void printGrid(Measurable distance, List<Voter> users, List<Rankable> items) {

		System.out.println("\n\nMethod: " + distance.getClass().getSimpleName());
		System.out.print("User\t");

		for (int j = 0; j < users.size(); j++) {
			System.out.print("\tn." + j);
		}
		System.out.println();

		for (int j = 0; j < users.size(); j++) {
			Voter user1 = users.get(j);
			System.out.print(user1 + "\t");
			for (int i = 0; i <= j; i++) {
				Voter user2 = users.get(i);
				System.out.print(distance.getDistanceBetweenUsers(items, user1, user2) + "\t");
			}
			System.out.println("");
		}

	}

}
