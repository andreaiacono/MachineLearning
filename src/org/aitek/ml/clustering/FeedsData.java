package org.aitek.ml.clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FeedsData {

	Map<String, Feed> data = new HashMap<String, Feed>();

	public void setFeed(Feed feed) {

		data.put(feed.getName(), feed);
	}

	public Set<String> getWords() {

		Set<String> words = new HashSet<String>();
		for (String feed : data.keySet()) {

			words.addAll(data.get(feed).getWords());
		}

		return words;
	}

	// TODO these methods are to be refactored
	public Set<FeedWord> getWordsNumberInFeeds() {

		Set<FeedWord> feedWords = new HashSet<FeedWord>();
		Set<String> words = getWords();
		for (String word : words) {
			FeedWord feedWord = new FeedWord(word, 0);
			for (String feed : data.keySet()) {

				Integer occurrences = data.get(feed).getWordOccurrences(word);
				if (occurrences > 0) {
					feedWord.setOccurrences(feedWord.getOccurrences() + 1);
				}
			}

			feedWords.add(feedWord);
		}

		return feedWords;
	}

	public Set<FeedWord> getFeedWords(Set<FeedWord> commonWordsInFeeds) {

		Set<FeedWord> feedWords = new HashSet<FeedWord>();
		Set<String> words = getWords();
		for (String word : words) {
			FeedWord feedWord = new FeedWord(word, 0);
			for (String feed : data.keySet()) {

				Integer occurrences = data.get(feed).getWordOccurrences(word);
				if (occurrences > 0) {
					feedWord.setOccurrences(feedWord.getOccurrences() + data.get(feed).getWordOccurrences(word));
				}
			}

			feedWords.add(feedWord);
		}

		return feedWords;
	}

	public int getFeedsNumber() {

		return data.keySet().size();
	}
}
