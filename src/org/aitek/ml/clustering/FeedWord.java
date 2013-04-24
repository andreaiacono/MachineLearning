package org.aitek.ml.clustering;

public class FeedWord {

	private Integer occurrences;
	private final String word;

	public FeedWord(String word, Integer occurrences) {

		this.word = word;
		this.setOccurrences(occurrences);
	}

	public Integer getOccurrences() {

		return occurrences;
	}

	public void setOccurrences(Integer occurrences) {

		this.occurrences = occurrences;
	}

	public String getWord() {

		return word;
	}
}