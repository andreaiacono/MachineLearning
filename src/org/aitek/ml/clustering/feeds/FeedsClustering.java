package org.aitek.ml.clustering.feeds;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aitek.ml.domain.Item;
import org.aitek.ml.domain.Voter;
import org.aitek.ml.similarity.Similarity;
import org.aitek.ml.tools.SQLiteWrapper;

public class FeedsClustering {

	public void startClustering() throws Exception {

		FeedsData feedsData = SQLiteWrapper.loadData();

		Set<FeedWord> commonWordsInFeeds = new HashSet<FeedWord>();
		for (FeedWord feedWord : feedsData.getWordsNumberInFeeds()) {
			double ratio = feedWord.getOccurrences() / (double) feedsData.getFeedsNumber();
			System.out.println(feedWord.getWord() + ": " + feedWord.getOccurrences() + " " + ratio);
			if (ratio > 0.15 && ratio < 0.5) {
				commonWordsInFeeds.add(feedWord);
			}
		}

		Set<FeedWord> commonWords = feedsData.getFeedWords(commonWordsInFeeds);
		for (FeedWord feedWord : commonWords) {
			System.out.println("common " + feedWord.getWord() + ": " + feedWord.getOccurrences());
		}

	}

	private void printTopN(Similarity similarity, List<Voter> voters, List<Item> items, int topN) {

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
