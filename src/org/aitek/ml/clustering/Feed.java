package org.aitek.ml.clustering;

import java.util.HashSet;
import java.util.Set;

public class Feed {

	private final String name;
	private Set<FeedWord> feedWords;

	public Feed(String name) {

		this.name = name;
	}

	public Feed(String name, Set<FeedWord> words) {

		this.name = name;
		this.feedWords = words;
	}

	public void setWords(Set<FeedWord> words) {

		this.feedWords = words;
	}

	public Integer getWordOccurrences(String word) {

		for (FeedWord feedWord : feedWords) {
			if (feedWord.getWord().equals(word)) return feedWord.getOccurrences();
		}
		return -1;
	}

	public String getName() {

		return name;
	}

	public Set<FeedWord> getData() {

		return feedWords;
	}

	public Set<String> getWords() {

		Set<String> words = new HashSet<String>();
		for (FeedWord feedWord : feedWords) {
			words.add(feedWord.getWord());
		}
		return words;
	}

}
